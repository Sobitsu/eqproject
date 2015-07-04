truncate table reestrhisted231;
truncate table reestrinfoed321;
truncate table inreestrinfo;
truncate table inreestrhist;
truncate table reestrinfo;
truncate table reestrhist;
insert into reestrhist(
						Status,
						BeginProcessingDate,
						EndProcessingDate,
						ClearingSystemCode,
						RegisterItemsQuantity,
						RegisterDebetSum,
						RegisterCreditSum,
						EDNo,
						EDDate,
						EDAuthor) 
				values
('CREATE','2015-01-14','2015-01-14','2','2','100','100','1','2015-01-15','4333333333');
insert into reestrinfo (CollId,RegisterItemID,BIC,Sum,DC,status) values 
('1','1','044182727','100','1','CREATE'),
('1','2','044182727','100','2','CREATE');
