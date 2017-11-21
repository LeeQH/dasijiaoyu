package com.lqh.dasi.pojo;

import java.io.Serializable;
import java.util.Map;

public class Teacher implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String loginId;
	private String password;
	
	private String classid;
 	
//	private Map<String,String> map;
//	
//	private String cookieName;
//	private String cookieValue;
	
	public String getLoginId() {
		return loginId;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public String getClassid() {
		return classid;
	}
	public void setClassid(String classid) {
		this.classid = classid;
	}
	@Override
	public String toString() {
		return "Teacher [loginId=" + loginId + ", password=" + password + ", classid=" + classid + "]";
	}

	
//	public Map<String, String> getMap() {
//		return map;
//	}
//	public void setMap(Map<String, String> map) {
//		this.map = map;
//	}
	
//	public String getCookieName() {
//		return cookieName;
//	}
//	public void setCookieName(String cookieName) {
//		this.cookieName = cookieName;
//	}
//	public String getCookieValue() {
//		return cookieValue;
//	}
//	public void setCookieValue(String cookieValue) {
//		this.cookieValue = cookieValue;
//	}

	
	
}
