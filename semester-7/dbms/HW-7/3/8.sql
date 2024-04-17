UPDATE Students AS s
SET Marks = (SELECT COUNT(Mark) FROM Marks WHERE StudentId = s.StudentId),
    Debts = (SELECT COUNT(DISTINCT CourseId)
             FROM Students NATURAL JOIN Plan NATURAL LEFT JOIN Marks
             WHERE StudentId = s.StudentId AND Mark IS NULL)
WHERE TRUE
