package com.graduation.chenyou.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.buu.bysj.domain.JsonToBeanUtils;
import com.buu.bysj.domain.Student;
import com.graduation.chenyou.R;
import com.graduation.chenyou.adapter.StudentParentAdapter;
import com.graduation.chenyou.util.UserInfo;
import com.volley.VolleyInterface;
import com.volley.VolleyRequest;

/**
 * 新闻fragment
 * 
 * @author xxf
 *
 */

public class FragmentChat extends Fragment implements OnClickListener {
	// 定义控件
	private ListView lv_student_parent_list;
	
	protected Context mContext;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_chat, container, false);
		
		mContext = getActivity();
		
		// 获取控件
		getView2Init(v);
		//学生家长数据
		setStudentParentListData(mContext);
		
		return v;
	}

	/**
	 * 获得布局文件上的控件并初始化
	 */
	private void getView2Init(View view) {
		lv_student_parent_list = (ListView) view.findViewById(R.id.lv_student_parent_list);
	}

	// 点击事件
	@Override
	public void onClick(View v) {
		// 点击更多
		
	}
	
	// 填充数据
	private void setStudentParentListData(final Context context) {
		// 获取关注的用户
		String getStudentParentUrl = JsonToBeanUtils.url + "getTeacherStudent";
		Map<String, String> parameter = new HashMap<String, String>();
		parameter.put("tid", UserInfo.uid);
		// 网络请求
		VolleyRequest.RequestPost(getStudentParentUrl, "getTeacherStudent",
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
							final ArrayList<Student> studentParentList = (ArrayList<Student>) JsonToBeanUtils.JsonToStudentList(arr);

							// 设置数据设配器
							lv_student_parent_list.setAdapter(new StudentParentAdapter(
									context, studentParentList));
							// 设置关注的人列表item项点击事件
							lv_student_parent_list.setOnItemClickListener(new OnItemClickListener() {
										@Override
										public void onItemClick(AdapterView<?> arg0, View view,int position, long arg3) {
											// TODO Auto-generated method stub
											String studentId = studentParentList.get(position).getId();
											Intent intent = new Intent(context,ActivityChat.class);
											intent.putExtra("studentId", studentId);
											startActivity(intent);
										}
									});
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				});

	}

}
