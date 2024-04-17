SELECT TeamName FROM Teams WHERE TeamId NOT IN (
    SELECT TeamId FROM Sessions s, Runs r WHERE s.SessionId = r.SessionId
                                            AND Accepted = 1
                                            AND ContestId = :ContestId
                                            AND Letter = :Letter
)
