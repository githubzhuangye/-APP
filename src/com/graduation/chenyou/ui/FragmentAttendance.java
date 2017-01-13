package com.graduation.chenyou.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ListView;

import com.android.volley.VolleyError;
import com.buu.bysj.domain.JsonToBeanUtils;
import com.buu.bysj.domain.Student;
import com.graduation.chenyou.R;
import com.graduation.chenyou.adapter.StudentAttendanceAdapter;
import com.graduation.chenyou.util.UserInfo;
import com.volley.VolleyInterface;
import com.volley.VolleyRequest;

public class FragmentAttendance extends Fragment implements
		OnClickListener {
	// 搜索
	private EditText et_search_student;// 输入框
	private TextView tv_search_student_btn;
	//发送按钮
	private Button btn_send_student_attendance;
	//学生列表
	private ListView lv_student_attendance_list;

	protected Context mContext;


	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_attendance, container, false);
		
		// 获取上下文
		mContext = getActivity();
		// 初始化组件
		getView2Init(v);
		// 设置diarylit数据
		setStudentListData(mContext);

		return v;
	}

	/**
	 * 获得布局文件上的控件并初始化
	 */
	private void getView2Init(View view) {
		//搜索
		et_search_student = (EditText) view
				.findViewById(R.id.et_search_student);
		tv_search_student_btn = (TextView) view
				.findViewById(R.id.tv_search_student_btn);
		//发送按钮
		btn_send_student_attendance = (Button) view
				.findViewById(R.id.btn_send_student_attendance);
		// 列表
		lv_student_attendance_list = (ListView) view.findViewById(R.id.lv_student_attendance_list);
		// 设置点击事件
		tv_search_student_btn.setOnClickListener(this);
		btn_send_student_attendance.setOnClickListener(this);
	}

	// 填充学生数据
	private void setStudentListData(final Context context) {
		// 获取学生URL
		String getAllStudentUrl = JsonToBeanUtils.url + "getTeacherStudent";
		final Map<String, String> parameter = new HashMap<String, String>();
		parameter.put("tid", UserInfo.uid);
		// 网络请求
				VolleyRequest.RequestPost(getAllStudentUrl, "getTeacherStudent",
						parameter, new VolleyInterface() {
							// 请求失败
							@Override
							public void onfailure(VolleyError error) {
								// error.toString()
								Toast.makeText(context, "网络异常", Toast.LENGTH_LONG)
										.show();
							}
							// 请求成功
							@Override
							public void onSuccess(String result) {
								try {
									JSONArray arr = new JSONArray(result);
									final ArrayList<Student> studentList = (ArrayList<Student>) JsonToBeanUtils.JsonToStudentList(arr);
							// 设置数据
							lv_student_attendance_list.setAdapter(new StudentAttendanceAdapter(context, studentList));
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				});
	}

	// 点击事件
	@Override
	public void onClick(View v) {
		if (v == btn_send_student_attendance) {
			// 发送
			sendStudentAttendance();
		} else if (v == tv_search_student_btn) {
			// 按照关键字搜索
			searchStudent();
		}
		
	}
	//搜索学生
	private void searchStudent() {
		//搜索
		String keyword = et_search_student.getText().toString();
		if ("".equals(keyword)) {
			Toast.makeText(mContext, "搜索内容不能为空",
					Toast.LENGTH_LONG).show();
			return;
		}
		// 获取工具书URL
		String searchStudentUrl = JsonToBeanUtils.url + "searchStudent";
		Map<String, String> parameter = new HashMap<String, String>();
		parameter.put("name", keyword);
		parameter.put("tid", UserInfo.uid);
		// 网络请求获取工具书
		VolleyRequest.RequestPost(searchStudentUrl, "searchStudent",
				parameter, new VolleyInterface() {
					// 请求失败
					@Override
					public void onfailure(VolleyError error) {
						// TODO Auto-generated method stub
						Toast.makeText(mContext, error.toString(),
								Toast.LENGTH_LONG).show();
					}

					// 请求成功
					@Override
					public void onSuccess(String result) {
						try {
							JSONArray arr = new JSONArray(result);
							final ArrayList<Student> studentLists = (ArrayList<Student>) JsonToBeanUtils
									.JsonToStudentList(arr);
							//设置数据
							lv_student_attendance_list.setAdapter(new StudentAttendanceAdapter(mContext, studentLists));
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				});

	}

	//发送学生考勤
	protected void sendStudentAttendance() {
		//正在考虑是做成一个一个发送还是一次全部发送
//		searchType = ClassifyDiaryDialog.clickValue;
//		if(searchType != null){
//			searchDiary(searchType);
//		}
	}

}
