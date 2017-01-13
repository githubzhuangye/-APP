package com.buu.bysj.domain;

import java.util.Date;

public class Chat {
	//主键id、学生sid、老师tid、内容content、发布者user（家长表示：student、老师表示：teacher）、时间systime
	private String id;
	private Student sid;
	private Teacher tid;
	private String content;
	private String user;
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
	public Teacher getTid() {
		return tid;
	}
	public void setTid(Teacher tid) {
		this.tid = tid;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public Date getSystime() {
		return systime;
	}
	public void setSystime(Date systime) {
		this.systime = systime;
	}
	
	

}
