 # --- !Ups
CREATE TABLE category (
    name varchar(50) NOT NULL, 
    PRIMARY KEY(name)
);

ALTER TABLE t_user ADD n_category varchar(50);
ALTER TABLE t_user ADD CONSTRAINT fk_tuser_category FOREIGN KEY(n_category) REFERENCES category(name);

# --- !Downs
ALTER TABLE t_user DROP n_category;
DROP TABLE  IF EXISTS category;
