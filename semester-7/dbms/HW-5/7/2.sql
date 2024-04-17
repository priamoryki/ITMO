SELECT GroupName, CourseName FROM (
    SELECT DISTINCT GroupId, CourseId FROM Marks CROSS JOIN Students
    EXCEPT
    SELECT GroupId, CourseId
    FROM (
        SELECT GroupId, s.StudentId, CourseId FROM Marks CROSS JOIN Students s
        EXCEPT
        SELECT GroupId, StudentId, CourseId FROM Marks NATURAL JOIN Students
    ) AS query1
) AS query2 NATURAL JOIN Groups NATURAL JOIN Courses
