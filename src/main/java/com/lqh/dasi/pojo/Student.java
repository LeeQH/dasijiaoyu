package com.lqh.dasi.pojo;

import java.io.Serializable;

public class Student implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String studentName;
	private String parentName;
	private String phoneNum;
	private String startDate;
	private String endDate;
	private String RemainingDay;
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getParentName() {
		return parentName;
	}
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	public String getPhoneNum() {
		return phoneNum;
	}
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getRemainingDay() {
		return RemainingDay;
	}
	public void setRemainingDay(String remainingDay) {
		RemainingDay = remainingDay;
	}
	
	
}
