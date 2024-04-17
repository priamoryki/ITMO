SELECT DISTINCT s.StudentId FROM Students s
WHERE s.StudentId NOT IN(
SELECT DISTINCT s.StudentId FROM Students s, Marks m, Plan p, Lecturers l
WHERE s.StudentId = m.StudentId AND s.GroupId = p.GroupId AND m.CourseId = p.CourseId AND p.LecturerId = l.LecturerId AND l.LecturerName = :LecturerName)
