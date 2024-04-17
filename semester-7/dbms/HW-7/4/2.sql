UPDATE Marks AS m
SET Mark = (SELECT nm.Mark FROM NewMarks AS nm WHERE nm.StudentId = m.StudentId AND nm.CourseId = m.CourseId)
WHERE EXISTS(
    SELECT nm.Mark FROM NewMarks AS nm WHERE nm.StudentId = m.StudentId AND nm.CourseId = m.CourseId
)
