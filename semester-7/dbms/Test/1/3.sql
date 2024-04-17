SELECT RunId, TeamId, SubmitTime, Accepted FROM Sessions NATURAL JOIN Runs
WHERE ContestId = :ContestId AND Letter = :Letter
