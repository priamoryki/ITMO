SELECT s.StudentId FROM Students s
WHERE NOT EXISTS(
    SELECT c.CourseId FROM (SELECT s.StudentId, p.CourseId FROM Students s, Lecturers l, Plan p WHERE l.LecturerId = p.LecturerId AND s.GroupId = p.GroupId AND l.LecturerName = :LecturerName) c
    WHERE s.StudentId = c.StudentId
    AND c.CourseId NOT IN(
    SELECT c.CourseId FROM (SELECT s.StudentId, p.CourseId FROM Students s, Lecturers l, Plan p WHERE l.LecturerId = p.LecturerId AND s.GroupId = p.GroupId AND l.LecturerName = :LecturerName) c, Marks m
    WHERE s.StudentId = c.StudentId AND s.StudentId = m.StudentId AND c.CourseId = m.CourseId)
)

