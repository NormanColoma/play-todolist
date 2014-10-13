 # --- !Ups

ALTER TABLE task ADD t_date timestamp;

# --- !Downs
DELETE FROM task;
ALTER TABLE task DROP t_date;
