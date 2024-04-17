CREATE OR REPLACE FUNCTION BuyFree(FId INTEGER, SNo VARCHAR(4)) RETURNS BOOLEAN
AS $$
BEGIN
    IF NOT CanBuy(FId)
    THEN
        RETURN FALSE;
    END IF;

    IF NOT HasFreeSeat(FId, SNo)
    THEN
        RETURN FALSE;
    END IF;

    INSERT INTO Orders(FlightId, SeatNo, UserId, ExpirationTime, Status)
    VALUES (FId, SNo, NULL, NULL, 'bought');

    RETURN TRUE;
END;
$$ LANGUAGE plpgsql;
