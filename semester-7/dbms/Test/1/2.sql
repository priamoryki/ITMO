SELECT TeamName FROM Sessions NATURAL JOIN Teams
WHERE ContestId = :ContestId
GROUP BY TeamId, TeamName
