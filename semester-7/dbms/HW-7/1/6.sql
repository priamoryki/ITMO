DELETE FROM Students WHERE StudentId IN (
    SELECT StudentId FROM Students NATURAL JOIN Plan NATURAL LEFT JOIN Marks WHERE Mark IS NULL
)
