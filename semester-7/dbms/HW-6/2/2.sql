SELECT s.StudentId, s.StudentName, g.GroupName FROM Students s, Groups g WHERE s.GroupId = g.GroupId
AND s.StudentId NOT IN(
SELECT s.StudentId FROM Students s, Groups g, Marks m
    WHERE s.GroupId = g.GroupId AND s.StudentId = m.StudentId AND m.CourseId = :CourseId)
