CREATE DATABASE IF NOT EXISTS elqu_base_test CHARACTER SET=utf8;

DROP USER 'elqu_test';    #for re-install;

CREATE USER 'elqu_test' IDENTIFIED BY '123456';
GRANT all privileges on elqu_base_test.* to elqu_test;

USE elqu_base_test;

DROP TABLE IF EXISTS `users`;
CREATE TABLE  `users` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `login` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `token` varchar(255),
  `surname` varchar(255),
  `name` varchar(255),
  `father_name` varchar(255),
  `user_role` bigint(20) DEFAULT NULL,
  `ufilial` bigint(20) DEFAULT NULL,
  `upodr` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `user_role`;
CREATE TABLE  `user_role` (
`id` bigint(20) NOT NULL AUTO_INCREMENT,  
`authority` varchar(255) NOT NULL,
`name` varchar(255) NOT NULL, 
PRIMARY KEY (`id`),
UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO users (id, login, password, user_role,ufilial,upodr) VALUES
(1, 'test','098f6bcd4621d373cade4e832627b4f6', 1, 1, 1);
INSERT INTO user_role (authority, name, id) VALUES ('SUPER_ADMIN', 'Администратор',1);

DROP TABLE IF EXISTS `filial`;
CREATE TABLE  `filial` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `kod` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `address` varchar(255),
  `coment` varchar(255),
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `podrazdel`;
CREATE TABLE  `podrazdel` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `kod` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `ifilial` bigint(20) DEFAULT NULL,
  `address` varchar(255),
  `coment` varchar(255),
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#Справочник услуг (ИД, код услуги, 
#название, филиал, подразделение, 
#вышестоящая услуга, статус услуги, комментарий)

DROP TABLE IF EXISTS `uslugi`;
CREATE TABLE  `uslugi` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `kod` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `filial_ref` bigint(20) DEFAULT NULL,
  `podrazdel_ref` bigint(20) DEFAULT NULL,
  `high_usl_ref` bigint(20) DEFAULT NULL,
  `status_usl_ref` bigint(20) DEFAULT NULL,
  `comment` varchar(255),
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#Справочник статусов (ИД, код, название)
DROP TABLE IF EXISTS `statusi`;
CREATE TABLE  `statusi` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `kod` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#Справочник окон (ИД, код, название)
DROP TABLE IF EXISTS `windows`;
CREATE TABLE  `windows` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `kod` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;