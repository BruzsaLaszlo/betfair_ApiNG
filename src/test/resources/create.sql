-- aping_test
drop database if exists aping_test;
create schema aping_test;

drop user if exists 'aping_test'@'%';
CREATE USER 'aping_test'@'%' IDENTIFIED BY 'aping_test';
GRANT ALL PRIVILEGES ON aping_test.* TO 'aping_test'@'%';