 # --- !Ups

INSERT INTO task_user (name) values('anonimo');
INSERT INTO task_user (name) values('domingogallardo');
INSERT INTO task_user (name) values('norman');
 
# --- !Downs

DELETE FROM task;
DELETE FROM task_user;

