SELECT StudentName, CourseName FROM (
    SELECT StudentId, StudentName, CourseName FROM Students NATURAL JOIN Plan NATURAL JOIN Courses
    EXCEPT
    SELECT StudentId, StudentName, CourseName FROM Students NATURAL JOIN Marks NATURAL JOIN Courses WHERE Mark = 4 OR Mark = 5
) AS c
