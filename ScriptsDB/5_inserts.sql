-- Inserts para la tabla exp.country
INSERT INTO exp.exp_country (ctr_acronym, ctr_name, created_at, modified_at, is_delete)
VALUES
    ('EC', 'Ecuador', CURRENT_TIMESTAMP, null, false),
    ('US', 'United States', CURRENT_TIMESTAMP, null, false),
    ('CA', 'Canada', CURRENT_TIMESTAMP, null, false),
    ('MX', 'Mexico', CURRENT_TIMESTAMP, null, false),
    ('CO', 'Colombia', CURRENT_TIMESTAMP, null, false),
    ('AR', 'Argentina', CURRENT_TIMESTAMP, null, false),
    ('BR', 'Brazil', CURRENT_TIMESTAMP, null, false),
    ('PE', 'Peru', CURRENT_TIMESTAMP, null, false),
    ('CL', 'Chile', CURRENT_TIMESTAMP, null, false),
    ('VE', 'Venezuela', CURRENT_TIMESTAMP, null, false),
    ('UY', 'Uruguay', CURRENT_TIMESTAMP, null, false),
    ('PY', 'Paraguay', CURRENT_TIMESTAMP, null, false),
    ('BO', 'Bolivia', CURRENT_TIMESTAMP, null, false),
    ('GT', 'Guatemala', CURRENT_TIMESTAMP, null, false),
    ('HN', 'Honduras', CURRENT_TIMESTAMP, null, false),
    ('CR', 'Costa Rica', CURRENT_TIMESTAMP, null, false),
    ('SV', 'El Salvador', CURRENT_TIMESTAMP, null, false),
    ('NI', 'Nicaragua', CURRENT_TIMESTAMP, null, false),
    ('PA', 'Panama', CURRENT_TIMESTAMP, null, false),
    ('DO', 'Dominican Republic', CURRENT_TIMESTAMP, null, false),
    ('CU', 'Cuba', CURRENT_TIMESTAMP, null, false),
    ('CL', 'Chile', CURRENT_TIMESTAMP, null, false),
    ('BR', 'Brazil', CURRENT_TIMESTAMP, null, false),
    ('PE', 'Peru', CURRENT_TIMESTAMP, null, false),
    ('FR', 'France', CURRENT_TIMESTAMP, null, false),
    ('DE', 'Germany', CURRENT_TIMESTAMP, null, false),
    ('IT', 'Italy', CURRENT_TIMESTAMP, null, false),
    ('ES', 'Spain', CURRENT_TIMESTAMP, null, false),
    ('GB', 'United Kingdom', CURRENT_TIMESTAMP, null, false),
    ('PL', 'Poland', CURRENT_TIMESTAMP, null, false),
    ('NL', 'Netherlands', CURRENT_TIMESTAMP, null, false),
    ('BE', 'Belgium', CURRENT_TIMESTAMP, null, false),
    ('CH', 'Switzerland', CURRENT_TIMESTAMP, null, false),
    ('AT', 'Austria', CURRENT_TIMESTAMP, null, false),
    ('SE', 'Sweden', CURRENT_TIMESTAMP, null, false),
    ('NO', 'Norway', CURRENT_TIMESTAMP, null, false),
    ('FI', 'Finland', CURRENT_TIMESTAMP, null, false),
    ('DK', 'Denmark', CURRENT_TIMESTAMP, null, false),
    ('IE', 'Ireland', CURRENT_TIMESTAMP, null, false),
    ('PT', 'Portugal', CURRENT_TIMESTAMP, null, false),
    ('RU', 'Russia', CURRENT_TIMESTAMP, null, false),
    ('UA', 'Ukraine', CURRENT_TIMESTAMP, null, false),
    ('RO', 'Romania', CURRENT_TIMESTAMP, null, false),
    ('BG', 'Bulgaria', CURRENT_TIMESTAMP, null, false),
    ('HR', 'Croatia', CURRENT_TIMESTAMP, null, false),
    ('GR', 'Greece', CURRENT_TIMESTAMP, null, false),
    ('TR', 'Turkey', CURRENT_TIMESTAMP, null, false);
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


