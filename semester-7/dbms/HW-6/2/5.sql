SELECT s.StudentId, s.StudentName, g.GroupName FROM Students s, Groups g, Plan p, Courses c
    WHERE s.GroupId = g.GroupId AND g.GroupId = p.GroupId AND p.CourseId = c.CourseId AND c.CourseName = :CourseName
EXCEPT
SELECT s.StudentId, s.StudentName, g.GroupName FROM Students s, Groups g, Plan p, Marks m, Courses c
    WHERE s.GroupId = g.GroupId AND g.GroupId = p.GroupId AND p.CourseId = m.CourseId AND s.StudentId = m.StudentId AND p.CourseId = c.CourseId AND c.CourseName = :CourseName
