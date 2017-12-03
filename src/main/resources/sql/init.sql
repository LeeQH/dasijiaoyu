-- root用户操作
	-- 创建数据库
	-- drop database mydasi;
	CREATE DATABASE mydasi;

	-- 创建用户
	-- drop user 'dasi'@'localhost'
	CREATE USER 'dasi'@'localhost' IDENTIFIED BY 'mykeyisthis'; 

	-- select USER FROM USER; 

	-- 给dasi用户授予mydasi数据库的所有权
	GRANT ALL PRIVILEGES ON mydasi.* TO 'dasi'@'localhost' IDENTIFIED BY 'mykeyisthis';
	
-- drop table `STU_INFO`
-- drop table `TEACHER_INFO`
-- drop table `CLASS_INFO`
-- drop table `MONTH_SCORE_INFO`

	
-- dasi用户操作
-- 教师信息表
CREATE TABLE `TEACHER_INFO`(
 `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
 `login_name` VARCHAR(32) COMMENT '登录名',
 `login_pwd` VARCHAR(32) COMMENT '登录密码', 
 PRIMARY KEY (`id`)
);

-- 班级信息表
CREATE TABLE `CLASS_INFO`(
 `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
 `class_id` VARCHAR(32) COMMENT '班级id',
 `class_name` VARCHAR(32) COMMENT '班级名', 
 `teacher_id` INT(11) COMMENT '教师的id主键',
 PRIMARY KEY (`id`)
);

-- 学生信息表
-- 新增，根据classid删除，在添加新的（防止班级转让给另一个班主任时插入异常，丢失最后上线日期）
-- 更新班级人数时，遍历stu_id与爬取的比对，爬取多的填入，stu_id多出的删除
-- 每日自动更新最后上线日期，爬取日成绩，将有日成绩的修改上线日期
CREATE TABLE `STU_INFO`(
 -- `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
 `stu_id` VARCHAR(32) NOT NULL COMMENT '学生id',
 `stu_name` VARCHAR(32) COMMENT '学生姓名',
 `par_name` VARCHAR(32) COMMENT '家长姓名',
 `tel_num` VARCHAR(32) COMMENT '电话号码',
 `start_date` DATE COMMENT '开通日期',
 `end_date` DATE COMMENT '结束日期',  
 `last_date` DATE COMMENT '最后一次上线日期',
 `class_id` VARCHAR(32) COMMENT '班级id', 
 PRIMARY KEY (`stu_id`)
);
            
-- 月排名每个同学的月度积分
-- 给学生信息添加触发器，新增与删除的时候同步更新月度积分学生信息（month，class_id，stu_id，stu_name）
-- 查询班级月度积分时，用class_id与month
-- 添加目标积分
-- java定时更新：获取id、stu_name与目标分数，爬取月排名进行比对，更改score
CREATE TABLE `MONTH_SCORE_INFO`(
 `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
 `month` CHAR(6) COMMENT '当前月份（201701）'，
 `class_id` VARCHAR(32) COMMENT '班级id',
 `stu_id` VARCHAR(32) COMMENT '学生id',
 `stu_name` VARCHAR(32) COMMENT '学生姓名',
 `score` INT(7) COMMENT '当前月成绩',
 `goal_score` INT(7) COMMENT '目标分数',
 `finish_goal` TINYINT(1) DEFAULT 0 COMMENT '目标是否达成（0没有，1成功）',
 PRIMARY KEY (`id`)
);










