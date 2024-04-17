-- Ведет ли лектор заданный курс
SELECT COUNT(CourseId) > 0 FROM Plan WHERE LecturerId = :LecturerId AND CourseId = :CourseId;
