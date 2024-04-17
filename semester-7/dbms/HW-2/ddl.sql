CREATE TABLE university_group
(
	group_id   INT         NOT NULL,
	group_name VARCHAR(50) NOT NULL,
	CONSTRAINT pk_group PRIMARY KEY (group_id)
);

CREATE TABLE student
(
	student_id  INT         NOT NULL,
	first_name  VARCHAR(50) NOT NULL,
	second_name VARCHAR(50) NOT NULL,
	group_id    INT         NOT NULL,
	CONSTRAINT pk_student PRIMARY KEY (student_id),
	CONSTRAINT fk_student_group_id FOREIGN KEY (group_id) REFERENCES univercity_group (group_id)
);

CREATE TABLE teacher
(
	teacher_id  INT         NOT NULL,
	first_name  VARCHAR(50) NOT NULL,
	second_name VARCHAR(50) NOT NULL,
	CONSTRAINT pk_teacher PRIMARY KEY (teacher_id)
);

CREATE TABLE course
(
	course_id   INT         NOT NULL,
	course_name VARCHAR(50) NOT NULL,
	teacher_id  INT         NOT NULL,
	CONSTRAINT pk_course PRIMARY KEY (course_id),
	CONSTRAINT fk_course_teacher_id FOREIGN KEY (teacher_id) REFERENCES teacher (teacher_id)
);

CREATE TABLE course_participant
(
	participant_id INT NOT NULL
	student_id     INT NOT NULL,
	course_id      INT NOT NULL,
	mark           INT NOT NULL,
	CONSTRAINT pk_course_participant PRIMARY KEY (participant_id),
	CONSTRAINT fk_course_participant_student_id FOREIGN KEY (student_id) REFERENCES student (student_id),
	CONSTRAINT fk_course_participant_course_id FOREIGN KEY (course_id) REFERENCES course (course_id)
);
