-- Таблица рейсов с возможностью включать бронирование и покупку
CREATE TABLE Flights
(
    FlightId         INTEGER PRIMARY KEY,
    FlightTime       TIMESTAMP NOT NULL,
    PlaneId          INTEGER   NOT NULL,
    ReserveAvailable BOOLEAN DEFAULT TRUE,
    BuyAvailable     BOOLEAN DEFAULT TRUE
);

-- Таблица мест в зависимости от модели самолета
CREATE TABLE Seats
(
    PlaneId INTEGER    NOT NULL,
    SeatNo  VARCHAR(4) NOT NULL,
    CONSTRAINT flights_fk PRIMARY KEY (PlaneId, SeatNo)
);

-- Таблица юзеров и salt'ов паролей
CREATE TABLE Users
(
    UserId INTEGER PRIMARY KEY,
    Pass   TEXT NOT NULL
);

-- Статусы заказов
CREATE TYPE OrderStatus AS ENUM ('reserved', 'bought');

-- Таблица заказов
CREATE TABLE Orders
(
    FlightId       INTEGER     NOT NULL,
    SeatNo         VARCHAR(4)  NOT NULL,
    UserId         INTEGER,
    ExpirationTime TIMESTAMP,
    Status         OrderStatus NOT NULL,
    CONSTRAINT orders_pk PRIMARY KEY (FlightId, SeatNo),
    CONSTRAINT orders_flights_fk FOREIGN KEY (FlightId) REFERENCES Flights (FlightId),
    CONSTRAINT orders_users_fk FOREIGN KEY (UserId) REFERENCES Users (UserId)
);
