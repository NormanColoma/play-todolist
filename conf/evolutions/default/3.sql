 # --- !Ups

ALTER TABLE task ADD t_date date NULL;

# --- !Downs

DELETE FROM task_user;
ALTER TABLE task DROP t_date;