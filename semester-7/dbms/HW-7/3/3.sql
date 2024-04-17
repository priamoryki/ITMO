UPDATE Students AS s SET Marks = Marks + (SELECT COUNT(Mark) FROM NewMarks WHERE StudentId = s.StudentId)
WHERE TRUE
