-- Какие курсы студент хорошо знает
SELECT CourseId FROM Marks WHERE StudentId = :StudentId AND Mark = 5;
