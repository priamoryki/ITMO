CREATE OR REPLACE FUNCTION Reserve(UId INTEGER, Password TEXT, FId INTEGER, SNo VARCHAR(4)) RETURNS BOOLEAN
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

    IF NOT HasFreeSeat(FId, SNo)
    THEN
        RETURN FALSE;
    END IF;

    INSERT INTO Orders(FlightId, SeatNo, UserId, ExpirationTime, Status)
    VALUES (FId, SNo, UId, NOW() + INTERVAL '3 day', 'reserved')
    ON CONFLICT (FlightId, SeatNo) WHERE Status = 'reserved' DO UPDATE
        SET UserId = UId, ExpirationTime = NOW() + INTERVAL '3 day';

    RETURN TRUE;
END;
$$ LANGUAGE plpgsql;
