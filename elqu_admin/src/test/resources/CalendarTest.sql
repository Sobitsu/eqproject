truncate table workcalendar;

insert into workcalendar(caldate,isworkdate,pervworkdate) values 
('2015-01-01','0','2014-12-31'),
('2015-01-02','0',null),
('2015-01-03','0',null),
('2015-01-04','0',null),
('2015-01-05','0',null),
('2015-01-06','0',null),
('2015-01-07','0',null),
('2015-01-08','0',null),
('2015-01-09','0',null),
('2015-01-10','0',null),
('2015-01-11','0',null),
('2015-01-12','0',null),
('2015-01-13','0',null),
('2015-01-14','0',null),
('2015-01-15','1',null),
('2015-01-16','1',null),
('2015-01-17','0',null),
('2015-01-18','0',null),
('2015-01-19','1',null),
('2015-01-20','1',null),
('2015-01-21','1',null),
('2015-01-22','1',null),
('2015-01-23','1',null),
('2015-01-24','0',null),
('2015-01-25','0',null),
('2015-01-26','1',null),
('2015-01-27','1',null),
('2015-01-28','1',null),
('2015-01-29','1',null),
('2015-01-30','1',null),
('2015-01-31','1',null),
('2015-02-01','1',null),
('2015-02-02','1',null),
('2015-02-03','1',null),
('2015-02-04','1',null),
('2015-02-05','1',null),
('2015-02-06','1',null),
('2015-02-07','0',null),
('2015-02-08','0',null),
('2015-02-09','1',null),
('2015-02-10','1',null),
('2015-02-11','1',null),
('2015-02-12','1',null),
('2015-02-13','1',null),
('2015-02-14','1',null),
('2015-02-15','1',null),
('2015-02-16','1',null),
('2015-02-17','1',null),
('2015-02-18','1',null),
('2015-02-19','1',null),
('2015-02-20','1',null),
('2015-02-21','1',null),
('2015-03-02','1',null),
('2015-03-03','1',null),
('2015-03-04','1',null),
('2015-03-21','0',null),
('2015-03-22','0',null),
('2015-03-23','1',null),
('2015-03-24','1',null),
('2015-03-25','1',null),
('2015-03-26','1',null),
('2015-03-27','1',null),
('2015-03-28','0',null),
('2015-03-29','0',null),
('2015-03-30','1',null),
('2015-03-31','1',null),
('2015-04-01','1',null),
('2015-04-02','1',null),
('2015-04-03','1',null),
('2015-04-04','0',null),
('2015-04-05','0',null),
('2015-04-06','1',null),
('2015-04-07','1',null),
('2015-04-08','1',null),
('2015-04-09','1',null),
('2015-04-10','1',null),
('2015-04-11','0',null),
('2015-04-12','0',null),
('2015-04-13','1',null),
('2015-04-14','1',null),
('2015-04-15','1',null),
('2015-04-16','1',null),
('2015-04-17','1',null),
('2015-04-18','0',null),
('2015-04-19','0',null),
('2015-04-20','1',null),
('2015-04-21','1',null),
('2015-04-22','1',null),
('2015-04-23','1',null),
('2015-04-24','1',null),
('2015-04-25','1',null),
('2015-04-26','1',null)
;
update workcalendar set `mastfilecount` = 1, `visafilecount` = 2, `registeritemcount` = 3 where caldate = '2015-01-15';