 # --- !Ups

ALTER TABLE task ADD t_date timestamp;

# --- !Downs
ALTER TABLE task DROP t_date;