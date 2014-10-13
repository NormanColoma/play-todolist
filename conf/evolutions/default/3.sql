 # --- !Ups

ALTER TABLE task ADD t_date varchar(10);

# --- !Downs
ALTER TABLE task DROP t_date;