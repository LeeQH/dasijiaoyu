package com.lqh.dasi.pojo;

public class StudyInfo {
	private String time; 
	private String course; 
	private String passNum; 
	private String score; 
	private String useTime;
	
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getCourse() {
		return course;
	}
	public void setCourse(String course) {
		this.course = course;
	}
	public String getPassNum() {
		return passNum;
	}
	public void setPassNum(String passNum) {
		this.passNum = passNum;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public String getUseTime() {
		return useTime;
	}
	public void setUseTime(String useTime) {
		this.useTime = useTime;
	}
	
	@Override
	public String toString() {
		return "StudyInfo [time=" + time + ", course=" + course + ", passNum=" + passNum + ", score=" + score
				+ ", useTime=" + useTime + "]";
	} 
	
	
}