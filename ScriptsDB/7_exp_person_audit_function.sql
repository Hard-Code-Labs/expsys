CREATE OR REPLACE FUNCTION exp.exp_person_audit()
    RETURNS TRIGGER AS $$
BEGIN
    INSERT INTO exp.exp_person_audit (
        per_id,
        old_email, new_email,
        changed_at, changed_by,
        old_enabled, new_enabled,
        old_country, new_country,
        password_changed
    ) VALUES (
 OLD.per_id,
 CASE WHEN OLD.per_mail IS DISTINCT FROM NEW.per_mail THEN OLD.per_mail ELSE NULL END,
 CASE WHEN OLD.per_mail IS DISTINCT FROM NEW.per_mail THEN NEW.per_mail ELSE NULL END,
 NOW(),
 CURRENT_USER,
 CASE WHEN OLD.per_enabled IS DISTINCT FROM NEW.per_enabled THEN OLD.per_enabled ELSE NULL END,
 CASE WHEN OLD.per_enabled IS DISTINCT FROM NEW.per_enabled THEN NEW.per_enabled ELSE NULL END,
 CASE WHEN OLD.ctr_id IS DISTINCT FROM NEW.ctr_id THEN OLD.ctr_id ELSE NULL END,
 CASE WHEN OLD.ctr_id IS DISTINCT FROM NEW.ctr_id THEN NEW.ctr_id ELSE NULL END,
 CASE WHEN OLD.per_password IS DISTINCT FROM NEW.per_password THEN TRUE ELSE FALSE END
             );
    RETURN NEW;
END;

$$ LANGUAGE plpgsql;
