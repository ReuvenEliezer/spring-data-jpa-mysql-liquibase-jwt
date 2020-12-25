--liquibase formatted sql
-- changeset reuven:2082019-2
ALTER
DATABASE netapp CHARACTER
SET = 'utf8' COLLATE = 'utf8_general_ci';

--changeset dudim:1511272183088-2
GRANT ALL ON `netapp`.* TO 'dbsync' IDENTIFIED BY 'dbsync';
FLUSH PRIVILEGES;

--changeset reuvene:09092019-01
CREATE TABLE `netapp`.`abstract_entity`
(
    `id`        INT(11)     NOT NULL AUTO_INCREMENT,
    PRIMARY KEY (`id`)
);

--changeset reuvene:09092019-1
CREATE TABLE `netapp`.`note`
(
    `id`        INT(11)     NOT NULL AUTO_INCREMENT,
    `Title`     VARCHAR(45) NULL,
    `Content`   VARCHAR(45) NULL,
    `CreatedAt` DATETIME    NOT NULL,
    `UpdatedAt` DATETIME    NOT NULL,
    PRIMARY KEY (`id`)
);
--     ENGINE = InnoDB
-- DEFAULT CHARACTER SET = utf8
--     COLLATE = utf8_bin;

--changeset reuvene:09092019-2
CREATE TABLE `netapp`.`author`
(
    `id`        INT(11)     NOT NULL AUTO_INCREMENT,
    `name`     VARCHAR(45)  NULL UNIQUE,
    PRIMARY KEY (`id`)
);

--changeset reuvene:09092019-3
CREATE TABLE `netapp`.`book`
(
    `id`        INT(11)     NOT NULL AUTO_INCREMENT,
    `name`     VARCHAR(45) NULL UNIQUE,
    `author`    INT(11)  NULL ,

    PRIMARY KEY (`id`)
);

--changeset reuvene:09092019-5
CREATE TABLE `employee` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(50) DEFAULT NULL,
  `last_name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
)

--changeset reuvene:09092019-6
CREATE TABLE `project` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
)

--changeset reuvene:09092019-7
CREATE TABLE `employee_project` (
  `employee_id` int(11) NOT NULL,
  `project_id` int(11) NOT NULL,
  PRIMARY KEY (`employee_id`,`project_id`),
  KEY `project_id` (`project_id`),
  CONSTRAINT `employee_project_ibfk_1`
   FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`),
  CONSTRAINT `employee_project_ibfk_2`
   FOREIGN KEY (`project_id`) REFERENCES `project` (`id`)
)
