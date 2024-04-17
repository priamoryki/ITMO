SELECT g.GroupName, AVG(AvgMark) AS AvgAvgMark FROM Groups g
LEFT JOIN (
    SELECT g.GroupId, g.GroupName, AVG(CAST(Mark AS FLOAT)) AS AvgMark FROM Students s
    LEFT JOIN Marks m ON m.StudentId = s.StudentId
    LEFT JOIN Groups g ON g.GroupId = s.GroupId
    GROUP BY s.StudentId, s.StudentName, g.GroupId, g.GroupName
) studentAvg ON studentAvg.GroupId = g.GroupId
GROUP BY g.GroupId, g.GroupName
