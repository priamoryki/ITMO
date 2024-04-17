SELECT AVG(CAST(Mark AS FLOAT)) AS AvgMark FROM Students NATURAL JOIN Marks
WHERE GroupId = :GroupId AND CourseId = :CourseId;
