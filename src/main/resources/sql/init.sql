-- root用户操作
	-- 创建数据库
	CREATE DATABASE mydasi;

	-- 创建用户
	CREATE USER 'dasi'@'localhost' IDENTIFIED BY 'mykeyisthis'; 

	-- select USER FROM USER; 

	-- 给dasi用户授予mydasi数据库的所有权
	GRANT ALL PRIVILEGES ON mydasi.* TO 'dasi'@'localhost' IDENTIFIED BY 'mykeyisthis';
	
	
-- dasi用户操作
-- drop table `STU_INFO`
-- 学生信息表
CREATE TABLE `STU_INFO`(
 `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
 `class_id` VARCHAR(32) COMMENT '班级id',
 `class_name` VARCHAR(32) COMMENT '班级名称', 
 `stu_name` VARCHAR(60) COMMENT '学生姓名',
 `par_name` VARCHAR(60) COMMENT '家长姓名',
 `tel_num` VARCHAR(20) COMMENT '电话号码',
 `start_date` TIMESTAMP COMMENT '开通时间',
 `end_date` TIMESTAMP COMMENT '结束时间', 
 `last_date` TIMESTAMP COMMENT '最后一次上线时间',
 PRIMARY KEY (`id`)
)