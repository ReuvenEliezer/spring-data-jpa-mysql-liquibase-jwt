--liquibase formatted sql


--changeset reuvene:1511272083081-1 failOnError:CONTINUE
SET GLOBAL log_bin_trust_function_creators = 1;

--changeset reuvene:1511272183089-1
alter database netapp CHARACTER SET = 'utf8' COLLATE = 'utf8_general_ci';

--changeset reuvene:1511272183089-2
grant all on `netapp`.* TO 'dbsync' IDENTIFIED BY 'dbsync';
FLUSH PRIVILEGES;

--changeset reuvene:1511272183089-3
create TABLE  `netapp`.`abstract_entity`(`id`        BIGINT(20)     NOT NULL AUTO_INCREMENT,PRIMARY KEY (`id`));

--changeset reuvene:1511272183089-4
create TABLE  `netapp`.`note`( `id`        BIGINT(20)     NOT NULL AUTO_INCREMENT,    `Title`     VARCHAR(45) NULL,  `Content`   VARCHAR(45) NULL,`CreatedAt` DATETIME    NOT NULL,`UpdatedAt` DATETIME    NOT NULL,PRIMARY KEY (`id`));
--     ENGINE = InnoDB
-- DEFAULT CHARACTER SET = utf8
--     COLLATE = utf8_bin;

--changeset reuvene:1511272183089-5
create TABLE  `netapp`.`author`(`id`        BIGINT(20)     NOT NULL AUTO_INCREMENT,
    `name`     VARCHAR(45)  NULL UNIQUE,
    PRIMARY KEY (`id`)
);

--changeset reuvene:1511272183089-6
create TABLE  `netapp`.`book`
(
    `id`        BIGINT(20)     NOT NULL AUTO_INCREMENT,
    `name`     VARCHAR(45) NULL UNIQUE,
    `author`    BIGINT(20)  NULL ,

    PRIMARY KEY (`id`),
     KEY `id` (`id`),
  CONSTRAINT `book_ibfk_1`
      FOREIGN KEY (`author`) REFERENCES `author` (`id`) ON update NO ACTION ON delete NO ACTION
);

--changeset reuvene:1511272183089-7
create TABLE  `employee` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(50) DEFAULT NULL,
  `last_name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
)

--changeset reuvene:1511272183089-8
create TABLE  `project` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `title` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
)

--changeset reuvene:1511272183089-9
create TABLE  `employee_project` (
  `employee_id` BIGINT(20) NOT NULL,
  `project_id` BIGINT(20) NOT NULL,
  PRIMARY KEY (`employee_id`,`project_id`),
  KEY `project_id` (`project_id`),
  CONSTRAINT `employee_project_ibfk_1`
   FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`) ON update NO ACTION ON delete NO ACTION,
  CONSTRAINT `employee_project_ibfk_2`
   FOREIGN KEY (`project_id`) REFERENCES `project` (`id`) ON update NO ACTION ON delete NO ACTION
)

--changeset reuvene:1511272183089-10
create TABLE  `course` (
  `id` BIGINT(20)  NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL,
  PRIMARY KEY (`id`)
)

--changeset reuvene:1511272183089-11
create TABLE  `student` (
  `id` BIGINT(20)  NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL,
  PRIMARY KEY (`id`)
)

--changeset reuvene:1511272183089-12
create TABLE  `course_rating` (
  `course_id` BIGINT(20)  NOT NULL,
  `student_id` BIGINT(20)  NOT NULL,
  `rating` DOUBLE NULL,

  PRIMARY KEY (`course_id`, `student_id`),   KEY `student_id` (`student_id`),
      CONSTRAINT `course_rating_course_ibfk_1`
     FOREIGN KEY (`course_id`) REFERENCES `course` (`id`) ON update NO ACTION ON delete NO ACTION,
    CONSTRAINT `course_rating_course_ibfk_2`
     FOREIGN KEY (`student_id`) REFERENCES `student` (`id`) ON update NO ACTION ON delete NO ACTION
)

--changeset reuvene:1511272183089-13
create TABLE `netapp`.`case` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC)
)

--changeset reuvene:1511272183089-14
create TABLE `netapp`.`profile` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(45) NULL,
  `photo` VARCHAR(45) NULL,
  PRIMARY KEY (`id`)
)

--changeset reuvene:1511272183089-15
create TABLE `netapp`.`profile_case` (
  `profile_id` BIGINT(20) NOT NULL,
  `case_id` BIGINT(20) NOT NULL,
  PRIMARY KEY (`case_id`, `profile_id`),   KEY `profile_id` (`case_id`),
      CONSTRAINT `profile_case_ibfk_1`
          FOREIGN KEY (`profile_id`) REFERENCES `profile` (`id`) ON update NO ACTION ON delete NO ACTION,
       CONSTRAINT `profile_case_ibfk_2`
          FOREIGN KEY (`case_id`) REFERENCES `case` (`id`) ON update NO ACTION ON delete NO ACTION
)

