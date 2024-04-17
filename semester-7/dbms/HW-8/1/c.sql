-- PK для таблицы Groups
-- ДЗ-5.5.1. Для каждого студента ФИО и названия дисциплин, которые у него есть по плану
-- ДЗ-5.5.2. Для каждого студента ФИО и названия дисциплин, которые у него есть по плану, но у него нет оценки
-- ДЗ-5.5.3. Для каждого студента ФИО и названия дисциплин, которые у него есть по плану, но у него не 4 или 5
CREATE UNIQUE INDEX Courses_CourseId_Index ON Courses USING hash (CourseId);

-- Нужен для поиска курсов по имени
-- ДЗ-5.4.1. Информацию о студентах не имеющих оценки по дисциплине среди всех студентов
-- ДЗ-5.4.2. Информацию о студентах не имеющих оценки по дисциплине среди студентов, у которых есть эта дисциплина
-- ДЗ-6.1.4. Информацию о студентах с заданной оценкой по дисциплине заданной названием
CREATE UNIQUE INDEX Courses_CourseName_CourseId_Index ON Courses USING btree (CourseName, CourseId);
