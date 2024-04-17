SELECT g.GroupId, c.CourseId FROM Groups g, Courses c
WHERE NOT EXISTS(
    SELECT s.StudentId FROM Students s WHERE s.GroupId = g.GroupId
    AND s.StudentId NOT IN
    (SELECT s.StudentId FROM Students s, Marks m WHERE s.GroupId = g.GroupId AND s.StudentId = m.StudentId AND c.CourseId = m.CourseId)
)
