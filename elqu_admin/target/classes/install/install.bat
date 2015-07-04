SET MYSQLROOT=root
SET PASSWD=krutoy
mysql -h127.0.0.1 -u%MYSQLROOT% -p%PASSWD% < install.sql
PAUSE