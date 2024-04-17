-- Поскольку при выполнении CompressSeats происходит и чтение и запись, то режим READ WRITE;
-- Чтение фантомов может произойти при параллельном исполнении вместе с BuyFree;
-- Косой записи не будет, т.к. нет UPDATE;
-- Поскольку косой записи не произойдет можем использовать SNAPSHOT;
START TRANSACTION READ WRITE ISOLATION LEVEL SNAPSHOT;
