SELECT s.StudentId, s.StudentName, s.GroupId FROM Students s, Groups g WHERE s.GroupId = g.GroupId AND GroupName = :GroupName
