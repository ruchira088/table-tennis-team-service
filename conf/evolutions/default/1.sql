# TEAMS schema

# --- !Ups

CREATE TABLE TEAMS (
  ID VARCHAR(100) NOT NULL,
  NAME VARCHAR(100) NOT NULL UNIQUE,
  CREATED_AT TIMESTAMP NOT NULL,
  PRIMARY KEY (ID)
);

CREATE TABLE USERS (
  ID VARCHAR(100) NOT NULL,
  TEAM_ID VARCHAR(100) NOT NULL,
  USERNAME VARCHAR(100) NOT NULL UNIQUE,
  CREATED_AT TIMESTAMP NOT NULL,
  FIRST_NAME VARCHAR(100),
  LAST_NAME VARCHAR(100),
  EMAIL VARCHAR(100) NOT NULL,
  PRIMARY KEY (ID)
);

# --- !Downs

DROP TABLE TEAMS;