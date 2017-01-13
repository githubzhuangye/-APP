package com.graduation.chenyou.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.smartimage.SmartImageView;
import com.buu.bysj.domain.Chat;
import com.buu.bysj.domain.JsonToBeanUtils;
import com.graduation.chenyou.R;

public class ChatAdapter extends BaseAdapter {
	private ArrayList<Chat> lists;
	private LayoutInflater inflater;
	private Context context;
	public ChatAdapter(Context context,ArrayList<Chat> lists){
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
	public View getView(int position, View view, ViewGroup arg2) {
		ViewHolder holder = null;
		if(view == null){
			holder = new ViewHolder();
			view = inflater.inflate(R.layout.item_chat, null);
			
			holder.siv_item_chat_user_picurl = (ImageView) view.findViewById(R.id.siv_item_chat_user_picurl);
			//这里依旧是那个问题
			holder.tv_item_chat_user_name = (TextView) view.findViewById(R.id.tv_item_chat_user_name);
			holder.tv_item_chat_systime = (TextView) view.findViewById(R.id.tv_item_chat_systime);
			holder.tv_item_chat_content = (TextView) view.findViewById(R.id.tv_item_chat_content);
			view.setTag(holder);
		}else {
			holder = (ViewHolder) view.getTag();
		}
		
		if(lists.get(position).getUser().equals("student")){
			holder.tv_item_chat_user_name.setText(lists.get(position).getSid().getName());
			holder.siv_item_chat_user_picurl.setImageDrawable(context.getResources().getDrawable(
					R.drawable.icon_parent));
		}else{
			holder.tv_item_chat_user_name.setText(lists.get(position).getTid().getUsername());
			holder.siv_item_chat_user_picurl.setImageDrawable(context.getResources().getDrawable(
					R.drawable.icon_teacher));
		}
		
		holder.tv_item_chat_systime.setText(JsonToBeanUtils.formatter1.format(lists.get(position).getSystime()));
		holder.tv_item_chat_content.setText(lists.get(position).getContent());
		return view;
	}
	
	private class ViewHolder{
		private ImageView siv_item_chat_user_picurl;
		private TextView tv_item_chat_user_name;
		private TextView tv_item_chat_systime;
		private TextView tv_item_chat_content;
	}

}
