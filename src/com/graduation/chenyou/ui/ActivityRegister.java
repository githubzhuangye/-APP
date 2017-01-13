package com.graduation.chenyou.ui;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.AlignmentSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.buu.bysj.domain.JsonToBeanUtils;
import com.buu.bysj.domain.Msg;
import com.graduation.chenyou.R;
import com.graduation.chenyou.util.FileImageUpload;
import com.graduation.chenyou.util.ModifyAvatarDialog;
import com.volley.VolleyInterface;
import com.volley.VolleyRequest;

public class ActivityRegister extends Activity implements OnClickListener {
	private ImageView iv_register_arrow_back;
	private EditText et_register_username;
	private EditText et_register_worknum;
	private EditText et_register_phone;
	private EditText et_register_password;
	private EditText et_register_repassword;
	private Spinner sn_register_classname;
	private Button btn_register;

	private String classname = "run";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);

		getView2Init();
	}

	// /**
	// * 获得布局文件上的控件并初始化
	// */
	private void getView2Init() {
		iv_register_arrow_back = (ImageView) findViewById(R.id.iv_register_arrow_back);
		et_register_username = (EditText) findViewById(R.id.et_register_username);
		et_register_worknum = (EditText) findViewById(R.id.et_register_worknum);
		et_register_phone = (EditText) findViewById(R.id.et_register_phone);
		et_register_password = (EditText) findViewById(R.id.et_register_password);
		et_register_repassword = (EditText) findViewById(R.id.et_register_repassword);
		sn_register_classname = (Spinner) findViewById(R.id.sn_register_classname);
		btn_register = (Button) findViewById(R.id.btn_register);

		sn_register_classname
				.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
					@Override
					public void onItemSelected(AdapterView<?> parent,
							View view, int position, long id) {
						classname = sn_register_classname.getSelectedItem().toString();

					}

					@Override
					public void onNothingSelected(AdapterView<?> parent) {
						// TODO Auto-generated method stub

					}
				});
		// 设置点击事件
		iv_register_arrow_back.setOnClickListener(this);
		btn_register.setOnClickListener(this);
	}

	// 点击事件
	@Override
	public void onClick(View v) {
		// 返回
		if (v == iv_register_arrow_back) {
			finish();
		} else if (v == btn_register) {
			// 注册
			register();
		}
	}

	// 注册
	private void register() {
		final String username = et_register_username.getText().toString()
				.trim();
		final String worknum = et_register_worknum.getText().toString()
				.trim();
		final String phone = et_register_phone.getText().toString()
				.trim();
		final String password = et_register_password.getText().toString()
				.trim();
		final String repassword = et_register_repassword.getText().toString()
				.trim();
		//校验用户名，工号，手机号不能为空
		if ("".equals(username) || "".equals(worknum) || "".equals(phone)) {
			Toast.makeText(getApplicationContext(), "用户名，工号，手机号不能为空",
					Toast.LENGTH_LONG).show();
			return;
		}
		// 校验密码和确认密码
		if (!password.equals(repassword)) {
			Toast.makeText(getApplicationContext(), "密码与确认密码不一致",
					Toast.LENGTH_LONG).show();
			return;
		}
		// [2.1]定义get方式要提交的路径 小细节 如果提交中文要对name 和 pwd 进行一个urlencode 编码
		String registerUrl = JsonToBeanUtils.url + "registerTeacher";
		Map<String, String> parameter = new HashMap<String, String>();
		parameter.put("username", username);
		parameter.put("worknum", worknum);
		parameter.put("password", password);
		parameter.put("phone", phone);
		parameter.put("classname", classname);
		VolleyRequest.RequestPost(registerUrl, "register", parameter,
				new VolleyInterface() {
					// 网络失败
					@Override
					public void onfailure(VolleyError error) {
						// error.toString()
						Toast.makeText(getApplicationContext(), "网络异常！",
								Toast.LENGTH_LONG).show();
					}

					// 网络成功
					@Override
					public void onSuccess(String result) {
						try {
							JSONObject obj = new JSONObject(result);
							Msg msg = JsonToBeanUtils.JsonToMsg(obj);
							// 注册失败
							if (msg.getStatus().equals("error")) {
								Toast.makeText(getApplicationContext(),
										msg.getMsg(), Toast.LENGTH_LONG).show();
								// 注册成功
							} else if (msg.getStatus().equals("success")) {
								Toast.makeText(getApplicationContext(), "注册成功",
										Toast.LENGTH_LONG).show();
								//跳转页面
								Intent intent = new Intent(ActivityRegister.this, ActivityLogin.class);
								startActivity(intent);
							} else {
								Toast.makeText(getApplicationContext(),
										"服务器繁忙，请稍后", Toast.LENGTH_LONG).show();
							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				});
	}
}
