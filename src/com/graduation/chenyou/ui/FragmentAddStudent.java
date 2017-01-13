package com.graduation.chenyou.ui;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.buu.bysj.domain.JsonToBeanUtils;
import com.buu.bysj.domain.Msg;
import com.graduation.chenyou.R;
import com.graduation.chenyou.util.UserInfo;
import com.volley.VolleyInterface;
import com.volley.VolleyRequest;

/**
 *学生
 * @author xxf
 *
 */

public class FragmentAddStudent extends Fragment implements OnClickListener {
	// 定义控件
	private EditText et_add_student_name;
	private EditText et_add_student_stunum;
	private RadioGroup rg_add_student_sex;
	private RadioButton rb_add_student_sex_male;
	private RadioButton rb_add_student_sex_female;
	private Button btn_save_add_student;
	private Button btn_cancel_add_student;
	
	//性别
	private String sex = "男";
	protected Context mContext;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_add_student, container,
				false);

		mContext = getActivity();

		// 获取控件
		getView2Init(v);
		return v;
	}

	/**
	 * 获得布局文件上的控件并初始化
	 */
	private void getView2Init(View view) {
		et_add_student_name = (EditText) view
				.findViewById(R.id.et_add_student_name);
		et_add_student_stunum = (EditText) view
				.findViewById(R.id.et_add_student_stunum);
		rg_add_student_sex = (RadioGroup) view
				.findViewById(R.id.rg_add_student_sex);
		rb_add_student_sex_male = (RadioButton) view
				.findViewById(R.id.rb_add_student_sex_male);
		rb_add_student_sex_female = (RadioButton) view
				.findViewById(R.id.rb_add_student_sex_female);
		//按钮
		btn_save_add_student = (Button) view
				.findViewById(R.id.btn_save_add_student);
		btn_cancel_add_student = (Button) view
				.findViewById(R.id.btn_cancel_add_student);
		
		// 监听获取性别类型
		rg_add_student_sex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
							@Override
							public void onCheckedChanged(RadioGroup group, int checkedId) {
								if (rb_add_student_sex_male.getId() == checkedId) {
									sex = "男";
								} else if (rb_add_student_sex_female.getId() == checkedId) {
									sex = "女";
								}
							}
						});

		// 设置点击事件
		btn_save_add_student.setOnClickListener(this);
		btn_cancel_add_student.setOnClickListener(this);
	}

	// 点击事件
	@Override
	public void onClick(View v) {
		if (v == btn_save_add_student) {
			//保存
			saveNewStudent(mContext);
		} else if (v == btn_cancel_add_student) {
			//取消
			cancelAddStudent();
		}

	}
		//取消
		private void cancelAddStudent() {
			et_add_student_name.setText(null);
			et_add_student_stunum.setText(null);
			sex = "男";
		}

		
		//保存
		private void saveNewStudent(final Context context) {
			final String name = et_add_student_name.getText().toString()
					.trim();
			final String stunum = et_add_student_stunum.getText().toString()
					.trim();
			String addStudentUrl = JsonToBeanUtils.url + "addStudent";
			Map<String, String> parameter = new HashMap<String, String>();
			parameter.put("name", name);
			parameter.put("stunum", stunum);
			parameter.put("sex", sex);
			parameter.put("tid", UserInfo.uid);
			VolleyRequest.RequestPost(addStudentUrl, "addStudent", parameter,
					new VolleyInterface() {
						// 网络失败
						@Override
						public void onfailure(VolleyError error) {
							// error.toString()
							Toast.makeText(context, "网络异常！",
									Toast.LENGTH_LONG).show();
						}

						// 网络成功
						@Override
						public void onSuccess(String result) {
							try {
								JSONObject obj = new JSONObject(result);
								Msg msg = JsonToBeanUtils.JsonToMsg(obj);
								// 保存失败
								if (msg.getStatus().equals("error")) {
									Toast.makeText(context,msg.getMsg(), Toast.LENGTH_LONG).show();
									// 保存成功
								} else if (msg.getStatus().equals("success")) {
									Toast.makeText(context, msg.getMsg(),Toast.LENGTH_LONG).show();
									cancelAddStudent();
								} else {
									Toast.makeText(context,"服务器繁忙，请稍后", Toast.LENGTH_LONG).show();
								}
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						}
					});
		}

}
