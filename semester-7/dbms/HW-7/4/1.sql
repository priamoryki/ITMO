INSERT INTO Marks (StudentId, CourseId, Mark)
SELECT nm.StudentId, nm.CourseId, nm.Mark FROM NewMarks AS nm LEFT JOIN Marks AS m
ON nm.StudentId = m.StudentId AND nm.CourseId = m.CourseId WHERE m.Mark IS NULL
