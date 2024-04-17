SELECT s.StudentName, c.CourseName FROM Students s, Plan p, Marks m, Courses c
WHERE s.GroupId = p.GroupId AND s.StudentId = m.StudentId AND p.CourseId = m.CourseId AND m.CourseId = c.CourseId AND m.Mark <= 2
GROUP BY s.StudentId, s.StudentName, c.CourseId, c.CourseName
