SELECT s.StudentId, p.CourseId FROM Students s, Plan p WHERE s.GroupId = p.GroupId
UNION
SELECT m.StudentId, m.CourseId FROM Marks m
