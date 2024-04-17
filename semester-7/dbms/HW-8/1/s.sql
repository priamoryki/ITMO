-- PK для таблицы Students
-- ДЗ-5.1.1. Информация о студентах с заданным идентификатором
-- ДЗ-5.2.1. Полная информация о студентах с заданным идентификатором
-- ДЗ-6.1.3. Информация о студентах с заданной оценкой по дисциплине, заданной идентификатором
CREATE UNIQUE INDEX Students_StudentId_Index ON Students USING hash (StudentId);

-- Нужен для join'а с таблицей Groups
-- ДЗ-5.8.3. Суммарный балл каждой группы
-- ДЗ-5.9.3. Средний балл каждой группы
-- ДЗ-5.10. Статистика студентов
CREATE INDEX Students_GroupId_Index ON Students USING hash (GroupId);

-- Нужен для поиска студентов по имени
-- ДЗ-5.1.2. Информацию о студентах с заданным ФИО
-- ДЗ-5.2.2. Полную информацию о студентах с заданным ФИО
-- ДЗ-6.1.1. Информацию о студентах с заданным ФИО
CREATE UNIQUE INDEX Students_StudentName_StudentId_Index ON Students USING btree (StudentName, StudentId);
