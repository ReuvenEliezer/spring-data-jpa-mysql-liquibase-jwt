--liquibase formatted sql
-- changeset reuven:2082019-2
ALTER
DATABASE netapp CHARACTER
SET = 'utf8' COLLATE = 'utf8_general_ci';

--changeset dudim:1511272183088-2
GRANT ALL ON `netapp`.* TO 'dbsync' IDENTIFIED BY 'dbsync';
FLUSH PRIVILEGES;

--changeset reuvene:09092019-01
CREATE TABLE IF NOT EXISTS `netapp`.`abstract_entity`
(
    `id`        BIGINT(20)     NOT NULL AUTO_INCREMENT,
    PRIMARY KEY (`id`)
);

--changeset reuvene:09092019-1
CREATE TABLE IF NOT EXISTS `netapp`.`note`
(
    `id`        BIGINT(20)     NOT NULL AUTO_INCREMENT,
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
CREATE TABLE IF NOT EXISTS `netapp`.`author`
(
    `id`        BIGINT(20)     NOT NULL AUTO_INCREMENT,
    `name`     VARCHAR(45)  NULL UNIQUE,
    PRIMARY KEY (`id`)
);

--changeset reuvene:09092019-3
CREATE TABLE IF NOT EXISTS `netapp`.`book`
(
    `id`        BIGINT(20)     NOT NULL AUTO_INCREMENT,
    `name`     VARCHAR(45) NULL UNIQUE,
    `author`    BIGINT(20)  NULL ,

    PRIMARY KEY (`id`),
     KEY `id` (`id`),
  CONSTRAINT `book_ibfk_1`
      FOREIGN KEY (`author`) REFERENCES `author` (`id`) ON UPDATE NO ACTION ON DELETE NO ACTION
);

--changeset reuvene:09092019-5
CREATE TABLE IF NOT EXISTS `employee` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(50) DEFAULT NULL,
  `last_name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
)

--changeset reuvene:09092019-6
CREATE TABLE IF NOT EXISTS `project` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `title` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
)

--changeset reuvene:09092019-7
CREATE TABLE IF NOT EXISTS `employee_project` (
  `employee_id` BIGINT(20) NOT NULL,
  `project_id` BIGINT(20) NOT NULL,
  PRIMARY KEY (`employee_id`,`project_id`),
  KEY `project_id` (`project_id`),
  CONSTRAINT `employee_project_ibfk_1`
   FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`) ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT `employee_project_ibfk_2`
   FOREIGN KEY (`project_id`) REFERENCES `project` (`id`) ON UPDATE NO ACTION ON DELETE NO ACTION
)

--changeset reuvene:09092019-8
CREATE TABLE IF NOT EXISTS `course` (
  `id` BIGINT(20)  NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL,
  PRIMARY KEY (`id`)
)

--changeset reuvene:09092019-9
CREATE TABLE IF NOT EXISTS `student` (
  `id` BIGINT(20)  NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL,
  PRIMARY KEY (`id`)
)

--changeset reuvene:09092020-1
CREATE TABLE IF NOT EXISTS `course_rating` (
  `course_id` BIGINT(20)  NOT NULL,
  `student_id` BIGINT(20)  NOT NULL,
  `rating` DOUBLE NULL,

  PRIMARY KEY (`course_id`, `student_id`),
      KEY `student_id` (`student_id`),
      CONSTRAINT `course_rating_course_ibfk_1`
     FOREIGN KEY (`course_id`) REFERENCES `course` (`id`) ON UPDATE NO ACTION ON DELETE NO ACTION,
    CONSTRAINT `course_rating_course_ibfk_2`
     FOREIGN KEY (`student_id`) REFERENCES `student` (`id`) ON UPDATE NO ACTION ON DELETE NO ACTION
)

