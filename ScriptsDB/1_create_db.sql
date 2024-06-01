/*Primero creamos una bd en el servidor y me conecto a la misma para
ejecutar los comandos a continuacion.*/
CREATE DATABASE xisdb;

-- Ademas debo quitar los permisos para que cualquiera pueda crear
-- sobre el esquema PUBLIC
REVOKE CREATE ON SCHEMA public FROM PUBLIC;

-- y tambien bloqueo que los roles o usuarios con permisos heredados de
--PUBLIC no puedan acceder a mi base de datos
REVOKE ALL ON DATABASE xisdb FROM PUBLIC;


--DBAs
CREATE USER tebo WITH ENCRYPTED PASSWORD 'tebodev';
CREATE USER freddy WITH ENCRYPTED PASSWORD 'freddydev';