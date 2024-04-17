UPDATE Students AS s SET Marks = (SELECT COUNT(Mark) FROM Marks WHERE StudentId = s.StudentId)
WHERE StudentId = :StudentId
