CREATE TABLE users
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

CREATE TABLE urls
(
  id    BIGINT AUTO_INCREMENT NOT NULL,
  name  CHARACTER VARYING NOT NULL UNIQUE,
  url   CHARACTER VARYING NOT NULL UNIQUE,
  PRIMARY KEY (id)
);

CREATE TABLE permissions
(
  id     BIGINT AUTO_INCREMENT NOT NULL,
  action CHARACTER VARYING NOT NULL,
  role   CHARACTER VARYING NOT NULL,
  PRIMARY KEY (id)
);

INSERT INTO users(surname, name, second_name, login, password, email, date_create, role, status)
VALUES('Админ', 'Администратор', 'Администратович', 'root', '$2a$10$LijUmixpYL0i9rRvwXrnX.heUijboQzE3PsoCrxuJANIDVX28FNjS',
       'root@email', CURRENT_TIMESTAMP, 'ROLE_ADMIN', 'ACTIVE')