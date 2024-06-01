--Debo esta conectado a la base de datos creada para ejecutar lo siguiente:

-- 1) Crear el Esquema exp dentro de xisdb
CREATE SCHEMA exp;

-- 2) Crear Roles
CREATE ROLE exp_readonly;
CREATE ROLE exp_readwrite;

-- 3) Permiso al Rol para Conectarse a la Base de Datos xisdb
GRANT CONNECT ON DATABASE xisdb TO exp_readonly;
GRANT CONNECT ON DATABASE xisdb TO exp_readwrite;

-- 4) Permiso para Usar el Esquema exp
GRANT USAGE ON SCHEMA exp TO exp_readonly;
GRANT USAGE, CREATE ON SCHEMA exp TO exp_readwrite;

-- 5) Permisos de Operaciones a Todas las Tablas Existentes y Futuras del Esquema
GRANT SELECT ON ALL TABLES IN SCHEMA exp TO exp_readonly;
ALTER DEFAULT PRIVILEGES IN SCHEMA exp GRANT SELECT ON TABLES TO exp_readonly;

GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA exp TO exp_readwrite;
ALTER DEFAULT PRIVILEGES IN SCHEMA exp GRANT SELECT, INSERT, UPDATE, DELETE ON TABLES TO exp_readwrite;

-- 6) Finalmente creo los usuarios y les asigno un rol
CREATE USER exp WITH ENCRYPTED PASSWORD 'expdev';
GRANT exp_readwrite TO exp;

--Cambiar clave de un usuario
-- ALTER USER exp WITH PASSWORD 'niupass';

--quitar rol a usuario
-- REVOKE exp_readwrite FROM exp;

--borrar usuario
-- DROP USER exp;

