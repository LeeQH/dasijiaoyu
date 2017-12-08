package com.lqh.dasi.pojo;

public class MonthScoreInfo {
    private Integer id;

    private String month;

    private String classId;

    private String stuId;

    private String stuName;

    private Integer score;

    private Integer goalScore;

    private Boolean finishGoal;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month == null ? null : month.trim();
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId == null ? null : classId.trim();
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

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getGoalScore() {
        return goalScore;
    }

    public void setGoalScore(Integer goalScore) {
        this.goalScore = goalScore;
    }

    public Boolean getFinishGoal() {
        return finishGoal;
    }

    public void setFinishGoal(Boolean finishGoal) {
        this.finishGoal = finishGoal;
    }

	@Override
	public String toString() {
		return "MonthScoreInfo [id=" + id + ", month=" + month + ", classId=" + classId + ", stuId=" + stuId
				+ ", stuName=" + stuName + ", score=" + score + ", goalScore=" + goalScore + ", finishGoal="
				+ finishGoal + "]";
	}
    
}