truncate table reestrhisted231;
truncate table reestrinfoed321;
truncate table inreestrinfo;
truncate table inreestrhist;
truncate table reestrinfo;
truncate table reestrhist;

insert into inreestrhist(
						Status,
						BeginProcessingDate,
						EndProcessingDate,
						ClearingSystemCode,
						ItemCount,
						SumDt,
						SumKt) 
				values
('CREATE','2015-01-14','2015-01-14','2','2','100','100');

insert into inreestrinfo (CollId,Status,BIC,Sum,DC,UniID,ClearingSystemCode,sessionID,sessionDate,filename) values 
('1','CREATE','044182727','100','1','123','2','1234567890','2015-01-14','x2x.xml'),
('1','CREATE','044182728','100','2','124','2','1234567890','2015-01-14','x2x.xml');