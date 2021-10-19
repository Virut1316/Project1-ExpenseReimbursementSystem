--Creation of database and dba user
CREATE USER dbaers WITH PASSWORD 'password';

CREATE DATABASE ers_project;

GRANT ALL PRIVILEGES ON DATABASE ers_project TO dbaers;