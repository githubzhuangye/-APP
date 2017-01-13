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
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.smartimage.SmartImageView;
import com.android.volley.VolleyError;
import com.buu.bysj.domain.JsonToBeanUtils;
import com.buu.bysj.domain.Message;
import com.buu.bysj.domain.Msg;
import com.graduation.chenyou.R;
import com.graduation.chenyou.util.UserInfo;
import com.volley.VolleyInterface;
import com.volley.VolleyRequest;

public class MessageAdapter extends BaseAdapter {
	private ArrayList<Message> lists;
	private LayoutInflater inflater;
	private Context context;

	public MessageAdapter(Context context, ArrayList<Message> lists) {
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
			view = inflater.inflate(R.layout.item_message, null);
			holder.tv_item_message_title = (TextView) view
					.findViewById(R.id.tv_item_message_title);
			holder.tv_item_message_delete_text = (TextView) view
					.findViewById(R.id.tv_item_message_delete_text);
			holder.tv_item_message_systime = (TextView) view
					.findViewById(R.id.tv_item_message_systime);
			holder.tv_item_message_content = (TextView) view
					.findViewById(R.id.tv_item_message_content);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}

		holder.tv_item_message_title.setText(lists.get(position).getTitle());
		holder.tv_item_message_systime.setText(JsonToBeanUtils.formatter1
				.format(lists.get(position).getSystime()));
		holder.tv_item_message_content
				.setText(lists.get(position).getContent());

		// 公告删除事件
		holder.tv_item_message_delete_text
				.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						deleteMessage(context, lists.get(position).getId(),position);
					}

				});
		return view;
	}

	private class ViewHolder {
		private TextView tv_item_message_title;
		private TextView tv_item_message_delete_text;
		private TextView tv_item_message_systime;
		private TextView tv_item_message_content;
	}

	// 删除公告
	private void deleteMessage(final Context context, String mid,final int position) {
		// 删除URL
		String deleteMessageUrl = JsonToBeanUtils.url + "deleteMessage";
		Map<String, String> parameter = new HashMap<String, String>();
		parameter.put("id", mid);
		parameter.put("uid", UserInfo.uid);

		// 网络请求获取工具书
		VolleyRequest.RequestPost(deleteMessageUrl, "deleteMessage",
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
//							lists.remove(position);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				});
	}

}
