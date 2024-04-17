INSERT INTO univercity_group (group_id, group_name)
VALUES (1, 'M34361'),
       (2, 'M34371'),
       (3, 'M34381'),
       (4, 'M34391');

INSERT INTO student (student_id, first_name, second_name, group_id)
VALUES (1, 'Константин', 'Бац', 1),
       (2, 'Артём', 'Котон', 2),
       (3, 'Михаил', 'Рузавин', 2);

INSERT INTO teacher (teacher_id, first_name, second_name)
VALUES (1, 'Андрей', 'Станкевич'),
       (2, 'Павел', 'Маврин');

INSERT InTO course (course_id, course_name, teacher_id)
VALUES (1, 'Методы Трансляций', 1),
       (2, 'Дискретная математика', 1),
       (3, 'Алгоритмы и структуры данных', 2);

INSERT INTO course_participant (participant_id, student_id, course_id, mark)
VALUES (1, 1, 1, 4),
       (2, 1, 2, 5),
       (3, 1, 3, 5),
       (4, 2, 2, 5),
       (5, 2, 3, 5);
