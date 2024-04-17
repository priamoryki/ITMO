SELECT StudentName, CourseName FROM (
    SELECT StudentId, StudentName, CourseName FROM Students NATURAL JOIN Plan NATURAL JOIN Courses
    EXCEPT
    SELECT StudentId, StudentName, CourseName FROM Students NATURAL JOIN Marks NATURAL JOIN Courses
) AS c
