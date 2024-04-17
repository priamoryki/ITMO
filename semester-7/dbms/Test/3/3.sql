INSERT INTO Sessions(TeamId, ContestId, Start)
SELECT TeamId, ContestId, current_timestamp
FROM Sessions WHERE ContestId = :ContestId
