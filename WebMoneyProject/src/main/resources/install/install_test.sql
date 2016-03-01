
CREATE SEQUENCE web_money_test.SEQ_ID;
drop table web_money_test.wm_actives cascade;
drop table web_money_test.WM_ACTIVE_TYPES cascade;
drop table web_money_test.user_role cascade;
drop table web_money_test.

CREATE TABLE web_money_test.WM_ACTIVE_TYPES (
  ID INTEGER PRIMARY KEY DEFAULT NEXTVAL('web_money_test.SEQ_ID'),
  C_CODE VARCHAR(255) NOT NULL,
  C_NAME VARCHAR(255)
);
create table web_money_test.wm_actives(
  ID INTEGER PRIMARY KEY DEFAULT NEXTVAL('web_money_test.SEQ_ID'),
  C_CODE VARCHAR(255) NOT NULL,
  C_NAME VARCHAR(255),
  C_ACTIVE_TYPE INTEGER REFERENCES web_money_test.WM_ACTIVE_TYPES(ID) MATCH FULL 
);

CREATE TABLE web_money_test.user_role (
  authority varchar(255) NOT NULL,
  name varchar(255) NOT NULL,
  user_id INTEGER DEFAULT NULL
);
CREATE TABLE web_money_test.USERS (
  ID INTEGER PRIMARY KEY DEFAULT NEXTVAL('web_money_test.SEQ_ID'),
  LOGIN VARCHAR(255) NOT NULL,
  PASSWORD VARCHAR(255) NOT NULL,
  TOKEN VARCHAR(255),
  C_SURNAME VARCHAR(255),
  C_NAME VARCHAR(255),
  C_FATHER_NAME VARCHAR(255),
  C_EMAIL varchar(255),
  ACTIVE INTEGER NOT NULL DEFAULT 1,
  DATE_UPDATED timestamp NOT NULL,
  USER_UPDATED_ID INTEGER
);
INSERT INTO web_money_test.users (login, password, date_updated, user_updated_id) VALUES
('test','098f6bcd4621d373cade4e832627b4f6', NOW(), 1);
INSERT INTO web_money_test.user_role (authority, name, user_id) VALUES ('SUPER_ADMIN', 'Администратор', 1);