package com.buu.bysj.domain;

import java.util.Date;

public class Attendance {
	//主键id、学生sid、类型 type、信息message、时间systime
	private String id;
	private Student sid;
	private String type;
	private String message;
	private Date systime;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Student getSid() {
		return sid;
	}
	public void setSid(Student sid) {
		this.sid = sid;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Date getSystime() {
		return systime;
	}
	public void setSystime(Date systime) {
		this.systime = systime;
	}
	
	
}
