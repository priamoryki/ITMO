CREATE OR REPLACE FUNCTION FlightStat(UId INTEGER, Password TEXT, FId INTEGER) RETURNS TABLE(
    FlightId      INTEGER,
    CanReserve    BOOLEAN,
    CanBuy        BOOLEAN,
    FreeSeats     BIGINT,
    ReservedSeats BIGINT,
    BoughtSeats   BIGINT
)
AS $$
BEGIN
    IF NOT ValidateUser(UId, Password)
    THEN
        RETURN;
    END IF;

    RETURN QUERY
        SELECT f.FlightId                                                                                                AS FlightId,
            CanReserve(f.FlightId)
                AND (COUNT(SeatNo) FILTER (WHERE Status IS NULL OR Status = 'reserved' AND ExpirationTime <= NOW())) > 0 AS CanReserve,
            CanBuy(f.FlightId)
                AND (COUNT(SeatNo) FILTER (WHERE UserId = UId AND Status = 'reserved' AND ExpirationTime > NOW())) > 0   AS CanBuy,
            COUNT(SeatNo) FILTER (WHERE Status IS NULL OR Status = 'reserved' AND ExpirationTime <= NOW())               AS FreeSeats,
            COUNT(SeatNo) FILTER (WHERE UserId = UId AND Status = 'reserved' AND ExpirationTime > NOW())                 AS ReservedSeats,
            COUNT(SeatNo) FILTER (WHERE UserId = UId AND Status = 'bought')                                              AS BoughtSeats
        FROM Flights f NATURAL JOIN Seats NATURAL LEFT JOIN Orders WHERE f.FlightId = FId
        GROUP BY f.FlightId;
END;
$$ LANGUAGE plpgsql;
