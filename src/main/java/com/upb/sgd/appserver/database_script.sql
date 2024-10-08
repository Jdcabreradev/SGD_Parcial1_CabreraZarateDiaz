CREATE DATABASE SGDUSERDB;
CREATE USER 'APPSERVERUSER'@'%' IDENTIFIED BY '123';
GRANT ALL PRIVILEGES ON SGDUSERDB.* TO 'APPSERVERUSER'@'%';
FLUSH PRIVILEGES;

USE SGDUSERDB;

CREATE TABLE `GROUP` (
    Id INT PRIMARY KEY AUTO_INCREMENT,
    `Name` VARCHAR(255) NOT NULL
);

CREATE TABLE USER (
    Id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(255) NOT NULL,
    isAdmin BOOLEAN DEFAULT FALSE,
    `password` VARCHAR(255) NOT NULL
);

CREATE TABLE USERGROUP(
    Id INT PRIMARY KEY AUTO_INCREMENT,
    UserId INT,
    GroupId INT,
    CONSTRAINT fk_ug_groupId FOREIGN KEY (GroupId) REFERENCES `GROUP`(Id),
    CONSTRAINT fk_ug_userId FOREIGN KEY (UserId) REFERENCES `USER`(Id)
);

/* This group NEEDS to exist, don't delete! */
INSERT INTO `GROUP` (`Name`) VALUES ('New Users');

INSERT INTO `GROUP` (`Name`) VALUES ('test');
INSERT INTO USER (username, `password`, isAdmin) 
VALUES ('utest', '123',true);

INSERT INTO USER (username, `password`, isAdmin) 
VALUES ('noadmin', '123',false);

INSERT INTO USER (username, `password`, isAdmin) 
VALUES ('noadmin2', '123',false);

INSERT INTO USER (username, `password`, isAdmin)
VALUES ('Poncho', '123', false);

INSERT INTO USERGROUP (UserId,GroupId) VALUES 
(4,1);

INSERT INTO USERGROUP (UserId,GroupId) VALUES 
(1,1);

INSERT INTO USERGROUP (UserId,GroupId) VALUES 
(2,1);
