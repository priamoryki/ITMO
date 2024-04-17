UPDATE Students AS s
SET Debts = (SELECT COUNT(DISTINCT CourseId)
             FROM Students NATURAL JOIN Plan NATURAL LEFT JOIN Marks
             WHERE StudentId = s.StudentId AND Mark IS NULL)
WHERE s.StudentId = :StudentId
