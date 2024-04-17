CREATE OR REPLACE PROCEDURE CompressSeats(FId INTEGER)
AS $$
DECLARE PlaneSeats CURSOR FOR SELECT SeatNo FROM Flights NATURAL JOIN Seats WHERE FlightId = FId ORDER BY SeatNo;
DECLARE PlaneSeat VARCHAR(4);
DECLARE UId INTEGER;
DECLARE ET TIMESTAMP;
DECLARE S OrderStatus;
BEGIN
    IF NOT FlightExists(FId)
    THEN
        RETURN;
    END IF;

    CREATE TEMPORARY TABLE TempOrders AS (SELECT * FROM Orders WHERE FlightId = FId);
    DELETE FROM Orders WHERE FlightId = FId;

    OPEN PlaneSeats;

    FOR UId, ET, S IN SELECT UserId, ExpirationTime, Status FROM TempOrders WHERE FlightId = FId AND Status = 'bought'
    LOOP
        FETCH NEXT FROM PlaneSeats INTO PlaneSeat;
        INSERT INTO Orders(FlightId, SeatNo, UserId, ExpirationTime, Status)
        VALUES (FId, PlaneSeat, UId, ET, S);
    END LOOP;

    FOR UId, ET, S IN SELECT UserId, ExpirationTime, Status FROM TempOrders WHERE FlightId = FId AND Status = 'reserved'
                                                                              AND ExpirationTime > NOW()
    LOOP
        FETCH NEXT FROM PlaneSeats INTO PlaneSeat;
        INSERT INTO Orders(FlightId, SeatNo, UserId, ExpirationTime, Status)
        VALUES (FId, PlaneSeat, UId, ET, S);
    END LOOP;

    CLOSE PlaneSeats;
END;
$$ LANGUAGE plpgsql;
