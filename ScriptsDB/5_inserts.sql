-- Inserts para la tabla exp.country
INSERT INTO exp.exp_country (ctr_acronym, ctr_name, created_at,modified_at, is_delete)
VALUES
       ('EC', 'Ecuador', CURRENT_TIMESTAMP,null, false);

-- Inserts para la tabla exp.role
INSERT INTO exp.exp_role (rol_name, created_at,modified_at, is_delete)
VALUES
       ('ADMIN', CURRENT_TIMESTAMP,null, false),
       ('BASIC', CURRENT_TIMESTAMP, null,false),
       ('DEVELOPER', CURRENT_TIMESTAMP, null,false);

-- Common categories
INSERT INTO exp.exp_person_category (cat_name, cat_type,cat_icon, cat_editable, per_id, created_at,modified_at, is_delete)
VALUES
      ('Food', 'E',null, false, null, CURRENT_TIMESTAMP,null, false),
      ('Internet', 'E',null, false, null, CURRENT_TIMESTAMP,null, false);


