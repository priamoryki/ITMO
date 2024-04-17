UPDATE Runs AS r1
SET Accepted = 1
WHERE SubmitTime = (
    SELECT MAX(SubmitTime)
    FROM Runs AS r2
    WHERE r1.SessionId = r2.SessionId AND Accepted = 0
)
