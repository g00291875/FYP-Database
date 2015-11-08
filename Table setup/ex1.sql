create database if not exists demo;

use demo;

drop table if exists employees;

CREATE TABLE `employees` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `last_name` varchar(64) DEFAULT NULL,
  `first_name` varchar(64) DEFAULT NULL,
  `email` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=latin1;

INSERT INTO `employees` (`id`,`last_name`,`first_name`,`email`) VALUES (1,'Doe','John','john.doe@foo.com');
INSERT INTO `employees` (`id`,`last_name`,`first_name`,`email`) VALUES (2,'Public','Mary','mary.public@foo.com');
INSERT INTO `employees` (`id`,`last_name`,`first_name`,`email`) VALUES (3,'Queue','Susan','susan.queue@foo.com');