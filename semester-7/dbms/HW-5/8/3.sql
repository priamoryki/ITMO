SELECT GroupName, SUM(Mark) AS SumMark FROM Groups g
LEFT JOIN Students s ON s.GroupId = g.GroupId
LEFT JOIN Marks m ON m.StudentId = s.StudentId
GROUP BY g.GroupId, g.GroupName