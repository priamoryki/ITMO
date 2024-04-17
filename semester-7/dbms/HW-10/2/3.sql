-- Пользователь может анонимно купить место без авторизации (BuyFree)
START TRANSACTION READ WRITE ISOLATION LEVEL SNAPSHOT;
SELECT * FROM BuyFree(:Flightid, :SeatNo);
COMMIT;

-- Авторизованный пользователь может забронировать место на 3 дня (Reserve)
START TRANSACTION READ WRITE ISOLATION LEVEL SNAPSHOT;
SELECT * FROM Reserve(:UserId, :Pass, :FlightId, :SeatNo);
COMMIT;

-- Авторизованный пользователь может продлить бронирование места на 3 дня (ExtendReservation)
START TRANSACTION READ WRITE ISOLATION LEVEL REPEATABLE READ;
SELECT * FROM ExtendReservation(:UserId, :Pass, :FlightId, :SeatNo);
COMMIT;

-- Авторизованный пользователь может купить ранее забронированное место (BuyReserved)
START TRANSACTION READ WRITE ISOLATION LEVEL REPEATABLE READ;
SELECT * FROM BuyReserved(:UserId, :Pass, :FlightId, :SeatNo);
COMMIT;
