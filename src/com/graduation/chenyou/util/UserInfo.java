package com.graduation.chenyou.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class UserInfo {
	public static String uid;
	// 保存登录信息
	public static void save(Context context, String username, String password,String loginUid) {

		// 获取SharedPreferences对象

		SharedPreferences sharedPre = context.getSharedPreferences("userInfo",
				context.MODE_PRIVATE);

		// 获取Editor对象

		Editor editor = sharedPre.edit();

		// 设置参数

		editor.putString("username", username);

		editor.putString("password", password);
		editor.putString("uid", loginUid);
		//设置静态
		uid = loginUid;
		// 提交
		editor.commit();
	}

	// 获取登录信息
	public static String[] get(Context context) {
		String[] userInfo=new String[3];
		SharedPreferences sharedPre = context.getSharedPreferences("userInfo",
				context.MODE_PRIVATE);

		String username = sharedPre.getString("username", "");

		String password = sharedPre.getString("password", "");
		String loginUid = sharedPre.getString("uid", "");
		userInfo[0] = username;
		userInfo[1] = password;
		userInfo[2] = loginUid;
		uid = loginUid;
//		UID = uid;
		return userInfo;
	}

}
