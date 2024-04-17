SELECT s.StudentName, c.CourseName FROM (
    SELECT s.StudentId, p.CourseId FROM Students s, Plan p WHERE s.GroupId = p.GroupId
    EXCEPT
    SELECT m.StudentId, m.CourseId FROM Marks m WHERE m.Mark > 2
) d, Students s, Courses c WHERE d.StudentId = s.StudentId AND d.CourseId = c.CourseId
