MERGE INTO Marks AS m
USING NewMarks AS nm
ON nm.StudentId = m.StudentId AND nm.CourseId = m.CourseId
WHEN MATCHED AND m.Mark < nm.Mark THEN UPDATE SET m.Mark = nm.Mark
WHEN NOT MATCHED THEN INSERT (StudentId, CourseId, Mark) VALUES (nm.StudentId, nm.CourseId, nm.Mark)
