package com.lqh.dasi.commen;

/**
 * 地址常量
 * 
 * @author LiQuanhui
 * @date 2017年11月24日 下午5:22:40
 */
public final class URLConstant {

	/**
	 * 登陆的URL: http://ht.dasijiaoyu.com/dasi/userAction!userLogin.do
	 */
	public static final String LOGIN_URL = "http://ht.dasijiaoyu.com/dasi/userAction!userLogin.do";

	/**
	 * 查询老师下有多少班级的URL:
	 * http://ht.dasijiaoyu.com/dasi/allclAction!getAllclListByParam.do
	 */
	public static final String QUERY_CLASS_URL = "http://ht.dasijiaoyu.com/dasi/allclAction!getAllclListByParam.do";

	/**
	 * 根据classsid查询该班学生信息的URL:
	 * http://ht.dasijiaoyu.com/dasi/studentAction!getStudentListGroupByName.do?classid=
	 */
	public static final String QUERY_STUDENT_INFO_URL = "http://ht.dasijiaoyu.com/dasi/studentAction!getStudentListGroupByName.do?classid=";

	/**
	 * 根据classsid查询该班成绩排名的URL:
	 * http://ht.dasijiaoyu.com/dasi/learninginfoAction!toBgHeroesList.do?classid=
	 */
	public static final String QUERY_RANK_URL = "http://ht.dasijiaoyu.com/dasi/learninginfoAction!toBgHeroesList.do?classid=";

	/** class_path的路径 */
	public static final String CLASS_PATH = Thread.currentThread().getContextClassLoader().getResource("/").getPath();
}
