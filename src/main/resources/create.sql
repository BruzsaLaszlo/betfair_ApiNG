-- aping
drop database if exists aping;
create schema aping;

drop user if exists 'aping'@'%';
CREATE USER 'aping'@'%' IDENTIFIED BY 'aping';
GRANT ALL PRIVILEGES ON aping.* TO 'aping'@'%';