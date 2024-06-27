drop table exp.exp_transaction cascade;
drop table exp.exp_person_category cascade;
drop table exp.exp_country cascade;
drop table exp.exp_role_person cascade;
drop table exp.exp_person cascade;
drop table exp.exp_role cascade;

drop sequence exp.exp_transaction_sq cascade;
drop sequence exp.exp_person_category_sq cascade;
drop sequence exp.exp_country_sq cascade;
drop sequence exp.exp_role_person_sq cascade;
drop sequence exp.exp_person_sq cascade;
drop sequence exp.exp_role_sq cascade;

--sequences
CREATE SEQUENCE exp.exp_transaction_sq START WITH 1 INCREMENT 1;
CREATE SEQUENCE exp.exp_person_category_sq START WITH 1 INCREMENT 1;
CREATE SEQUENCE exp.exp_country_sq START WITH 1 INCREMENT 1;
CREATE SEQUENCE exp.exp_role_person_sq START WITH 1 INCREMENT 1;
CREATE SEQUENCE exp.exp_person_sq START WITH 1 INCREMENT 1;
CREATE SEQUENCE exp.exp_role_sq START WITH 1 INCREMENT 1;

CREATE TABLE exp.exp_country (
 ctr_id INTEGER NOT NULL DEFAULT nextval('exp.exp_country_sq') PRIMARY KEY,
 ctr_acronym VARCHAR(2),
 ctr_name VARCHAR(200),
 ctr_icon BYTEA,
 created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
 modified_at TIMESTAMP,
 is_delete BOOLEAN NOT NULL DEFAULT false
);

CREATE TABLE exp.exp_role (
  rol_id INTEGER NOT NULL DEFAULT nextval('exp.exp_role_sq') PRIMARY KEY,
  rol_name VARCHAR(50) NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  modified_at TIMESTAMP,
  is_delete BOOLEAN NOT NULL DEFAULT false
);

CREATE TABLE exp.exp_person (
    per_id INTEGER NOT NULL DEFAULT nextval('exp.exp_person_sq') PRIMARY KEY,
    per_uuid UUID NOT NULL,
    per_mail VARCHAR(100) NOT NULL,
    per_name VARCHAR(60) NOT NULL,
    per_lastname VARCHAR(60) NOT NULL,
    per_password VARCHAR NOT NULL,
    per_avatar bytea,
    per_last_access TIMESTAMP,
    ctr_id INTEGER NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    modified_at TIMESTAMP,
    is_delete BOOLEAN NOT NULL DEFAULT false,
    FOREIGN KEY (ctr_id) REFERENCES exp.exp_country (ctr_id)
);

CREATE TABLE exp.exp_role_person (
  rop_id INTEGER NOT NULL DEFAULT nextval('exp.exp_role_person_sq') PRIMARY KEY,
  rol_id INTEGER NOT NULL,
  per_id INTEGER NOT NULL,
  rop_active BOOLEAN NOT NULL DEFAULT true,
  rop_start_date TIMESTAMP NOT NULL,
  rop_end_date TIMESTAMP,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  modified_at TIMESTAMP,
  is_delete BOOLEAN NOT NULL DEFAULT false,
  FOREIGN KEY (rol_id) REFERENCES exp.exp_role (rol_id),
  FOREIGN KEY (per_id) REFERENCES exp.exp_person (per_id)
);

CREATE TABLE exp.exp_person_category (
  cat_id INTEGER NOT NULL DEFAULT nextval('exp.exp_person_category_sq') PRIMARY KEY,
  cat_name VARCHAR NOT NULL,
  cat_type VARCHAR(1) NOT NULL,
  cat_icon VARCHAR,
  cat_editable BOOLEAN NOT NULL DEFAULT true,
  per_id INTEGER,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  modified_at TIMESTAMP,
  is_delete BOOLEAN NOT NULL DEFAULT false,
  FOREIGN KEY (per_id) REFERENCES exp.exp_person (per_id)
);

CREATE TABLE exp.exp_transaction (
  trn_id INTEGER NOT NULL DEFAULT nextval('exp.exp_transaction_sq') PRIMARY KEY,
  trn_uuid UUID NOT NULL,
  trn_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  trn_month_reference INTEGER NOT NULL,
  trn_description VARCHAR NOT NULL,
  trn_amount NUMERIC(10,3) NOT NULL,
  cat_id INTEGER NOT NULL,
  per_id INTEGER NOT NULL,
  is_delete BOOLEAN NOT NULL DEFAULT false,
  FOREIGN KEY (cat_id) REFERENCES exp.exp_person_category (cat_id),
  FOREIGN KEY (per_id) REFERENCES exp.exp_person (per_id)
);

COMMENT ON COLUMN exp.exp_country.ctr_acronym IS 'Based ISO-3166-1 ALPHA-2';

COMMENT ON COLUMN exp.exp_transaction.trn_month_reference IS 'Month of reference';

COMMENT ON COLUMN exp.exp_person_category.cat_type IS 'Type: I=Income E=Expense';
