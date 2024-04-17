-- PK для таблицы Groups
-- ДЗ-5.1.2. Информацию о студентах с заданным ФИО
-- ДЗ-5.2.2. Полную информацию о студентах с заданным ФИО
-- ДЗ-6.1.1. Информацию о студентах с заданным ФИО
CREATE UNIQUE INDEX Groups_GroupId_Index ON Groups USING hash (GroupId);

-- Индекс по уникальным названиям групп
CREATE UNIQUE INDEX Groups_GroupName_Index ON Groups USING hash (GroupName);

-- Нужен для поиска групп по имени
-- ДЗ-6.1.2. Информацию о студентах учащихся в заданной группе
-- ДЗ-7.1.2. Удаление студентов учащихся в группе, заданной названием
-- ДЗ-7.2.4. Перевод всех студентов из группы в группу по названиям
CREATE UNIQUE INDEX Groups_GroupName_GroupId_Index ON Groups USING btree (GroupName, GroupId);
