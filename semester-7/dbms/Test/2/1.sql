SELECT DISTINCT TeamId FROM Sessions s, Runs r
WHERE s.SessionId = r.SessionId
  AND Accepted = 0
  AND ContestId = :ContestId
  AND Letter = :Letter
