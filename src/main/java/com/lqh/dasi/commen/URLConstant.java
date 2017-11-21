package com.lqh.dasi.commen;

public final class URLConstant {
	
	/**登陆的URL*/
	public static final String LOGIN_URL="http://ht.dasijiaoyu.com/dasi/userAction!userLogin.do";
	
	/**查询老师下有多少班级的URL*/
	public static final String QUERY_CLASS_URL="http://ht.dasijiaoyu.com/dasi/allclAction!getAllclListByParam.do";
	
	/**根据classsid查询该班学生信息的URLl*/
	public static final String QUERY_STUDENT_INFO_URL="http://ht.dasijiaoyu.com/dasi/studentAction!getStudentListGroupByName.do?classid=";
	
	/**根据classsid查询该班成绩排名的URL*/
	public static final String QUERY_RANK_URL="http://ht.dasijiaoyu.com/dasi/learninginfoAction!toBgHeroesList.do?classid=";

	
}
