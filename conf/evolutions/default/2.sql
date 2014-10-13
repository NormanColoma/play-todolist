 # --- !Ups

CREATE TABLE task_user (
    name varchar(50) NOT NULL, 
    PRIMARY KEY(name)
);

ALTER TABLE task ADD t_user varchar(50) DEFAULT 'anonimo';
ALTER TABLE task ADD CONSTRAINT fk_task_tuser FOREIGN KEY(t_user) REFERENCES task_user(name);

INSERT INTO task_user (name) values('anonimo');
INSERT INTO task_user (name) values('domingogallardo');
INSERT INTO task_user (name) values('norman');
 
# --- !Downs

DELETE FROM task_user;
ALTER TABLE task DROP t_user;

