pi{RunId, TeamId, SubmitTime, Accepted}(
    select{ContestId = :ContestId && Letter = :Letter}(Sessions nj Runs)
)
