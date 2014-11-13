 # --- !Ups
CREATE SEQUENCE category_id_seq;
CREATE TABLE category (
	id integer NOT NULL DEFAULT nextval('category_id_seq'),
    name varchar(50) NOT NULL, 
    PRIMARY KEY(id)
);

CREATE TABLE tcat(
	id_task integer NOT NULL, 
	id_category integer NOT NULL, 
	PRIMARY KEY(id_task,id_category)
);



ALTER TABLE category ADD c_user varchar(50);
ALTER TABLE category ADD CONSTRAINT fk_tuser_category FOREIGN KEY(c_user) REFERENCES task_user(name);
ALTER TABLE tcat ADD CONSTRAINT fk_task_category FOREIGN KEY(id_task) REFERENCES task(id);
ALTER TABLE tcat ADD CONSTRAINT fk_cat_category FOREIGN KEY(id_category) REFERENCES category(id);
ALTER TABLE tcat ADD tcat_user varchar(50);
ALTER TABLE tcat ADD CONSTRAINT fk_tcat_user FOREIGN KEY(tcat_user) REFERENCES task_user(name);


# --- !Downs
ALTER TABLE category DROP c_user; 
ALTER TABLE tcat DROP tcat_user;
DROP TABLE  IF EXISTS category;
DROP TABLE  IF EXISTS tcat;
