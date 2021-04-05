drop database if exists school;
create database school;
use school;

CREATE TABLE school (   id INT(11) NOT NULL AUTO_INCREMENT,
                        name VARCHAR(50) NOT NULL,
                        year INT(11) NOT NULL,

                        PRIMARY KEY (id),
                        UNIQUE KEY name_year (name, year),
                        KEY name(name),
                        KEY year(year)
                        ) ENGINE=INNODB DEFAULT CHARSET=utf8;
                        
CREATE TABLE `group` (  id INT(11) NOT NULL AUTO_INCREMENT,
                        name VARCHAR(50) NOT NULL,
                        room VARCHAR(50) NOT NULL,
                        schoolId INT(11) NOT NULL,

                        PRIMARY KEY (id),
                        FOREIGN KEY (schoolId) REFERENCES school (id) ON DELETE CASCADE,
                        KEY name(name),
                        KEY room(room)
                        ) ENGINE=INNODB DEFAULT CHARSET=utf8;
                        
CREATE TABLE trainee (  id INT(11) NOT NULL AUTO_INCREMENT,
                        firstName VARCHAR(50) NOT NULL,
                        lastName VARCHAR(50) NOT NULL,
                        rating INT(11) default null,
                        groupId INT(11) default null,

                        PRIMARY KEY (id),
                        FOREIGN KEY (groupId) REFERENCES `group` (id) ON DELETE SET NULL,
                        KEY firstName(firstName),
                        KEY lastName(lastName),
                        KEY rating(rating)
                        ) ENGINE=INNODB DEFAULT CHARSET=utf8;

CREATE TABLE `subject`( id INT(11) NOT NULL AUTO_INCREMENT,
                        name VARCHAR(50) NOT NULL,

                        PRIMARY KEY (id),
                        
                        KEY name(name)
                        ) ENGINE=INNODB DEFAULT CHARSET=utf8;

create table subject_group( id INT(11) NOT NULL AUTO_INCREMENT,
                        groupId INT(11) NOT NULL,
                        subjectId INT(11) NOT NULL,

                        UNIQUE KEY subject_group (subjectId, groupId),
                        PRIMARY KEY (id),
                        FOREIGN KEY (groupId) REFERENCES `group` (id) ON DELETE CASCADE,
                        FOREIGN KEY (subjectId) REFERENCES subject (id) ON DELETE CASCADE
                        ) ENGINE=INNODB DEFAULT CHARSET=utf8;

CREATE TABLE cites (    id INT(11) NOT NULL AUTO_INCREMENT,
                        name VARCHAR(50) NOT NULL,

                        PRIMARY KEY (id),
                        UNIQUE KEY name (name)
                        ) ENGINE=INNODB DEFAULT CHARSET=utf8;
                        
INSERT INTO cites(`name`) VALUES ('London');
INSERT INTO cites(`name`) VALUES ('Paris');
INSERT INTO cites(`name`) VALUES ('Kiev');