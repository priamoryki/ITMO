SELECT DISTINCT StudentId FROM Students NATURAL JOIN (
    SELECT StudentId, GroupId FROM Marks CROSS JOIN (SELECT GroupId FROM Plan NATURAL JOIN Lecturers WHERE LecturerName = :LecturerName) AS query1
    EXCEPT
    SELECT StudentId, GroupId FROM (
        SELECT StudentId, query2.CourseId, GroupId FROM Marks CROSS JOIN (SELECT CourseId, GroupId FROM Plan p NATURAL JOIN Lecturers WHERE LecturerName = :LecturerName) AS query2
        EXCEPT
        SELECT StudentId, CourseId, GroupId FROM Marks NATURAL JOIN Plan p NATURAL JOIN Lecturers WHERE LecturerName = :LecturerName
    ) AS query3
) AS query4
