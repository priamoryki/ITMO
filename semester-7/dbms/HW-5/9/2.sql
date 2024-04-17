SELECT StudentName, AVG(CAST(Mark AS FLOAT)) AS AvgMark FROM Students s
LEFT JOIN Marks m ON m.StudentId = s.StudentId
GROUP BY s.StudentId, StudentName