package com.buu.bysj.domain;

import java.util.Date;

public class Student {
	//主键id、老师tid、姓名name、学号stunum、性别sex、作品数量worksnum、添加时间systime
	private String id;
	private Teacher tid;
	private String name;
	private String stunum;
	private String sex;
	private Integer worksnum;
	private Date systime;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Teacher getTid() {
		return tid;
	}
	public void setTid(Teacher tid) {
		this.tid = tid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStunum() {
		return stunum;
	}
	public void setStunum(String stunum) {
		this.stunum = stunum;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public Integer getWorksnum() {
		return worksnum;
	}
	public void setWorksnum(Integer worksnum) {
		this.worksnum = worksnum;
	}
	public Date getSystime() {
		return systime;
	}
	public void setSystime(Date systime) {
		this.systime = systime;
	}
	
	

}
