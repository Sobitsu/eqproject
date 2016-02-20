CREATE DATABASE IF NOT EXISTS nps_cb_integration CHARACTER SET=utf8;

DROP USER 'nps';	#for re-install;

CREATE USER 'nps' IDENTIFIED BY '123456';
GRANT all privileges on nps_cb_integration.* to nps;

USE nps_cb_integration;

DROP TABLE IF EXISTS `biks`;
CREATE TABLE `biks` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `bik` VARCHAR(255) NOT NULL,
  `corr_acc` VARCHAR(255),
  `full_name` VARCHAR(255),
  `name` VARCHAR(255),
  `reg_num` VARCHAR(255),
  `okpo` VARCHAR(255),
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `inreestrinfo`;
DROP TABLE IF EXISTS `inreestrhist`;
DROP TABLE IF EXISTS `statuses`;
DROP TABLE IF EXISTS `ed201hist`;

CREATE TABLE `statuses` (
  `code` VARCHAR(20) UNIQUE,
  `name` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`code`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

CREATE TABLE `inreestrhist` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `Status` VARCHAR(20) NOT NULL,
  `BeginProcessingDate` date NOT NULL,
  `EndProcessingDate` date NOT NULL,
  `ClearingSystemCode` VARCHAR(2)NOT NULL,
  `ItemCount` INTEGER NOT NULL,
  `SumDt` bigint(20) NOT NULL,
  `SumKt` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
  #FOREIGN KEY (`Status`) REFERENCES statuses(`id`)
  ) ENGINE=innodb DEFAULT CHARSET=utf8;

CREATE TABLE `inreestrinfo` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `CollId` BIGINT(20),
  `Status` VARCHAR(20) NOT NULL,
  `Bic` VARCHAR(9) NOT NULL,
  `Sum` bigint(20) NOT NULL,
  `DC` VARCHAR(2),
  `UniID` varchar(20),
  `MSG_BLOCK` VARCHAR(2000),
  `sessionID` varchar(100),
  `ClearingSystemCode` VARCHAR(2),
  `sessionDate` date,
  `filename` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  FOREIGN KEY (`CollId`) REFERENCES inreestrhist(`id`)
) ENGINE=innodb DEFAULT CHARSET=utf8;

CREATE UNIQUE INDEX inreestrinfoUNO
        ON inreestrinfo (UniID);
CREATE INDEX inreestrinfoSession
		on inreestrinfo (sessionID,ClearingSystemCode);
        
insert into statuses(code,name) values('CREATE','Создано');
insert into statuses(code,name) values('UPDATE','Обновлено');
insert into statuses(code,name) values('DONE','Обработанно');
insert into statuses(code,name) values('ACCEPT','Пройден первичный контроль');
insert into statuses(code,name) values('EXPORTED','Выгружен в ЦБ');
insert into statuses(code,name) values('GROUPED','Объеденены');
insert into statuses(code,name) values('CHECKERROR','Ошибка предпроверки валидности реестра');
insert into statuses(code,name) values('ERRORREESTR','Ошибка реестра');
insert into statuses(code,name) values('DONECONTROLCB','Проконтролирован в ЦБ');
insert into statuses(code,name) values('WAITMONEYCB','Отложенно в ЦБ');
insert into statuses(code,name) values('REFUSECB','Исключено в ЦБ');
insert into statuses(code,name) values('PAYDCB','Оплачено в ЦБ');
insert into statuses(code,name) values('CANTPAYCB','Невозможно исполнить через ПС БР');

CREATE TABLE `ed201hist` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `EDNo` INTEGER NOT NULL,
  `EDDate` date NOT NULL,
  `EDAuthor` VARCHAR(10),
  `EDReceiver` VARCHAR(10),
  `CtrlTime` time NOT NULL,
  `CtrlCode` VARCHAR(4),
  `Annotation` VARCHAR(150),
  `ErrorDiagnostic` TEXT,
  `MsgID` VARCHAR(150),
  `EDRefID_EDNo` BIGINT(20),
  `EDRefID_EDDate` date,
  `EDRefID_EDAuthor` VARCHAR(10), 
  `filename` VARCHAR(100), 
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
  ) ENGINE=innodb DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `users`;
CREATE TABLE  `users` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `login` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `token` varchar(255),
  `surname` varchar(255),
  `name` varchar(255),
  `father_name` varchar(255),
  `email` varchar(255),
  `active` tinyint NOT NULL DEFAULT 1,
  `date_updated` DATETIME NOT NULL,
  `user_updated_id` bigint(20),
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `user_role`;
CREATE TABLE  `user_role` (
  `authority` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `user_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO users (id, login, password, date_updated, user_updated_id) VALUES
(1, 'test','098f6bcd4621d373cade4e832627b4f6', NOW(), 1);
INSERT INTO user_role (authority, name, user_id) VALUES ('SUPER_ADMIN', 'Администратор', 1);

DROP TABLE IF EXISTS `reestrhist`;
CREATE TABLE  `reestrhist` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `Status` VARCHAR(20) NOT NULL,
  `BeginProcessingDate` date NOT NULL,
  `EndProcessingDate` date NOT NULL,
  `ClearingSystemCode` VARCHAR(2)NOT NULL,
  `RegisterItemsQuantity` INTEGER NOT NULL,
  `RegisterDebetSum` bigint(20) NOT NULL,
  `RegisterCreditSum` bigint(20) NOT NULL,
  `falename` VARCHAR(100),
  `inreeID` bigint(20),
  `EDNo` VARCHAR(9) NOT NULL,
  `EDDate` date NOT NULL,
  `EDAuthor` VARCHAR(10) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `reestrinfo`;
CREATE TABLE  `reestrinfo` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `CollId` bigint(20),
  `RegisterItemID` VARCHAR(9) NOT NULL,
  `BIC` VARCHAR(9) NOT NULL,
  `Sum` bigint(20) NOT NULL,
  `DC` VARCHAR(1)NOT NULL,
  `status` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

drop table if exists `workcalendar`;
create table `workcalendar` (
`id` bigint(20) not null auto_increment,
`caldate` date not null,
`mastfilecount` int,
`visafilecount` int,
`isworkdate` varchar(1),
`pervworkdate` date,
`registeritemcount` int,
`edncount` int,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE INDEX workcalendardate
		on workcalendar (caldate);
		
DROP TABLE IF EXISTS `reestrhisted231`;
CREATE TABLE  `reestrhisted231` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `BeginProcessingDate` DATE NOT NULL,
  `EndProcessingDate` DATE NOT NULL,
  `ClearingSystemCode` VARCHAR(2)NOT NULL,
  `RegisterItemsQuantity` INTEGER NOT NULL,
  `falename` VARCHAR(100) NOT NULL,
  `RegisterMode` INTEGER NOT NULL,
  `EDNo` VARCHAR(9) NOT NULL,
  `EDDate` DATE NOT NULL,
  `EDAuthor` VARCHAR(10) NOT NULL,
  `InitialEDEDNo` VARCHAR(9) NOT NULL,
  `InitialEDEDDate` DATE NOT NULL,
  `InitialEDEDAuthor` VARCHAR(10) NOT NULL,

  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `reestrinfoed321`;
CREATE TABLE  `reestrinfoed321` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `CollId` BIGINT(20),
  `RegisterItemID` VARCHAR(9) NOT NULL,
  `BIC` VARCHAR(9) NOT NULL,
  `Sum` bigint(20) NOT NULL,
  `DC` VARCHAR(1) NOT NULL,
  `StatusCode` VARCHAR(1) NOT NULL,
  `EDNo` VARCHAR(9) NOT NULL,
  `EDDate` DATE NOT NULL,
  `EDAuthor` VARCHAR(10) NOT NULL,
  `ree230detid` BIGINT(20),
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
