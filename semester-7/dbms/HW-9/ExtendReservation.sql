CREATE OR REPLACE FUNCTION ExtendReservation(UId INTEGER, Password TEXT, FId INTEGER, SNo VARCHAR(4)) RETURNS BOOLEAN
AS $$
BEGIN
    IF NOT ValidateUser(UId, Password)
    THEN
        RETURN FALSE;
    END IF;

    IF NOT CanReserve(FId)
    THEN
        RETURN FALSE;
    END IF;

    IF EXISTS(SELECT FlightId FROM Orders WHERE FlightId = FId
                                            AND SeatNo = SNo
                                            AND ExpirationTime > NOW()
                                            AND Status = 'reserved')
    THEN
        UPDATE Orders
        SET ExpirationTime = NOW() + INTERVAL '3 day'
        WHERE FlightId = FId AND SeatNo = SNo;
    END IF;

    RETURN TRUE;
END;
$$ LANGUAGE plpgsql;
