 # --- !Ups

ALTER TABLE task ADD t_date varchar(10);

# --- !Downs

DELETE FROM task_user;
ALTER TABLE task DROP t_date;