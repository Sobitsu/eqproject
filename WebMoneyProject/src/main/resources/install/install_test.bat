SET MYSQLROOT=postgres
SET PASSWD=Ny042012
psql  -hlocalhost -U%MYSQLROOT% -finstall_test.sql -a -oinstall_test.res -Linstall_test.log
PAUSE
