CREATE OR REPLACE FUNCTION FreeSeats(FId INTEGER) RETURNS TABLE(
    SeatNo VARCHAR(4)
)
AS $$
DECLARE PId INTEGER;
BEGIN
    IF NOT CanReserve(FId)
    THEN
        RETURN;
    END IF;

    PId := (SELECT PlaneId FROM Flights WHERE FlightId = FId);
    RETURN QUERY
        SELECT Seats.SeatNo FROM Seats WHERE PlaneId = PId
        EXCEPT
        SELECT OccupiedSeats.SeatNo FROM OccupiedSeats(FId);
END;
$$ LANGUAGE plpgsql;
