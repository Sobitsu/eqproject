#!/bin/bash
export MYSQLROOT=root
export PASSWD=krutoy
mysql -h127.0.0.1 -u$MYSQLROOT -p$PASSWD < ./install.sql