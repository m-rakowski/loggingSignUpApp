create database logging_app_db;



-- Table: users

-- DROP TABLE users;

CREATE TABLE users
(
  id integer NOT NULL DEFAULT nextval('logging_app_user.user_user_id_seq'::regclass),
  name character varying(50) NOT NULL,
  passwordhash character varying(64) NOT NULL,
  salt character varying(64) NOT NULL,
  role character varying(20),
  email character varying(40),
  CONSTRAINT user_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE users
  OWNER TO postgres;




-- Table: logs

-- DROP TABLE logs;

CREATE TABLE logs
(
  id integer NOT NULL DEFAULT nextval('logging_app_user.log_log_id_seq'::regclass),
  "timestamp" timestamp without time zone NOT NULL,
  user_id integer NOT NULL,
  CONSTRAINT log_pkey PRIMARY KEY (id),
  CONSTRAINT log_user_id_fkey FOREIGN KEY (user_id)
      REFERENCES users (id) MATCH FULL
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE logs
  OWNER TO postgres;
