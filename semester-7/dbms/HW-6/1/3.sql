SELECT s.StudentId, s.StudentName, s.GroupId FROM Students s, Marks m WHERE s.StudentId = m.StudentId AND m.Mark = :Mark AND m.CourseId = :CourseId
