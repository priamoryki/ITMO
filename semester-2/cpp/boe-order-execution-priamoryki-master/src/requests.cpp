#include "requests.h"

namespace {

void encode_new_order_opt_fields(unsigned char * bitfield_start,
                                 const double price,
                                 const char ord_type,
                                 const char time_in_force,
                                 const unsigned max_floor,
                                 const std::string & symbol,
                                 const char capacity,
                                 const std::string & account)
{
    auto * p = bitfield_start + new_order_bitfield_num();
#define FIELD(name, bitfield_num, bit)                    \
    set_opt_field_bit(bitfield_start, bitfield_num, bit); \
    p = encode_field_##name(p, name);
#include "new_order_opt_fields.inl"
}

uint8_t encode_request_type(const RequestType type)
{
    switch (type) {
    case RequestType::New:
        return 0x38;
    }
    return 0;
}

unsigned char * add_request_header(unsigned char * start, unsigned length, const RequestType type, unsigned seq_no)
{
    *start++ = 0xBA;
    *start++ = 0xBA;
    start = encode(start, static_cast<uint16_t>(length));
    start = encode(start, encode_request_type(type));
    *start++ = 0;
    return encode(start, seq_no);
}

char convert_side(const Side side)
{
    switch (side) {
    case Side::Buy: return '1';
    case Side::Sell: return '2';
    }
    return 0;
}

char convert_ord_type(const OrdType ord_type)
{
    switch (ord_type) {
    case OrdType::Market: return '1';
    case OrdType::Limit: return '2';
    case OrdType::Pegged: return 'P';
    }
    return 0;
}

char convert_time_in_force(const TimeInForce time_in_force)
{
    switch (time_in_force) {
    case TimeInForce::Day: return '0';
    case TimeInForce::IOC: return '3';
    case TimeInForce::GTD: return '6';
    }
    return 0;
}

char convert_capacity(const Capacity capacity)
{
    switch (capacity) {
    case Capacity::Agency: return 'A';
    case Capacity::Principal: return 'P';
    case Capacity::RisklessPrincipal: return 'R';
    }
    return 0;
}

} // anonymous namespace

std::array<unsigned char, calculate_size(RequestType::New)> create_new_order_request(const unsigned seq_no,
                                                                                     const std::string & cl_ord_id,
                                                                                     const Side side,
                                                                                     const double volume,
                                                                                     const double price,
                                                                                     const OrdType ord_type,
                                                                                     const TimeInForce time_in_force,
                                                                                     const double max_floor,
                                                                                     const std::string & symbol,
                                                                                     const Capacity capacity,
                                                                                     const std::string & account)
{
    static_assert(calculate_size(RequestType::New) == 78, "Wrong New Order message size");

    std::array<unsigned char, calculate_size(RequestType::New)> msg;
    auto * p = add_request_header(&msg[0], msg.size() - 2, RequestType::New, seq_no);
    p = encode_text(p, cl_ord_id, 20);
    p = encode_char(p, convert_side(side));
    p = encode_binary4(p, static_cast<uint32_t>(volume));
    p = encode(p, static_cast<uint8_t>(new_order_bitfield_num()));
    encode_new_order_opt_fields(p,
                                price,
                                convert_ord_type(ord_type),
                                convert_time_in_force(time_in_force),
                                max_floor,
                                symbol,
                                convert_capacity(capacity),
                                account);
    return msg;
}

std::string readSequenceOfBytes(const std::vector<unsigned char> & message, int & i, int amount)
{
    int end = fmin(i + amount, message.size());
    std::string result = std::string(
            message.begin() + i,
            std::find(message.begin() + i, message.begin() + end,0x00)
            );
    i = end;
    return result;
}

std::string convertLong(unsigned long long x, int base)
{
    std::string result;
    while (x != 0) {
        const int value = x % base;
        if (value >= 10) {
            // value + 'A' - 10 -- converts the number to letter
            result.push_back(static_cast<char>(value + 'A' - 10));
        } else {
            // value + '0' -- converts the number to char number
            result.push_back(static_cast<char>(value + '0'));
        }
        x /= base;
    }
    std::reverse(result.begin(), result.end());
    return result;
}

unsigned long long convertString(const std::string & s, int base)
{
    unsigned long long result = 0;
    for (size_t i = 0; i < s.size(); i++) {
        result *= base;
        result += static_cast<unsigned char>(s[s.size() - (i + 1)]);
    }
    return result;
}

unsigned long long readString(const std::vector<unsigned char> & message, int & i, int amount, int base)
{
    return convertString(readSequenceOfBytes(message, i, amount), base);
}

unsigned long long readVolume(const std::vector<unsigned char> & message, int & i)
{
    return readString(message, i, 4, 256);
}

void setLiquidityIndicator(const std::vector<unsigned char> & message, int & i, ExecutionDetails & exec_details)
{
    const char baseLiquidityIndicator = readSequenceOfBytes(message, i, 1)[0];
    if (baseLiquidityIndicator == 'A') {
        exec_details.liquidity_indicator = LiquidityIndicator::Added;
    } else if (baseLiquidityIndicator == 'R') {
        exec_details.liquidity_indicator = LiquidityIndicator::Removed;
    }
}

ExecutionDetails decode_order_execution(const std::vector<unsigned char> & message)
{
    ExecutionDetails exec_details;
    int i = 0; // next char id
    // FILE HEADER
    readSequenceOfBytes(message, i, 2); // StartOfMassage
    readSequenceOfBytes(message, i, 2); // MessageLength
    readSequenceOfBytes(message, i, 1); // MessageType
    readSequenceOfBytes(message, i, 1); // MatchingUnit
    readSequenceOfBytes(message, i, 4); // SequenceNumber
    readSequenceOfBytes(message, i, 8); // TransactionTime
    // CONTENT DATA
    exec_details.cl_ord_id = readSequenceOfBytes(message, i, 20); // ClOrdId
    exec_details.exec_id = convertLong(readString(message, i, 8, 256), 36); // ExecId
    exec_details.filled_volume = readVolume(message, i); // LastShares
    exec_details.price = round(readString(message, i, 8, 256)) / 10000; // LastPx
    exec_details.active_volume = readVolume(message, i); // LeavesQty
    setLiquidityIndicator(message, i, exec_details); // BaseLiquidityIndicator
    readSequenceOfBytes(message, i, 1); // SubLiquidityIndicator
    readSequenceOfBytes(message, i, 4); // ContraBroker
    readSequenceOfBytes(message, i, 1); // ReservedInternal
    const int numberOfReturn = readSequenceOfBytes(message, i, 1)[0]; // NumberOfReturn
    // OPTIONAL BITFIELDS
    for (int j = 0; j < numberOfReturn; j++) {
        readSequenceOfBytes(message, i, 1); // ReturnBitfield
    }
    exec_details.symbol = readSequenceOfBytes(message, i, 8); // Symbol
    exec_details.last_mkt = readSequenceOfBytes(message, i, 4); // LastMkt
    exec_details.fee_code = readSequenceOfBytes(message, i, 2); // FeeCode
    return exec_details;
}

std::vector<unsigned char> request_optional_fields_for_message(const ResponseType)
{
    return {0x00, 0x01, 0x00, 0x00, 0x00, 0x00, 0x80, 0x01};
}