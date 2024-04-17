-- PostgreSQL (v0.15.1)
-- Инвариант: все студенты из таблицы Students имеют оценки только по предметам из из плана.
-- Для каждой таблицы рассматриваю 3 операции: INSERT, UPDATE, DELETE и проверяю инвариант.
-- ВАЖНО! Перед запуском инвариант должен выполняться. Делаю это процедурой checkNoExtraMarks.

CREATE OR REPLACE PROCEDURE checkNoExtraMarks() AS $$
BEGIN
    IF EXISTS(SELECT CourseId FROM Marks NATURAL JOIN Students NATURAL LEFT JOIN Plan WHERE LecturerId IS NULL)
    THEN
        RAISE EXCEPTION 'Some of the students have extra marks';
    END IF;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION applyNoExtraMarksForMarks() RETURNS TRIGGER AS $$
BEGIN
    IF EXISTS(SELECT CourseId FROM Plan AS p NATURAL JOIN Students AS s WHERE p.CourseId = NEW.CourseId AND s.StudentId = NEW.StudentId)
    THEN
        RETURN NEW;
    END IF;
    RAISE EXCEPTION 'Student % has extra mark by course %', NEW.StudentId, NEW.CourseId;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION applyNoExtraMarksForStudents() RETURNS TRIGGER AS $$
BEGIN
    IF EXISTS(SELECT Mark FROM Marks AS m WHERE m.StudentId = NEW.StudentId AND m.CourseId NOT IN (SELECT CourseId FROM Plan AS p WHERE p.GroupId = NEW.GroupId))
    THEN
        RAISE EXCEPTION 'Student % has extra mark in group %', NEW.StudentId, NEW.GroupId;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION applyNoExtraMarksForPlan() RETURNS TRIGGER AS $$
BEGIN
    IF EXISTS(SELECT StudentId FROM Students AS s NATURAL JOIN Marks AS m WHERE s.GroupId = OLD.GroupId AND m.CourseId = OLD.CourseId)
    THEN
        RAISE EXCEPTION 'Some students from group % have extra marks by course %', OLD.GroupId, OLD.CourseId;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- INSERT: Могли испортить инвариант, если добавили оценку по курсу, которого нет в плане студента.
-- UPDATE: Могли испортить инвариант, если добавили оценку по курсу, которого нет в плане студента.
-- DELETE: Инвариант не испорчен. Каждый студент группы и так не имеет оценок по лишним предметам.
CREATE OR REPLACE TRIGGER NoExtraMarksForMarks
    BEFORE INSERT OR UPDATE OF StudentId, CourseId ON Marks
    FOR EACH ROW EXECUTE PROCEDURE applyNoExtraMarksForMarks();

-- INSERT: Инвариант не выполняется заранее (есть студент с оценками не из таблицы Students) - невалидно.
-- UPDATE: Могли испортить инвариант, если добавили студента у которого были лишние оценки.
-- DELETE: Инвариант не испорчен. Каждый студент группы и так не имеет оценок по лишним предметам.
CREATE OR REPLACE TRIGGER NoExtraMarksForStudents
    BEFORE UPDATE OF StudentId, GroupId ON Students
    FOR EACH ROW EXECUTE PROCEDURE applyNoExtraMarksForStudents();

-- INSERT: Инвариант не испорчен. Каждый студент группы и так не имеет оценок по лишним предметам.
-- UPDATE: Могли испортить инвариант из-за того, что не удалили оценки у студентов старой группы остался старый курс.
-- DELETE: Могли испортить инвариант из-за того, что не удалили оценки у студентов старой группы остался старый курс.
CREATE OR REPLACE TRIGGER NoExtraMarksForPlan
    BEFORE DELETE OR UPDATE OF GroupId, CourseId ON Plan
    FOR EACH ROW EXECUTE PROCEDURE applyNoExtraMarksForPlan();

-- Проверка выполнения инварианта при добавлении триггеров.
CALL checkNoExtraMarks();
