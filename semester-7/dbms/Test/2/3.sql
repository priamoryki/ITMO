SELECT DISTINCT TeamId FROM Sessions s, Runs r
WHERE s.SessionId = r.SessionId AND ContestId = :ContestId AND Accepted = 0
