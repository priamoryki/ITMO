-- PK для таблицы Lecturers
CREATE UNIQUE INDEX Lecturers_LecturerId_Index ON Lecturers USING hash (LecturerId);

-- Нужен для поиска лекторов по имени
-- ДЗ-5.3.4. Информацию о студентах с заданной оценкой по дисциплине, которую у них вёл лектор, заданный ФИО
-- ДЗ-5.6.1. Идентификаторы студентов по преподавателю, имеющих хотя бы одну оценку у преподавателя
-- ДЗ-5.6.2. Идентификаторы студентов по преподавателю, не имеющих ни одной оценки у преподавателя
CREATE UNIQUE INDEX Lecturers_LecturerName_ LecturerId_Index ON Lecturers USING btree (LecturerName, LecturerId);
