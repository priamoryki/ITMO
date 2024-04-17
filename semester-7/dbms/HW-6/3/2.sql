SELECT StudentName, CourseName FROM (
    SELECT s.StudentId, p.CourseId FROM Students s, Plan p WHERE s.GroupId = p.GroupId
    UNION
    SELECT m.StudentId, m.CourseId FROM Marks m
) i, Students s, Courses c WHERE i.StudentId = s.StudentId AND i.CourseId = c.CourseId
