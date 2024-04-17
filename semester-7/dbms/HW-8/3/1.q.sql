-- Студенты с максимальной оценкой по курсу
SELECT * FROM Students NATURAL JOIN MARKS
WHERE CourseId = :CourseId
  AND Mark = (SELECT MAX(Mark) FROM Marks WHERE CourseId = :CourseId);
