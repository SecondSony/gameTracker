create table users
(
  id                BIGINT AUTO_INCREMENT NOT NULL,
  surname           CHARACTER VARYING NOT NULL,
  name              CHARACTER VARYING NOT NULL,
  second_name       CHARACTER VARYING NOT NULL,
  login             CHARACTER VARYING NOT NULL UNIQUE,
  password          CHARACTER VARYING NOT NULL,
  email             CHARACTER VARYING NOT NULL,
  date_create       TIMESTAMP NOT NULL,
  date_block        TIMESTAMP,
  date_last_online  TIMESTAMP,
  role              CHARACTER VARYING,
  status            CHARACTER VARYING NOT NULL,
  PRIMARY KEY (id)
);

create table urls
(
  id    BIGINT AUTO_INCREMENT NOT NULL,
  url   CHARACTER VARYING NOT NULL UNIQUE,
  user  BIGINT NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (user) REFERENCES users (id)
);

CREATE TABLE permissions
(
  id     BIGINT AUTO_INCREMENT NOT NULL,
  action CHARACTER VARYING NOT NULL,
  role   CHARACTER VARYING NOT NULL,
  PRIMARY KEY (id)
);