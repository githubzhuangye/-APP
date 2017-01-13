package com.graduation.chenyou.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.smartimage.SmartImageView;
import com.android.volley.VolleyError;
import com.buu.bysj.domain.JsonToBeanUtils;
import com.buu.bysj.domain.Message;
import com.buu.bysj.domain.Msg;
import com.buu.bysj.domain.Student;
import com.graduation.chenyou.R;
import com.graduation.chenyou.util.UserInfo;
import com.volley.VolleyInterface;
import com.volley.VolleyRequest;

public class StudentAttendanceAdapter extends BaseAdapter {
	private ArrayList<Student> lists;
	private LayoutInflater inflater;
	private Context context;
	
	//学号
//	private String stunum;
//	private Spinner attendanceTypeSpinner;
	public StudentAttendanceAdapter(Context context, ArrayList<Student> lists) {
		this.lists = lists;
		inflater = LayoutInflater.from(context);
		this.context = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return lists.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return lists.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(final int position, View view, ViewGroup arg2) {
		ViewHolder holder = null;
		if (view == null) {
			holder = new ViewHolder();
			view = inflater.inflate(R.layout.item_student_attendance, null);
			holder.tv_item_student_attendance_name = (TextView) view
					.findViewById(R.id.tv_item_student_attendance_name);
			holder.tv_item_student_attendance_stunum = (TextView) view
					.findViewById(R.id.tv_item_student_attendance_stunum);
			holder.sn_item_student_attendance_type = (Spinner) view
					.findViewById(R.id.sn_item_student_attendance_type);
			
//			attendanceTypeSpinner = holder.sn_item_student_attendance_type;
			
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}

		holder.tv_item_student_attendance_name.setText(lists.get(position).getName());
		holder.tv_item_student_attendance_stunum.setText(lists.get(position).getStunum().toString());
		
		//学号
//		stunum = lists.get(position).getStunum().toString();
		//监听选择的请假类型
		holder.sn_item_student_attendance_type.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent,
					View view, int position, long id) {
				String type = parent.getItemAtPosition(position).toString();
				if(!type.equals("已到")){
					//发送请假消息到后台
					sendStudentAttendance(context,lists.get(position).getId(),type);
				}

			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});
		return view;
	}

	private class ViewHolder {
		private TextView tv_item_student_attendance_name;
		private TextView tv_item_student_attendance_stunum;
		private Spinner sn_item_student_attendance_type;
	}

	// 发送请假消息到后台
	private void sendStudentAttendance(final Context context,String studentId, String type) {
		// 发送请假URL
		String sendStudentAttendanceUrl = JsonToBeanUtils.url + "addAttendance";
		Map<String, String> parameter = new HashMap<String, String>();
		parameter.put("sid", studentId);
//		parameter.put("stunum", stunum);
		parameter.put("type", type);
		parameter.put("message", "请假");
		parameter.put("uid", UserInfo.uid);

		// 网络请求获取工具书
		VolleyRequest.RequestPost(sendStudentAttendanceUrl, "addAttendance",
				parameter, new VolleyInterface() {
					// 请求失败
					@Override
					public void onfailure(VolleyError error) {
						// TODO Auto-generated method stub
						Toast.makeText(context, error.toString(),
								Toast.LENGTH_LONG).show();
					}

					// 请求成功
					@Override
					public void onSuccess(String result) {
						try {
							JSONObject jsonObj = new JSONObject(result);
							Msg msg = JsonToBeanUtils.JsonToMsg(jsonObj);
							Toast.makeText(context, msg.getMsg(),
									Toast.LENGTH_LONG).show();
							//成功之后禁用
//							attendanceTypeSpinner.setEnabled(false);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				});
	}

}
