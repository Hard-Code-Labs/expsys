-- drop table exp.exp_transaction cascade;
-- drop table exp.exp_person_category cascade;
-- drop table exp.exp_country cascade;
-- drop table exp.exp_role_person cascade;
-- drop table exp.exp_person cascade;
-- drop table exp.exp_role cascade;
--
-- drop sequence exp.exp_transaction_sq cascade;
-- drop sequence exp.exp_person_category_sq cascade;
-- drop sequence exp.exp_country_sq cascade;
-- drop sequence exp.exp_role_person_sq cascade;
-- drop sequence exp.exp_person_sq cascade;
-- drop sequence exp.exp_role_sq cascade;

--sequences
CREATE SEQUENCE exp.exp_transaction_sq START WITH 1 INCREMENT 1;
CREATE SEQUENCE exp.exp_person_category_sq START WITH 1 INCREMENT 1;
CREATE SEQUENCE exp.exp_country_sq START WITH 1 INCREMENT 1;
CREATE SEQUENCE exp.exp_role_person_sq START WITH 1 INCREMENT 1;
CREATE SEQUENCE exp.exp_person_sq START WITH 1 INCREMENT 1;
CREATE SEQUENCE exp.exp_role_sq START WITH 1 INCREMENT 1;
CREATE SEQUENCE exp.exp_permission_sq START WITH 1 INCREMENT 1;
CREATE SEQUENCE exp.exp_role_permission_sq START WITH 1 INCREMENT 1;

CREATE TABLE exp.exp_country (
 ctr_id INTEGER NOT NULL DEFAULT nextval('exp.exp_country_sq') PRIMARY KEY,
 ctr_acronym VARCHAR(2),
 ctr_name VARCHAR(200),
 ctr_icon VARCHAR(5),
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
    per_mail VARCHAR(100) NOT NULL UNIQUE,
    per_name VARCHAR(30) NOT NULL,
    per_lastname VARCHAR(30) NOT NULL,
    per_password VARCHAR(60) NOT NULL,
    per_avatar bytea,
    per_last_access TIMESTAMP,
    per_verification_code VARCHAR(3000),
    per_enabled BOOLEAN NOT NULL DEFAULT false,
    ctr_id INTEGER NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    modified_at TIMESTAMP,
    is_delete BOOLEAN NOT NULL DEFAULT false,
    FOREIGN KEY (ctr_id) REFERENCES exp.exp_country (ctr_id)
);

COMMENT ON COLUMN exp.exp_person.per_password IS 'Bcrypt support max 72 char';

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

CREATE TABLE exp.exp_permission(
    prm_id INTEGER NOT NULL DEFAULT nextval('exp.exp_permission_sq') PRIMARY KEY,
    prm_name VARCHAR NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    modified_at TIMESTAMP,
    is_delete BOOLEAN NOT NULL DEFAULT false
);

CREATE TABLE exp.exp_role_permission(
    rpe_id INTEGER NOT NULL DEFAULT nextval('exp.exp_role_permission_sq') PRIMARY KEY,
    prm_id INTEGER NOT NULL,
    rol_id INTEGER NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    modified_at TIMESTAMP,
    is_delete BOOLEAN NOT NULL DEFAULT false,
    FOREIGN KEY (prm_id) REFERENCES exp.exp_permission,
    FOREIGN KEY (rol_id) REFERENCES exp.exp_role
);

CREATE TABLE exp.exp_person_category (
  cat_id INTEGER NOT NULL DEFAULT nextval('exp.exp_person_category_sq') PRIMARY KEY,
  cat_name VARCHAR NOT NULL,
  cat_type VARCHAR(1) NOT NULL,
  cat_icon VARCHAR(5),
  per_id INTEGER NOT NULL,
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

--Para AUDITORIA
CREATE TABLE exp.exp_person_audit (
  audit_id SERIAL PRIMARY KEY,
  per_id INTEGER NOT NULL,
  old_email VARCHAR(100),
  new_email VARCHAR(100),
  changed_at TIMESTAMP DEFAULT NOW(),
  changed_by VARCHAR(50),
  old_enabled BOOLEAN,
  new_enabled BOOLEAN,
  old_country INTEGER,
  new_country INTEGER,
  password_changed BOOLEAN DEFAULT FALSE -- Solo indica si hubo cambio de contraseña
);


--Para FLYWAY
create table exp.flyway_schema_history
(
    installed_rank integer                 not null
        constraint flyway_schema_history_pk
            primary key,
    version        varchar(50),
    description    varchar(200)            not null,
    type           varchar(20)             not null,
    script         varchar(1000)           not null,
    checksum       integer,
    installed_by   varchar(100)            not null,
    installed_on   timestamp default now() not null,
    execution_time integer                 not null,
    success        boolean                 not null
);

alter table exp.flyway_schema_history
    owner to exp;

create index flyway_schema_history_s_idx
    on exp.flyway_schema_history (success);