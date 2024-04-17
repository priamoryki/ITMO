-- Помогает получать оценки студентов по курсу
CREATE INDEX Marks_CourseId_StudentId_Mark_Index ON Marks USING btree (CourseId, StudentId, Mark);

-- Помогает получать студентов из группы
CREATE INDEX Students_GroupId_StudentId_Index ON Students USING btree (GroupId, StudentId);
