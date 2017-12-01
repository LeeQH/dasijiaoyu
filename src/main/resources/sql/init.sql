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
-- 学生信息表(更新时，根据classid删除记录，后重新添加)
CREATE TABLE `STU_INFO`(
 `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
 `stu_id` VARCHAR(32) DEFAULT NULL COMMENT '学生id',
 `stu_name` VARCHAR(32) DEFAULT NULL COMMENT '学生姓名',
 `par_name` VARCHAR(32) DEFAULT NULL COMMENT '家长姓名',
 `tel_num` VARCHAR(32) DEFAULT NULL COMMENT '电话号码',
 `start_date` DATE COMMENT '开通时间',
 `end_date` DATE COMMENT '结束时间', 
 `last_date` DATE COMMENT '最后一次上线时间',
 `class_id` VARCHAR(32) COMMENT '班级id', 
 `class_name` VARCHAR(32) COMMENT '班级名称', 
 PRIMARY KEY (`id`)
);
COMMIT;
-- drop table `TEACHER_INFO`
-- 教师信息表
CREATE TABLE `TEACHER_INFO`(
 `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
 `login_name` VARCHAR(32) COMMENT '登录名',
 `login_pwd` VARCHAR(32) COMMENT '登录密码', 
 PRIMARY KEY (`id`)
);

-- drop table `CLASS_INFO`
-- 班级信息表
CREATE TABLE `CLASS_INFO`(
 `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
 `class_id` VARCHAR(32) COMMENT '班级id',
 `class_name` VARCHAR(32) COMMENT '班级名', 
 `teacher_id` INT(11) COMMENT '教师的id主键',
 PRIMARY KEY (`id`)
);