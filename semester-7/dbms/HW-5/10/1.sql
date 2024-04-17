SELECT s.StudentId,
       COUNT(DISTINCT p.CourseId) AS Total,
       COUNT(DISTINCT m.CourseId) AS Passed,
       COUNT(DISTINCT p.CourseId) - COUNT(DISTINCT m.CourseId) AS Failed
FROM Students s
LEFT JOIN Plan p ON p.GroupId = s.GroupId
LEFT JOIN Marks m ON m.StudentId = s.StudentId AND m.CourseId = p.CourseId
GROUP BY s.StudentId