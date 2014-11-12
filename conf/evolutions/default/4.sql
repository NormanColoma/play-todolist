 # --- !Ups
CREATE TABLE category (
	id integer NOT NULL DEFAULT nextval('task_id_seq'),
    name varchar(50) NOT NULL, 
    PRIMARY KEY(id)
);

CREATE TABLE task_category{
	id_task integer, 
	id_category integer, 
	PRIMARY KEY(id_task, id_category)
}

ALTER TABLE category ADD t_user varchar(50);
ALTER TABLE category ADD CONSTRAINT fk_tuser_category FOREIGN KEY(t_user) REFERENCES task_user(name);

# --- !Downs
ALTER TABLE category DROP t_user;
DROP TABLE  IF EXISTS category;
