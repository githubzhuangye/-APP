package com.graduation.chenyou.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.smartimage.SmartImageView;
import com.android.volley.VolleyError;
import com.buu.bysj.domain.JsonToBeanUtils;
import com.buu.bysj.domain.Message;
import com.buu.bysj.domain.Msg;
import com.buu.bysj.domain.Student;
import com.graduation.chenyou.R;
import com.graduation.chenyou.ui.ActivityAddStudentWorks;
import com.graduation.chenyou.ui.ActivityChat;
import com.graduation.chenyou.util.UserInfo;
import com.volley.VolleyInterface;
import com.volley.VolleyRequest;

public class StudentAdapter extends BaseAdapter {
	private ArrayList<Student> lists;
	private LayoutInflater inflater;
	private Context context;

	public StudentAdapter(Context context, ArrayList<Student> lists) {
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
			view = inflater.inflate(R.layout.item_student, null);
			holder.tv_item_student_name = (TextView) view
					.findViewById(R.id.tv_item_student_name);
			holder.tv_item_student_stunum = (TextView) view
					.findViewById(R.id.tv_item_student_stunum);
			holder.tv_item_student_sex = (TextView) view
					.findViewById(R.id.tv_item_student_sex);
			holder.tv_item_student_worksnum = (TextView) view
					.findViewById(R.id.tv_item_student_worksnum);
			holder.tv_item_student_delete = (TextView) view
					.findViewById(R.id.tv_item_student_delete);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}

		holder.tv_item_student_name.setText(lists.get(position).getName());
		holder.tv_item_student_stunum.setText(lists.get(position).getStunum());
		holder.tv_item_student_sex.setText(lists.get(position).getSex());
		holder.tv_item_student_worksnum.setText(lists.get(position)
				.getWorksnum().toString());

		// 添加学生作品
		holder.tv_item_student_worksnum.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				String studentId = lists.get(position).getId();
				Intent intent = new Intent(context, ActivityAddStudentWorks.class);
				intent.putExtra("studentId", studentId);
				context.startActivity(intent);
			}

		});

		// 学生删除事件
		holder.tv_item_student_delete.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				deleteStudent(context, lists.get(position).getId(),position);
			}

		});
		return view;
	}

	private class ViewHolder {
		private TextView tv_item_student_name;
		private TextView tv_item_student_stunum;
		private TextView tv_item_student_sex;
		private TextView tv_item_student_worksnum;
		private TextView tv_item_student_delete;
	}

	// 删除学生
	private void deleteStudent(final Context context, String studentId,final int position) {
		// 删除URL
		String deleteStudentUrl = JsonToBeanUtils.url + "deleteStudent";
		Map<String, String> parameter = new HashMap<String, String>();
		parameter.put("sid", studentId);
		parameter.put("uid", UserInfo.uid);

		// 网络请求获取工具书
		VolleyRequest.RequestPost(deleteStudentUrl, "deleteStudent", parameter,
				new VolleyInterface() {
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
							lists.remove(position);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				});
	}

}
