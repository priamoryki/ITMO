CREATE OR REPLACE FUNCTION BuyReserved(UId INTEGER, Password TEXT, FId INTEGER, SNo VARCHAR(4)) RETURNS BOOLEAN
AS $$
BEGIN
    IF NOT ValidateUser(UId, Password)
    THEN
        RETURN FALSE;
    END IF;

    IF NOT CanBuy(FId)
    THEN
        RETURN FALSE;
    END IF;

    IF NOT EXISTS(SELECT FlightId FROM Orders WHERE FlightId = FId
                                                AND SeatNo = SNo
                                                AND UserId = UId
                                                AND ExpirationTime > NOW()
                                                AND Status = 'reserved')
    THEN
        RETURN FALSE;
    END IF;

    UPDATE Orders
    SET ExpirationTime = NULL, Status = 'bought'
    WHERE FlightId = FId AND SeatNo = SNo;

    RETURN TRUE;
END;
$$ LANGUAGE plpgsql;
