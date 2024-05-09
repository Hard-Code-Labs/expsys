-- Inserts para la tabla exp.country
INSERT INTO exp.country (ctr_acronym, ctr_name, created_at, is_delete)
VALUES
       ('EC', 'Ecuador', CURRENT_TIMESTAMP, false);

-- Inserts para la tabla exp.role
INSERT INTO exp.role (rol_name, created_at, is_delete)
VALUES
       ('ADMIN', CURRENT_TIMESTAMP, false),
       ('USER', CURRENT_TIMESTAMP, false);

-- Inserts para la tabla exp.person
INSERT INTO exp.person (per_uuid, per_mail, per_name, per_lastname, per_password, per_last_access, ctr_id, created_at, is_delete)
VALUES ('e8f1d4de-1919-4fc5-8c0a-bff4ad19e101',
        'teban.es@outlook.com', 'Tebo', 'stancamino', 'password123', null, 1, CURRENT_TIMESTAMP, false);


-- Inserts para la tabla exp.role_person
INSERT INTO exp.role_person (rol_id, per_id, rop_active, rop_start_date, created_at, is_delete)
VALUES (1, 1, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false);

-- Common categories
INSERT INTO exp.person_category (cat_name, cat_type, cat_editable, per_id, created_at, is_delete)
VALUES
      ('Education', 'E', true, null, CURRENT_TIMESTAMP, false),
      ('Food', 'E', true, null, CURRENT_TIMESTAMP, false);

-- Categories for me
INSERT INTO exp.person_category (cat_name, cat_type, cat_editable, per_id, created_at, is_delete)
VALUES
    ('Salary', 'I', true, 1, CURRENT_TIMESTAMP, false),
    ('Concerts', 'E', true, 1, CURRENT_TIMESTAMP, false);

-- Inserts para la tabla exp.transaction
INSERT INTO exp.transaction (trn_uuid, trn_date, trn_month_reference, trn_description, trn_amount, cat_id, per_id, is_delete)
VALUES
        ('c72765e9-3491-4a13-9fd9-2a46590f2c27', CURRENT_TIMESTAMP, 3, 'PedidosYa', 20.12, 2, 1, false),
        ('34912d03-8dd2-49f8-9e4c-84b345d20e34', CURRENT_TIMESTAMP, 3, 'Estereo Picnic', 1000.00, 4, 1, false);
