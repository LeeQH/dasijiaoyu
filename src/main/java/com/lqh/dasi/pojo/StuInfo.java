package com.lqh.dasi.pojo;

import java.util.Date;

public class StuInfo {
    private Integer id;

    private String stuId;

    private String stuName;

    private String parName;

    private String telNum;

    private Date startDate;

    private Date endDate;

    private Date lastDate;

    private String classId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStuId() {
        return stuId;
    }

    public void setStuId(String stuId) {
        this.stuId = stuId == null ? null : stuId.trim();
    }

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName == null ? null : stuName.trim();
    }

    public String getParName() {
        return parName;
    }

    public void setParName(String parName) {
        this.parName = parName == null ? null : parName.trim();
    }

    public String getTelNum() {
        return telNum;
    }

    public void setTelNum(String telNum) {
        this.telNum = telNum == null ? null : telNum.trim();
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getLastDate() {
        return lastDate;
    }

    public void setLastDate(Date lastDate) {
        this.lastDate = lastDate;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId == null ? null : classId.trim();
    }

	@Override
	public String toString() {
		return "StuInfo [id=" + id + ", stuId=" + stuId + ", stuName=" + stuName + ", parName=" + parName + ", telNum="
				+ telNum + ", startDate=" + startDate + ", endDate=" + endDate + ", lastDate=" + lastDate + ", classId="
				+ classId + "]";
	}
    
    
}