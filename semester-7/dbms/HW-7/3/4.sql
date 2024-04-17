UPDATE Students AS s
SET Marks = (SELECT COUNT(DISTINCT CourseId) FROM Marks WHERE StudentId = s.StudentId AND Mark IS NOT NULL)
WHERE TRUE
