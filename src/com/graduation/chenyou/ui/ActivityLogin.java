package com.graduation.chenyou.ui;



import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.buu.bysj.domain.JsonToBeanUtils;
import com.buu.bysj.domain.Msg;
import com.graduation.chenyou.R;
import com.graduation.chenyou.util.UserInfo;
import com.volley.VolleyInterface;
import com.volley.VolleyRequest;

public class ActivityLogin extends FragmentActivity implements OnClickListener {
	private EditText et_login_worknum;
	private EditText et_login_password;
	private TextView tv_go_register;
	private Button btn_login;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		getView2Init();

	}

	/**
	 * 获得布局文件上的控件并初始化
	 */
	private void getView2Init() {
		et_login_worknum = (EditText) findViewById(R.id.et_login_worknum);
		et_login_password = (EditText) findViewById(R.id.et_login_password);
		tv_go_register = (TextView) findViewById(R.id.tv_go_register);
		btn_login = (Button) findViewById(R.id.btn_login);
		//设置点击事件
		tv_go_register.setOnClickListener(this);
		btn_login.setOnClickListener(this);
	}
	
	
	//点击事件
	@Override
	public void onClick(View v) {
		if (v == tv_go_register) {
			Intent intent = new Intent(ActivityLogin.this, ActivityRegister.class);
			startActivity(intent);
		} else if (v == btn_login) {
			login();
		}
	}
	//登录

	private void login() {
		final String username = et_login_worknum.getText().toString().trim();
		final String password = et_login_password.getText().toString().trim();
		//[2.1]定义get方式要提交的路径    小细节 如果提交中文要对name 和 pwd 进行一个urlencode 编码 
		String loginUrl = JsonToBeanUtils.url +"loginTeacher";
		Map<String, String> parameter=new HashMap<String, String>();
		parameter.put("username",username);
		parameter.put("password",password);
		VolleyRequest.RequestPost(loginUrl, "login", parameter, new VolleyInterface() {
			
			@Override
			public void onfailure(VolleyError error) {
				// TODO Auto-generated method stuberror.toString()
				Toast.makeText(getApplicationContext(), "网络异常", Toast.LENGTH_LONG).show();
			}
			
			@Override
			public void onSuccess(String result) {
				 try {
					    JSONObject obj = new JSONObject(result);
						Msg msg = JsonToBeanUtils.JsonToMsg(obj);
						if(msg.getStatus().equals("error")){
							Toast.makeText(getApplicationContext(), msg.getMsg(), Toast.LENGTH_LONG).show();
						}else if(msg.getStatus().equals("success")){
							//保存登录信息
							UserInfo.save(getApplicationContext(), username, password,msg.getMsg());
							//跳转页面
							Intent intent = new Intent(ActivityLogin.this, MainActivity.class);
							startActivity(intent);
						}else{
							Toast.makeText(getApplicationContext(), "服务器繁忙，请稍后", Toast.LENGTH_LONG).show();;
						}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
	}

}
