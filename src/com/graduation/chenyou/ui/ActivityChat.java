package com.graduation.chenyou.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.buu.bysj.domain.Chat;
import com.buu.bysj.domain.JsonToBeanUtils;
import com.buu.bysj.domain.Msg;
import com.graduation.chenyou.R;
import com.graduation.chenyou.adapter.ChatAdapter;
import com.graduation.chenyou.util.UserInfo;
import com.volley.VolleyInterface;
import com.volley.VolleyRequest;

public class ActivityChat extends Activity implements OnClickListener {

	private ImageView iv_chat_back_arrow;// 返回
	private ListView lv_all_chat_list;// 私信列表
	private EditText et_new_chat_content;
	private Button btn_send_new_chat;

	private Context mContext;
	private String studentId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chat);
		// 获取上下文
		mContext = getApplicationContext();
		// 获取home页面传递的日志ID
		Intent intent = getIntent(); // 获取开启此Activity的意图对象
		studentId = intent.getStringExtra("studentId");

		// 控件并初始化
		getView2Init();
		// 填充数据
		setAllChatListData(mContext);

	}

	/**
	 * 获得布局文件上的控件并初始化
	 */
	private void getView2Init() {
		iv_chat_back_arrow = (ImageView) findViewById(R.id.iv_chat_back_arrow);
		lv_all_chat_list = (ListView) this.findViewById(R.id.lv_all_chat_list);
		et_new_chat_content = (EditText) findViewById(R.id.et_new_chat_content);
		btn_send_new_chat = (Button) findViewById(R.id.btn_send_new_chat);
		// 设置点击事件
		iv_chat_back_arrow.setOnClickListener(this);
		btn_send_new_chat.setOnClickListener(this);
	}

	// 点击事件
	@Override
	public void onClick(View v) {
		if (v == iv_chat_back_arrow) {
			// 返回
			finish();
		} else if (v == btn_send_new_chat) {
			// 发送新私信
			sendNewChat(mContext);
		}

	}

	// 填充聊天列表数据
	private void setAllChatListData(final Context context) {
		new Thread() {
			@Override
			public void run() {
				// 获取工具书URL
				String getAllChatUrl = JsonToBeanUtils.url + "getStudentChat";
				Map<String, String> parameter = new HashMap<String, String>();
				parameter.put("tid", UserInfo.uid);
				parameter.put("sid", studentId);

				// 网络请求
				VolleyRequest.RequestPost(getAllChatUrl, "getStudentChat", parameter,
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
									JSONArray arr = new JSONArray(result);
									ArrayList<Chat> chatList = (ArrayList<Chat>) JsonToBeanUtils.JsonToChatList(arr);
									// 设置数据设配器
									lv_all_chat_list.setAdapter(new ChatAdapter(context, chatList));
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

							}
						});

			}
		}.start();
	}

	// 发送聊天
	private void sendNewChat(final Context context) {
		String content = et_new_chat_content.getText().toString().trim();// 聊天内容
		if ("".equals(content)) {
			Toast.makeText(context, "请填写内容", Toast.LENGTH_LONG).show();
		} else {
			// 获取工具书URL
			final String sendNewChatUrl = JsonToBeanUtils.url + "addChat";
			final Map<String, String> parameter = new HashMap<String, String>();
			parameter.put("sid", studentId);
			parameter.put("tid", UserInfo.uid);
			parameter.put("content", content);
			parameter.put("user", "teacher");
			new Thread() {

				public void run() {
					// 网络请求获取工具书
					VolleyRequest.RequestPost(sendNewChatUrl,"addChat", parameter,
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
										JSONObject obj = new JSONObject(result);
										// 日志列表
										final Msg msg = JsonToBeanUtils
												.JsonToMsg(obj);
										if (msg.getStatus().equals("success")) {
											//清空数据
											et_new_chat_content.setText(null);
											//刷新数据
											setAllChatListData(context);
										}
									} catch (Exception e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}

								}
							});

				}
			}.start();
		}

	}
}
