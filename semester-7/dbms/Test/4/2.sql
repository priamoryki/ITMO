SELECT TeamId, COUNT(Letter) AS Opened
FROM (
    SELECT DISTINCT TeamId, ContestId, Letter FROM Sessions NATURAL JOIN Runs
) q GROUP BY TeamId
