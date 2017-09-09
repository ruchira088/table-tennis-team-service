# TEAMS schema

# --- !Ups

CREATE TABLE teams (
  id VARCHAR(100) NOT NULL,
  name VARCHAR(100) NOT NULL,
  created_at TIMESTAMP NOT NULL,
  PRIMARY KEY (id, created_at)
);

CREATE TABLE users (
  id VARCHAR(100) NOT NULL,
  team_id VARCHAR(100) NOT NULL,
  username VARCHAR(100) NOT NULL,
  created_at TIMESTAMP NOT NULL,
  first_name VARCHAR(100),
  last_name VARCHAR(100),
  email VARCHAR(100) NOT NULL,
  PRIMARY KEY (id, created_at)
);

# --- !Downs

DROP TABLE TEAMS;