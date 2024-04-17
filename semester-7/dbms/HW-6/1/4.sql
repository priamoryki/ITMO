SELECT s.StudentId, s.StudentName, s.GroupId FROM Students s, Marks m, Courses c
WHERE s.StudentId = m.StudentId AND m.CourseId = c.CourseId AND m.Mark = :Mark AND c.CourseName = :CourseName
