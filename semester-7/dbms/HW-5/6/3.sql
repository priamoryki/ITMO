SELECT DISTINCT StudentId FROM Marks m1
WHERE NOT EXISTS(
    SELECT CourseId FROM Lecturers NATURAL JOIN Plan WHERE LecturerName = :LecturerName
    EXCEPT
    SELECT m2.CourseId FROM Marks m2 WHERE m2.StudentId = m1.StudentId
)
