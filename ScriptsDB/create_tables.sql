CREATE TABLE exp.country (
 ctr_id SERIAL PRIMARY KEY,
 ctr_acronym VARCHAR(2),
 ctr_name VARCHAR(200),
 ctr_icon BYTEA,
 created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
 is_delete BOOLEAN NOT NULL DEFAULT false
);

CREATE TABLE exp.role (
  rol_id SERIAL PRIMARY KEY,
  rol_name VARCHAR(50) NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  is_delete BOOLEAN NOT NULL DEFAULT false
);

CREATE TABLE exp.person (
    per_id SERIAL PRIMARY KEY,
    per_uuid UUID NOT NULL,
    per_mail VARCHAR(100) NOT NULL,
    per_name VARCHAR(60) NOT NULL,
    per_lastname VARCHAR(60) NOT NULL,
    per_password VARCHAR NOT NULL,
    per_last_access TIMESTAMP,
    ctr_id INTEGER NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_delete BOOLEAN NOT NULL DEFAULT false,
    FOREIGN KEY (ctr_id) REFERENCES exp.country (ctr_id)
);

CREATE TABLE exp.role_person (
  rop_id SERIAL PRIMARY KEY,
  rol_id INTEGER NOT NULL,
  per_id INTEGER NOT NULL,
  rop_active BOOLEAN NOT NULL DEFAULT true,
  rop_start_date TIMESTAMP NOT NULL,
  rop_end_date TIMESTAMP,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  is_delete BOOLEAN NOT NULL DEFAULT false,
  FOREIGN KEY (rol_id) REFERENCES exp.role (rol_id),
  FOREIGN KEY (per_id) REFERENCES exp.person (per_id)
);

CREATE TABLE exp.person_category (
  cat_id SERIAL PRIMARY KEY,
  cat_name VARCHAR NOT NULL,
  cat_type VARCHAR(1) NOT NULL,
  cat_editable BOOLEAN NOT NULL DEFAULT true,
  per_id INTEGER,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  is_delete BOOLEAN NOT NULL DEFAULT false,
  FOREIGN KEY (per_id) REFERENCES exp.person (per_id)
);

CREATE TABLE exp.transaction (
  trn_id SERIAL PRIMARY KEY,
  trn_uuid UUID NOT NULL,
  trn_date TIMESTAMP,
  trn_month_reference INTEGER NOT NULL,
  trn_description VARCHAR NOT NULL,
  trn_amount NUMERIC(10,3) NOT NULL,
  cat_id INTEGER NOT NULL,
  per_id INTEGER NOT NULL,
  is_delete BOOLEAN NOT NULL DEFAULT false,
  FOREIGN KEY (cat_id) REFERENCES exp.person_category (cat_id),
  FOREIGN KEY (per_id) REFERENCES exp.person (per_id)
);

COMMENT ON COLUMN exp.country.ctr_acronym IS 'Based ISO-3166-1 ALPHA-2';

COMMENT ON COLUMN exp.transaction.trn_month_reference IS 'Month of reference';

COMMENT ON COLUMN exp.person_category.cat_type IS 'Type: I=Income E=Expense';
