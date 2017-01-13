package com.buu.bysj.domain;

public class Msg {
	
	public static final String SUCCESS="success";
	public static final String ERROR="error";
	
	private String status;//设置登录状态
	private String msg;
	public Msg() {
		super();
	}
	public Msg(String status, String msg) {
		super();
		this.status = status;
		this.msg = msg;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
}
