# Tasks schema
 
# --- !Ups

CREATE SEQUENCE task_id_seq;
CREATE TABLE task (
    id integer NOT NULL DEFAULT nextval('task_id_seq'),
    label varchar(255)
);

CREATE TABLE task_user (
    name varchar(50) NOT NULL, 
    PRIMARY KEY(name)
);

ALTER TABLE task ADD t_user varchar(50) DEFAULT 'anonimo';
ALTER TABLE task ADD CONSTRAINT fk_task_tuser FOREIGN KEY(t_user) REFERENCES task_user(name);
 
# --- !Downs
 
ALTER TABLE task DROP t_user;
DROP TABLE  IF EXISTS task;
DROP TABLE  IF EXISTS task_user;
DROP SEQUENCE task_id_seq;