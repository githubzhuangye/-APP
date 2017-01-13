package com.buu.bysj.domain;

import java.util.Date;

public class Works {
	//主键id、学生sid、名称name、照片picurl、时间systime
	private String id;
	private Student sid;
	private String name;
	private String picurl;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPicurl() {
		return picurl;
	}
	public void setPicurl(String picurl) {
		this.picurl = picurl;
	}
	public Date getSystime() {
		return systime;
	}
	public void setSystime(Date systime) {
		this.systime = systime;
	}
	

}
