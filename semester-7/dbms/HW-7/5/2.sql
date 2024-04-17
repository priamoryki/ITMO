CREATE VIEW AllMarks AS
SELECT StudentId, COUNT(Mark) AS Marks
FROM Students NATURAL LEFT JOIN (
    SELECT StudentId, CourseId, Mark FROM Marks
    UNION ALL
    SELECT StudentId, CourseId, Mark FROM NewMarks
) AS query GROUP BY StudentId
