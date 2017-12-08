package com.lqh.dasi.pojo;

public class ClassInfo {
    private Integer id;

    private String classId;

    private String className;

    private Integer teacherId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId == null ? null : classId.trim();
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className == null ? null : className.trim();
    }

    public Integer getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
    }

	@Override
	public String toString() {
		return "ClassInfo [id=" + id + ", classId=" + classId + ", className=" + className + ", teacherId=" + teacherId
				+ "]";
	}
    
}