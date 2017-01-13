package com.graduation.chenyou.ui;

import com.graduation.chenyou.R;


import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends FragmentActivity implements OnClickListener {
	private LinearLayout ll_menu_message;
	private LinearLayout ll_menu_attendance;
	private LinearLayout ll_menu_chat;
	private LinearLayout ll_menu_student;
	private ImageView iv_menu_message_icon;
	private ImageView iv_menu_attendance_icon;
	private ImageView iv_menu_chat_icon;
	private ImageView iv_menu_student_icon;
	private TextView tv_menu_message_text;
	private TextView tv_menu_attendance_text;
	private TextView tv_menu_chat_text;
	private TextView tv_menu_student_text;

	private FragmentManager fragmentManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//初始化控件
		getView2Init();
		//设置默认fragment
		initFragment(new FragmentMessage());
	}


	/**
	 * 获得布局文件上的控件并初始化
	 */
	private void getView2Init() {
		
		ll_menu_message = (LinearLayout) findViewById(R.id.ll_menu_message);
		ll_menu_attendance = (LinearLayout) findViewById(R.id.ll_menu_attendance);
		ll_menu_chat = (LinearLayout) findViewById(R.id.ll_menu_chat);
		ll_menu_student = (LinearLayout) findViewById(R.id.ll_menu_student);
		iv_menu_message_icon = (ImageView) findViewById(R.id.iv_menu_message_icon);
		iv_menu_attendance_icon = (ImageView) findViewById(R.id.iv_menu_attendance_icon);
		iv_menu_chat_icon = (ImageView) findViewById(R.id.iv_menu_chat_icon);
		iv_menu_student_icon = (ImageView) findViewById(R.id.iv_menu_student_icon);
		tv_menu_message_text = (TextView) findViewById(R.id.tv_menu_message_text);
		tv_menu_attendance_text = (TextView) findViewById(R.id.tv_menu_attendance_text);
		tv_menu_chat_text = (TextView) findViewById(R.id.tv_menu_chat_text);
		tv_menu_student_text = (TextView) findViewById(R.id.tv_menu_student_text);

		iv_menu_message_icon.setImageDrawable(getResources().getDrawable(
				R.drawable.icon_menu_message_pressed));
		tv_menu_message_text.setTextColor(getResources().getColor(
				R.color.menu_click));
		// 设置点击事件
		ll_menu_message.setOnClickListener(this);
		ll_menu_attendance.setOnClickListener(this);
		ll_menu_chat.setOnClickListener(this);
		ll_menu_student.setOnClickListener(this);
	}

	// 设置样式
	private void setMenuStyle(View v) {

		// 公告
		if (v == ll_menu_message) {
			iv_menu_message_icon.setImageDrawable(getResources().getDrawable(
					R.drawable.icon_menu_message_pressed));
			tv_menu_message_text.setTextColor(getResources().getColor(
					R.color.menu_click));
		} else {
			iv_menu_message_icon.setImageDrawable(getResources().getDrawable(
					R.drawable.icon_menu_message_normal));
			tv_menu_message_text.setTextColor(getResources().getColor(
					R.color.menu_nomarl));
		}
		//考勤
		if (v == ll_menu_attendance) {
			iv_menu_attendance_icon.setImageDrawable(getResources().getDrawable(
					R.drawable.icon_menu_attendance_pressed));
			tv_menu_attendance_text.setTextColor(getResources().getColor(
					R.color.menu_click));
		} else {
			iv_menu_attendance_icon.setImageDrawable(getResources().getDrawable(
					R.drawable.icon_menu_attendance_normal));
			tv_menu_attendance_text.setTextColor(getResources().getColor(
					R.color.menu_nomarl));
		}
		// 沟通
		if (v == ll_menu_chat) {
			iv_menu_chat_icon.setImageDrawable(getResources().getDrawable(
					R.drawable.icon_menu_chat_pressed));
			tv_menu_chat_text.setTextColor(getResources().getColor(
					R.color.menu_click));
		} else {
			iv_menu_chat_icon.setImageDrawable(getResources().getDrawable(
					R.drawable.icon_menu_chat_normal));
			tv_menu_chat_text.setTextColor(getResources().getColor(
					R.color.menu_nomarl));
		}
		// 学生
		if (v == ll_menu_student) {
			iv_menu_student_icon.setImageDrawable(getResources().getDrawable(
					R.drawable.icon_menu_student_pressed));
			tv_menu_student_text.setTextColor(getResources().getColor(
					R.color.menu_click));
		} else {
			iv_menu_student_icon.setImageDrawable(getResources().getDrawable(
					R.drawable.icon_menu_student_normal));
			tv_menu_student_text.setTextColor(getResources().getColor(
					R.color.menu_nomarl));
		}
	}

	// 点击事件
	@Override
	public void onClick(View v) {
		if (v == ll_menu_message) {
			updateFragment(new FragmentMessage());
		} else if (v == ll_menu_attendance) {
			updateFragment(new FragmentAttendance());

		}  else if (v == ll_menu_chat) {
			updateFragment(new FragmentChat());

		} else {
			updateFragment(new FragmentStudent());
		}
		setMenuStyle(v);
	}

	/**
	 * 初始化Fragment
	 * 
	 * @param f
	 */
	private void initFragment(Fragment f) {
		fragmentManager = getSupportFragmentManager();
		updateFragment(f);
	}

	/**
	 * 更新Fragment
	 * 
	 * @param f
	 */
	private void updateFragment(Fragment f) {
		FragmentTransaction ft = fragmentManager.beginTransaction();
		ft.replace(R.id.rl_main_content, f);
		ft.commit();
	}

}
