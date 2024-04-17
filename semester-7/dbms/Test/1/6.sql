SELECT ProblemName FROM (
    SELECT Letter FROM Problems
    EXCEPT
    SELECT Letter FROM Runs NATURAL JOIN Problems
) q NATURAL JOIN Problems
