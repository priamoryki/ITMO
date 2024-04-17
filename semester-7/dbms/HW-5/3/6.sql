SELECT s.StudentId, s.StudentName, s.GroupId
FROM Plan
    NATURAL JOIN Lecturers
    NATURAL JOIN Marks m
    INNER JOIN Students s ON s.StudentId = m.StudentId
WHERE Mark = :Mark AND LecturerName = :LecturerName
