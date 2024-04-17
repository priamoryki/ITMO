CREATE VIEW StudentDebts AS
SELECT StudentId, COALESCE(InnerDebts, 0) AS Debts
FROM Students NATURAL LEFT JOIN (
    SELECT StudentId, COUNT(DISTINCT CourseId) AS InnerDebts
    FROM Students NATURAL JOIN Plan NATURAL LEFT JOIN Marks
    WHERE Mark IS NULL
    GROUP BY StudentId
) AS query
