CREATE TRIGGER exp_person_audit_TG
    AFTER UPDATE ON exp.exp_person
    FOR EACH ROW
EXECUTE FUNCTION exp.exp_person_audit();