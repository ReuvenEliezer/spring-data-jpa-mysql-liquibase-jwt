CREATE DEFINER=`root`@`localhost` PROCEDURE TruncateTables()
BEGIN
DECLARE done BOOL DEFAULT FALSE;
DECLARE truncate_command VARCHAR(512);
DECLARE truncate_cur
 CURSOR FOR /*This is the query which selects the tables we want to truncate*/
  SELECT CONCAT('TRUNCATE TABLE ','`',TABLE_SCHEMA,'`.`', TABLE_NAME,'`')
  FROM INFORMATION_SCHEMA.TABLES
  WHERE TABLE_SCHEMA in ('netapp')
    AND TABLE_TYPE = 'BASE TABLE';
DECLARE
  CONTINUE HANDLER FOR
  SQLSTATE '02000'
   SET done = TRUE;

SET FOREIGN_KEY_CHECKS=0;
OPEN truncate_cur;

truncate_loop: LOOP
 FETCH truncate_cur INTO truncate_command;
 SET @truncate_command = truncate_command;

 IF done THEN
  CLOSE truncate_cur;
  LEAVE truncate_loop;
 END IF;

 PREPARE truncate_command_stmt FROM @truncate_command;
 EXECUTE truncate_command_stmt;

END LOOP;

SET FOREIGN_KEY_CHECKS=1;
END
