INSERT INTO groups (group_id, group_name)
VALUES (1, 'M34361'),
       (2, 'M34371'),
       (3, 'M34381'),
       (4, 'M34391');

INSERT INTO students (student_id, student_name, group_id)
VALUES (1, 'Константин Бац', 1),
       (2, 'Артём Котон', 2),
       (3, 'Михаил Рузавин', 2);

INSERT INTO lecturers (lecturer_id, lecturer_name)
VALUES (1, 'Андрей Станкевич'),
       (2, 'Павел Маврин');

INSERT INTO courses (course_id, course_name)
VALUES (1, 'Методы Трансляций'),
       (2, 'Дискретная математика'),
       (3, 'Алгоритмы и структуры данных');

INSERT INTO teaching_plan (group_id, course_id, lecturer_id)
VALUES (1, 1, 1),
       (2, 1, 1),
       (3, 1, 1),
       (4, 1, 1),
       (2, 2, 1),
       (2, 3, 2);

INSERT INTO marks (course_id, student_id, mark)
VALUES (3, 1, 4),
       (1, 2, 5),
       (1, 3, 5),
       (2, 2, 5);
