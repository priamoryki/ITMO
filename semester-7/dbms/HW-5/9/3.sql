SELECT GroupName, AVG(CAST(Mark AS FLOAT)) AS AvgMark FROM Groups g
LEFT JOIN Students s ON s.GroupId = g.GroupId
LEFT JOIN Marks m ON m.StudentId = s.StudentId
GROUP BY g.GroupId, g.GroupName