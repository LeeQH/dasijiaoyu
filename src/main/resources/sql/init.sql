-- root用户操作
	-- 创建数据库
	-- drop database dasiutil;
	CREATE DATABASE dasi_util;

	-- 创建用户
	-- drop user 'dasi'@'localhost'
	CREATE USER 'dasi'@'localhost' IDENTIFIED BY 'mykeyisthis'; 

	-- select USER FROM USER; 

	-- 给dasi用户授予mydasi数据库的所有权
	GRANT ALL PRIVILEGES ON dasiutil.* TO 'dasi'@'localhost' IDENTIFIED BY 'mykeyisthis';
	
-- drop table `STU_INFO`
-- drop table `TEACHER_INFO`
-- drop table `CLASS_INFO`
-- drop table `MONTH_SCORE_INFO`

	
-- dasi用户操作
-- 教师信息表
CREATE TABLE `dasi_util`.`TEACHER_INFO`(
 `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
 `login_name` VARCHAR(32) COMMENT '登录名',
 `login_pwd` VARCHAR(32) COMMENT '登录密码', 
 PRIMARY KEY (`id`)
);

-- 班级信息表
CREATE TABLE `dasi_util`.`CLASS_INFO`(
 `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
 `class_id` VARCHAR(32) COMMENT '班级id',
 `class_name` VARCHAR(32) COMMENT '班级名', 
 `teacher_id` INT(11) COMMENT '教师的id主键',
 PRIMARY KEY (`id`)
);

-- 学生信息表
-- 更新班级人数时，先查数据库，遍历stu_id与爬取的比对，爬取多的填入，stu_id多的删除,比对内部信息一样跳过
-- 每日自动更新最后上线日期，爬取日成绩，将有日成绩的修改上线日期
CREATE TABLE `dasi_util`.`STU_INFO`(
 `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
 `stu_id` VARCHAR(32) NOT NULL COMMENT '学生id',
 `stu_name` VARCHAR(32) COMMENT '学生姓名',
 `par_name` VARCHAR(32) COMMENT '家长姓名',
 `tel_num` VARCHAR(32) COMMENT '电话号码',
 `start_date` DATE COMMENT '开通日期',
 `end_date` DATE COMMENT '结束日期',  
 `last_date` DATE COMMENT '最后一次上线日期',
 `class_id` VARCHAR(32) COMMENT '班级id', 
 PRIMARY KEY (`id`)
);
            
-- 月排名每个同学的月度积分
-- 给学生信息添加触发器，新增与删除的时候同步更新月度积分学生信息（month，class_id，stu_id，stu_name）
-- 查询班级月度积分时，用class_id与month
-- 添加目标积分
-- java定时更新：获取id、stu_name与目标分数，爬取月排名进行比对，更改score
CREATE TABLE `dasi_util`.`MONTH_SCORE_INFO`(
 `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
 `month` CHAR(6) COMMENT '当前月份（201701）',
 `class_id` VARCHAR(32) COMMENT '班级id',
 `stu_id` VARCHAR(32) COMMENT '学生id',
 `stu_name` VARCHAR(32) COMMENT '学生姓名',
 `score` INT(7) COMMENT '当前月成绩',
 `goal_score` INT(7) COMMENT '目标分数',
 `finish_goal` TINYINT(1) DEFAULT 0 COMMENT '目标是否达成（0没有，1成功）',
 PRIMARY KEY (`id`)
);

-- 新增学生信息时同步新增month_score_info
DELIMITER $$ 
DROP TRIGGER IF EXISTS `dasi_util`.`insert_month_score`$$ 
CREATE TRIGGER `dasi_util`.`insert_month_score` 
AFTER INSERT ON `dasi_util`.`stu_info`
FOR EACH ROW 
BEGIN
   INSERT INTO `dasi_util`.`MONTH_SCORE_INFO` (`month`,`class_id`,`stu_id`,`stu_name`)  
   VALUES( DATE_FORMAT(NOW(),'%Y%m'),new.`class_id`,new.`stu_id`,new.`stu_name`);  
END $$ 
DELIMITER ; 

-- 删除学生信息时同步删除month_score_info
DELIMITER $$ 
DROP TRIGGER IF EXISTS `dasi_util`.`delete_month_score`$$ 
CREATE TRIGGER `dasi_util`.`delete_month_score` 
AFTER DELETE ON `dasi_util`.`stu_info`
FOR EACH ROW 
BEGIN
   DELETE FROM `dasi_util`.`MONTH_SCORE_INFO` WHERE `stu_id`=old.`stu_id`;
END $$ 
DELIMITER ; 

-- 修改分数时判断是否达成目标
DELIMITER $$ 
DROP TRIGGER IF EXISTS `dasi_util`.`finish_goal`$$ 
CREATE TRIGGER `dasi_util`.`finish_goal` 
BEFORE UPDATE ON `dasi_util`.`month_score_info`
FOR EACH ROW 
BEGIN
   -- 值不变的字段，new的值等于old的值，所以new的值不为空，即这个字段更新后肯定不为空
   IF new.`score` IS NOT NULL AND new.`goal_score` IS NOT NULL AND new.`score` >= new.`goal_score` THEN
      SET new.`finish_goal` = 1;
   END IF;
END $$ 
DELIMITER ; 

















-- drop table `dasi_util`.`student`
-- CREATE TABLE `dasi_util`.`student`(
--  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
--  `stu_id` VARCHAR(32) COMMENT '学生id',
--  `stu_name` VARCHAR(32) COMMENT '学生姓名',
--  `f1` VARCHAR(32) COMMENT '结果1',
--  `f2` VARCHAR(32) COMMENT '结果2',
--  PRIMARY KEY (`id`)
-- );
-- 
-- 
-- DELIMITER $$ 
-- DROP TRIGGER IF EXISTS `dasi_util`.`update_student`$$ 
-- CREATE TRIGGER `dasi_util`.`update_student` 
-- BEFORE UPDATE ON `dasi_util`.`student`
-- FOR EACH ROW 
-- BEGIN
--    SET new.`f1` = new.`stu_id`;
--    SET new.`f2` = old.`stu_id`;
-- END $$ 
-- DELIMITER ; 
-- 
-- insert `student`(f1,f2) values(0,0);
-- INSERT `student`(stu_id,f1,f2) VALUES(10,0,0);
-- SELECT * FROM student;
-- 
-- update `student` set `stu_name`='1' where id=4;
-- UPDATE `student` SET `stu_name`='1' WHERE id=5;
-- select * from student;