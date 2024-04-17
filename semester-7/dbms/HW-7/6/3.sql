-- PostgreSQL (v0.15.1)
-- Исходя из ФЗ предполагаю, что StudentId и CourseId находятся в Primary Key => создать уже существующую оценку не выйдет.

CREATE OR REPLACE FUNCTION applyPreserveMarks() RETURNS TRIGGER AS $$
BEGIN
    IF NEW IS NULL THEN
        RAISE EXCEPTION 'You can not delete from table';
    END IF;
    IF OLD.StudentId != NEW.StudentId THEN
        RAISE EXCEPTION 'You are trying to update StudentId %', OLD.StudentId;
    END IF;
    IF OLD.CourseId != NEW.CourseId THEN
        RAISE EXCEPTION 'You are trying to update CourseId %', OLD.CourseId;
    END IF;
    IF OLD.Mark < NEW.Mark THEN
        RETURN NEW;
    ELSE
        RETURN OLD;
    END IF;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER PreserveMarks
    BEFORE DELETE OR UPDATE ON Marks
    FOR EACH ROW EXECUTE PROCEDURE applyPreserveMarks();
