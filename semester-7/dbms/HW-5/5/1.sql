SELECT StudentName, CourseName FROM Students NATURAL JOIN Plan NATURAL JOIN Courses GROUP BY StudentId, StudentName, CourseName
