package com.buu.bysj.domain;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.annotation.SuppressLint;

public class JsonToBeanUtils {
	
	//时间格式
	@SuppressLint("SimpleDateFormat")
	public static SimpleDateFormat formatter = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss");//2016-12-13 00:43:18
	@SuppressLint("SimpleDateFormat")
	public static SimpleDateFormat formatter1 = new SimpleDateFormat( "MM-dd HH:mm");
	//路径
	public static String url="http://112.74.179.104:8080/ChenYou/";
	
	//字符串替换
	public static String pathreplace(String path){
		return path.replace('\\', '/');
	}
	
	/**-------------Msg---------------*/
	//将obj转为Msg
	public static Msg JsonToMsg(JSONObject obj) throws Exception{
		Msg msg=new Msg();
		msg.setMsg(obj.getString("msg"));
		msg.setStatus(obj.getString("status"));
		return msg;
	}
	//将arr转为list<Msg>
	public static List<Msg> JsonToMsgList(JSONArray arr) throws Exception{
		List<Msg> list=new ArrayList<Msg>();
		 for (int i = 0; i < arr.length(); i++) {
	    	JSONObject jsonObject= arr.getJSONObject(i);
	    	Msg msg=JsonToBeanUtils.JsonToMsg(jsonObject);
	    	list.add(msg);
		}
		return list;
	}
	
	

/**-------------teacher---------------*/
	//将obj转为 teacher
	public static Teacher JsonToTeacher(JSONObject obj) throws Exception{
		Teacher teacher=new Teacher();
		teacher.setClassname(obj.getString("classname"));
		teacher.setId(obj.getString("id"));
		teacher.setPassword(obj.getString("password"));
		teacher.setPhone(obj.getString("phone"));
		teacher.setSystime(formatter.parse(obj.getString("systime")));
		teacher.setUsername(obj.getString("username"));
		teacher.setWorknum(obj.getString("worknum"));
		return teacher;
	}
	//将arr转为list<teacher>
	public static List<Teacher> JsonToTeacherList(JSONArray arr) throws Exception{
		List<Teacher> list=new ArrayList<Teacher>();
		for (int i = 0; i < arr.length(); i++) {
	    	JSONObject jsonObject= arr.getJSONObject(i);
	    	Teacher teacher=JsonToBeanUtils.JsonToTeacher(jsonObject);
	    	list.add(teacher);
		}
		return list;
	}
	
/**-------------Student---------------*/
	//将obj转为 Student
	public static Student JsonToStudent(JSONObject obj) throws Exception{
		Student student=new Student();
		student.setId(obj.getString("id"));
		student.setName(obj.getString("name"));
		student.setSex(obj.getString("sex"));
		student.setStunum(obj.getString("stunum"));
		student.setSystime(formatter.parse(obj.getString("systime")));
		student.setTid(JsonToTeacher(obj.getJSONObject("tid")));
		student.setWorksnum(obj.getInt("worksnum"));
		return student;
	}
	//将arr转为list<Student>
	public static List<Student> JsonToStudentList(JSONArray arr) throws Exception{
		List<Student> list=new ArrayList<Student>();
		for (int i = 0; i < arr.length(); i++) {
	    	JSONObject jsonObject= arr.getJSONObject(i);
	    	Student student=JsonToBeanUtils.JsonToStudent(jsonObject);
	    	list.add(student);
		}
		return list;
	}
	
/**-------------Works---------------*/
	//将obj转为 Works
	public static Works JsonToWorks(JSONObject obj) throws Exception{
		Works works=new Works();
		works.setId(obj.getString("id"));
		works.setName(obj.getString("name"));
		works.setPicurl(pathreplace(url+obj.getString("picurl")));
		works.setSid(JsonToStudent(obj.getJSONObject("sid")));
		works.setSystime(formatter.parse(obj.getString("systime")));
		return works;
	}
	//将arr转为list<Works>
	public static List<Works> JsonToWorksList(JSONArray arr) throws Exception{
		List<Works> list=new ArrayList<Works>();
		for (int i = 0; i < arr.length(); i++) {
	    	JSONObject jsonObject= arr.getJSONObject(i);
	    	Works works=JsonToBeanUtils.JsonToWorks(jsonObject);
	    	list.add(works);
		}
		return list;
	}
/**-------------Attendance---------------*/
	//将obj转为 Attendance
	public static Attendance JsonToAttendance(JSONObject obj) throws Exception{
		Attendance attendance=new Attendance();
		attendance.setId(obj.getString("id"));
		attendance.setMessage(obj.getString("message"));
		attendance.setSid(JsonToStudent(obj.getJSONObject("sid")));
		attendance.setSystime(formatter.parse(obj.getString("systime")));
		attendance.setType(obj.getString("type"));
		return attendance;
	}
	//将arr转为list<Attendance>
	public static List<Attendance> JsonToAttendanceList(JSONArray arr) throws Exception{
		List<Attendance> list=new ArrayList<Attendance>();
		for (int i = 0; i < arr.length(); i++) {
	    	JSONObject jsonObject= arr.getJSONObject(i);
	    	Attendance attendance=JsonToBeanUtils.JsonToAttendance(jsonObject);
	    	list.add(attendance);
		}
		return list;
	}
	
/**-------------Message---------------*/
	//将obj转为 Message
	public static Message JsonToMessage(JSONObject obj) throws Exception{
		Message message=new Message();
		message.setContent(obj.getString("content"));
		message.setId(obj.getString("id"));
		message.setSystime(formatter.parse(obj.getString("systime")));
		message.setTitle(obj.getString("title"));
		return message;
	}
	//将arr转为list<Message>
	public static List<Message> JsonToMessageList(JSONArray arr) throws Exception{
		List<Message> list=new ArrayList<Message>();
		for (int i = 0; i < arr.length(); i++) {
	    	JSONObject jsonObject= arr.getJSONObject(i);
	    	Message message=JsonToBeanUtils.JsonToMessage(jsonObject);
	    	list.add(message);
		}
		return list;
	}
	
	/**-------------Chat---------------*/
	//将obj转为 Chat
	public static Chat JsonToChat(JSONObject obj) throws Exception{
		Chat chat=new Chat();
		chat.setContent(obj.getString("content"));
		chat.setId(obj.getString("id"));
		chat.setSid(JsonToStudent(obj.getJSONObject("sid")));
		chat.setSystime(formatter.parse(obj.getString("systime")));
		chat.setTid(JsonToTeacher(obj.getJSONObject("tid")));
		chat.setUser(obj.getString("user"));
		return chat;
	}
	//将arr转为list<Chat>
	public static List<Chat> JsonToChatList(JSONArray arr) throws Exception{
		List<Chat> list=new ArrayList<Chat>();
		for (int i = 0; i < arr.length(); i++) {
	    	JSONObject jsonObject= arr.getJSONObject(i);
	    	Chat chat=JsonToBeanUtils.JsonToChat(jsonObject);
	    	list.add(chat);
		}
		return list;
	}
	
	

/**-------------T---------------*/
	//将obj转为 T
/*	public static T JsonToT(JSONObject obj) throws Exception{
		T t=new T();
		t.setAuthor(obj.getString("author"));
		return t;
	}
	//将arr转为list<T>
	public static List<T> JsonToTList(JSONArray arr) throws Exception{
		List<T> list=new ArrayList<T>();
		for (int i = 0; i < arr.length(); i++) {
	    	JSONObject jsonObject= arr.getJSONObject(i);
	    	T t=JsonToBeanUtils.JsonToT(jsonObject);
	    	list.add(t);
		}
		return list;
	}*/
}
