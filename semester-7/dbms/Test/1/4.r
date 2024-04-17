pi{RunId, SessionId, Letter, SubmitTime}(
    select{ContestId = :ContestId && Accepted = 0}(Sessions nj Runs)
)
