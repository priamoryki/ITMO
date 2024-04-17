SELECT StudentName, CourseName FROM (
    SELECT s.StudentId, p.CourseId FROM Students s, Plan p WHERE s.GroupId = p.GroupId
    EXCEPT
    SELECT s.StudentId, p.CourseId FROM Students s, Plan p, Marks m WHERE s.GroupId = p.GroupId AND s.StudentId = m.StudentId AND p.CourseId = m.CourseId
) i, Students s, Courses c WHERE i.StudentId = s.StudentId AND i.CourseId = c.CourseId
