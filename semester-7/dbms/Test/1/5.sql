SELECT ProblemName FROM (
    SELECT Letter FROM Problems
    EXCEPT
    SELECT Letter FROM Runs NATURAL JOIN Problems WHERE Accepted = 1
) q NATURAL JOIN Problems
