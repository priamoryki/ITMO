CREATE TABLE groups
(
    group_id   INT         NOT NULL UNIQUE,
    group_name VARCHAR(50) NOT NULL,
    CONSTRAINT pk_groups PRIMARY KEY (group_id)
);

CREATE TABLE students
(
    student_id   INT         NOT NULL,
    student_name VARCHAR(50) NOT NULL,
    group_id     INT         NOT NULL,
    CONSTRAINT pk_students PRIMARY KEY (student_id),
    CONSTRAINT fk_students_groups_id FOREIGN KEY (group_id) REFERENCES groups (group_id)
);

CREATE TABLE lecturers
(
    lecturer_id   INT         NOT NULL,
    lecturer_name VARCHAR(50) NOT NULL,
    CONSTRAINT pk_lecturers PRIMARY KEY (lecturer_id)
);

CREATE TABLE courses
(
    course_id   INT         NOT NULL,
    course_name VARCHAR(50) NOT NULL,
    CONSTRAINT pk_course PRIMARY KEY (course_id)
);

CREATE TABLE teaching_plan
(
    group_id    INT NOT NULL,
    course_id   INT NOT NULL,
    lecturer_id INT NOT NULL,
    CONSTRAINT pk_teaching_plan PRIMARY KEY (group_id, course_id),
    CONSTRAINT fk_teaching_plan_group_id FOREIGN KEY (group_id) REFERENCES groups (group_id),
    CONSTRAINT fk_teaching_plan_course_id FOREIGN KEY (course_id) REFERENCES courses (course_id),
    CONSTRAINT fk_teaching_plan_lecturer_id FOREIGN KEY (lecturer_id) REFERENCES lecturers (lecturer_id)
);

CREATE TABLE marks
(
    course_id  INT NOT NULL,
    student_id INT NOT NULL,
    mark       INT NOT NULL,
    CONSTRAINT pk_marks PRIMARY KEY (student_id, course_id),
    CONSTRAINT fk_marks_course_id FOREIGN KEY (course_id) REFERENCES courses (course_id),
    CONSTRAINT fk_marks_student_id FOREIGN KEY (student_id) REFERENCES students (student_id)
);
