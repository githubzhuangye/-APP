package com.graduation.chenyou.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
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
import android.widget.Toast;
import android.widget.ListView;

import com.android.volley.VolleyError;
import com.buu.bysj.domain.JsonToBeanUtils;
import com.buu.bysj.domain.Message;
import com.buu.bysj.domain.Msg;
import com.graduation.chenyou.R;
import com.graduation.chenyou.adapter.MessageAdapter;
import com.graduation.chenyou.util.UserInfo;
import com.volley.VolleyInterface;
import com.volley.VolleyRequest;

public class FragmentMessage extends Fragment implements OnClickListener {

	// 定义
	private EditText et_new_message_title;
	private EditText et_new_message_content;
	private Button btn_send_new_message;
	private ListView lv_message_list;

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
		View v = inflater.inflate(R.layout.fragment_message, container, false);

		// 初始化组件
		getView2Init(v);
		mContext = getActivity();
		setMessageListData(mContext);
		return v;
	}

	/**
	 * 获得布局文件上的控件并初始化
	 */
	private void getView2Init(View view) {
		et_new_message_title = (EditText) view
				.findViewById(R.id.et_new_message_title);
		et_new_message_content = (EditText) view
				.findViewById(R.id.et_new_message_content);
		btn_send_new_message = (Button) view
				.findViewById(R.id.btn_send_new_message);
		lv_message_list = (ListView) view.findViewById(R.id.lv_message_list);

		// 设置点击事件
		btn_send_new_message.setOnClickListener(this);
	}

	// 点击事件
	@Override
	public void onClick(View v) {
		// 点击新建公告
		if (v == btn_send_new_message) {
			addNewMessage(mContext);
		}
	}

	// 新建公告
	private void addNewMessage(final Context context) {
		String tilte = et_new_message_title.getText().toString().trim();// 公告标题
		String content = et_new_message_content.getText().toString().trim();// 公告内容
		if ("".equals(tilte) || "".equals(content)) {
			Toast.makeText(context, "请填写标题和内容", Toast.LENGTH_LONG).show();
		} else {
			// 获取工具书URL
			final String addMessageUrl = JsonToBeanUtils.url + "addMessage";
			final Map<String, String> parameter = new HashMap<String, String>();
			parameter.put("tid", UserInfo.uid);
			parameter.put("title", tilte);
			parameter.put("content", content);
			new Thread() {
				public void run() {
					// 网络请求
					VolleyRequest.RequestPost(addMessageUrl, "addMessage",
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
										JSONObject obj = new JSONObject(result);
										// 日志列表
										final Msg msg = JsonToBeanUtils
												.JsonToMsg(obj);
										if (msg.getStatus().equals("success")) {
											// 刷新数据
											et_new_message_title.setText(null);
											et_new_message_content.setText(null);
											setMessageListData(context);
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

	// 填充公告数据
	private void setMessageListData(final Context context) {
		// 获取收藏的投票
		String getTeacherMessageUrl = JsonToBeanUtils.url + "getTeacherMessage";
		Map<String, String> parameter = new HashMap<String, String>();
		parameter.put("tid", UserInfo.uid);
		// 网络请求
		VolleyRequest.RequestPost(getTeacherMessageUrl, "getTeacherMessage",
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
							final ArrayList<Message> messageList = (ArrayList<Message>) JsonToBeanUtils
									.JsonToMessageList(arr);

							// 设置数据设配器
							lv_message_list.setAdapter(new MessageAdapter(
									context, messageList));
							// 设置公告列表item项点击事件
//							lv_message_list
//									.setOnItemClickListener(new OnItemClickListener() {
//										@Override
//										public void onItemClick(AdapterView<?> arg0, View view,int position, long arg3) {
//											// TODO Auto-generated method stub
//											String messageId = messageList.get(position).getId();
//											Intent intent = new Intent(context,ActivityMessageDetail.class);
//											intent.putExtra("messageId", messageId     );
//											startActivity(intent);
//										}
//									});
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				});

	}

}
