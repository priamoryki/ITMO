UPDATE Runs AS r1
SET Accepted = 0
WHERE SubmitTime = (
    SELECT MIN(SubmitTime)
    FROM Runs AS r2
    WHERE r1.SessionId = r2.SessionId AND r1.Letter = r2.Letter
)
