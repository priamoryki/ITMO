SELECT s.StudentId, s.StudentName, g.GroupName FROM Students s, Groups g, Plan p
    WHERE s.GroupId = g.GroupId AND g.GroupId = p.GroupId AND p.CourseId = :CourseId
EXCEPT
SELECT s.StudentId, s.StudentName, g.GroupName FROM Students s, Groups g, Plan p, Marks m
    WHERE s.GroupId = g.GroupId AND g.GroupId = p.GroupId AND s.StudentId = m.StudentId AND m.CourseId = :CourseId
