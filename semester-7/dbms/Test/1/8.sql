SELECT DISTINCT Letter, ContestId FROM Sessions, Runs
EXCEPT
SELECT Letter, ContestId FROM (
    SELECT r.Letter, s.ContestId, s.SessionId FROM Sessions s, Runs r
    EXCEPT
    SELECT r.Letter, s.ContestId, s.SessionId FROM Runs r INNER JOIN Sessions s on r.SessionId = s.SessionId
) q
