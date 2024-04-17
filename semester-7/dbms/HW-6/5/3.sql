SELECT s.StudentId FROM Students s
WHERE NOT EXISTS(
    SELECT c.CourseId FROM (SELECT p.CourseId FROM Lecturers l, Plan p WHERE l.LecturerId = p.LecturerId AND l.LecturerName = :LecturerName) c
    WHERE c.CourseId NOT IN(
    SELECT c.CourseId FROM (SELECT p.CourseId FROM Lecturers l, Plan p WHERE l.LecturerId = p.LecturerId AND l.LecturerName = :LecturerName) c, Marks m
    WHERE s.StudentId = m.StudentId AND c.CourseId = m.CourseId)
)
