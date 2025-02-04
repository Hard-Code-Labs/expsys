-- Permisos para usar secuencias
GRANT USAGE, SELECT ON SEQUENCE exp.exp_transaction_sq TO exp_readwrite;
GRANT USAGE, SELECT ON SEQUENCE exp.exp_person_category_sq TO exp_readwrite;
GRANT USAGE, SELECT ON SEQUENCE exp.exp_country_sq TO exp_readwrite;
GRANT USAGE, SELECT ON SEQUENCE exp.exp_role_person_sq TO exp_readwrite;
GRANT USAGE, SELECT ON SEQUENCE exp.exp_person_sq TO exp_readwrite;
GRANT USAGE, SELECT ON SEQUENCE exp.exp_role_sq TO exp_readwrite;
GRANT USAGE, SELECT ON SEQUENCE exp.exp_role_permission_sq TO exp_readwrite;
GRANT USAGE, SELECT ON SEQUENCE exp.exp_permission_sq TO exp_readwrite;