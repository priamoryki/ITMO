CREATE EXTENSION pgcrypto;

-- Проверяет, что пользователь вошел в систему
CREATE OR REPLACE FUNCTION ValidateUser(UId INTEGER, Password TEXT) RETURNS BOOLEAN
AS $$
BEGIN
    RETURN EXISTS(SELECT u.UserId FROM Users u WHERE u.UserId = UId AND u.Pass = CRYPT(Password, u.Pass));
END;
$$ LANGUAGE plpgsql;

-- Проверяет существование рейса с заданным идентификатором
CREATE OR REPLACE FUNCTION FlightExists(FId INTEGER) RETURNS BOOLEAN
AS $$
BEGIN
    RETURN EXISTS(SELECT FlightId FROM Flights WHERE FlightId = FId);
END;
$$ LANGUAGE plpgsql;

-- Проверяет, что полет не начнется в течение IntervalBefore
CREATE OR REPLACE FUNCTION HasIntervalBeforeFlight(FId INTEGER, IntervalBefore INTERVAL) RETURNS BOOLEAN
AS $$
DECLARE VarFlightTime TIMESTAMP;
BEGIN
    IF NOT FlightExists(FId)
    THEN
        RETURN FALSE;
    END IF;

    VarFlightTime := (SELECT FlightTime FROM Flights WHERE FlightId = FId);
    IF NOW() + IntervalBefore >= VarFlightTime
    THEN
        RETURN FALSE;
    END IF;

    RETURN TRUE;
END;
$$ LANGUAGE plpgsql;

-- Проверяет возможность зарезервировать место на рейс по времени
CREATE OR REPLACE FUNCTION CanReserve(FId INTEGER) RETURNS BOOLEAN
AS $$
DECLARE VarReserveAvailable BOOLEAN;
BEGIN
    VarReserveAvailable := (SELECT ReserveAvailable FROM Flights WHERE FlightId = FId);
    RETURN VarReserveAvailable AND HasIntervalBeforeFlight(FId, INTERVAL '3 day');
END;
$$ LANGUAGE plpgsql;

-- Проверяет возможность купить билет на рейс по времени
CREATE OR REPLACE FUNCTION CanBuy(FId INTEGER) RETURNS BOOLEAN
AS $$
DECLARE VarBuyAvailable BOOLEAN;
BEGIN
    VarBuyAvailable := (SELECT BuyAvailable FROM Flights WHERE FlightId = FId);
    RETURN VarBuyAvailable AND HasIntervalBeforeFlight(FId, INTERVAL '3 hour');
END;
$$ LANGUAGE plpgsql;

-- В самолете есть свободное место SNo
CREATE OR REPLACE FUNCTION HasFreeSeat(FId INTEGER, SNo VARCHAR(4)) RETURNS BOOLEAN
AS $$
DECLARE PId INTEGER;
BEGIN
    PId := (SELECT PlaneId FROM Flights WHERE FlightId = FId);

    IF SNo NOT IN (SELECT SeatNo FROM Seats WHERE PlaneId = PId)
    THEN
        RETURN FALSE;
    END IF;

    IF SNo IN (SELECT SeatNo FROM OccupiedSeats(FId))
    THEN
        RETURN FALSE;
    END IF;

    RETURN TRUE;
END;
$$ LANGUAGE plpgsql;

-- Возвращает купленные или зарезервированные места на рейс
CREATE OR REPLACE FUNCTION OccupiedSeats(FId INTEGER) RETURNS TABLE(
    SeatNo VARCHAR(4)
)
AS $$
BEGIN
    RETURN QUERY
        SELECT Orders.SeatNo FROM Orders WHERE FlightId = FId
                                    AND (Status = 'bought'
                                             OR (Status = 'reserved' AND ExpirationTime > NOW()));
END;
$$ LANGUAGE plpgsql;
