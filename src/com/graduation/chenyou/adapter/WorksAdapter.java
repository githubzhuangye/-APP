package com.graduation.chenyou.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.android.smartimage.SmartImageView;
import com.buu.bysj.domain.JsonToBeanUtils;
import com.buu.bysj.domain.Works;
import com.graduation.chenyou.R;

public class WorksAdapter extends BaseAdapter {
	private ArrayList<Works> lists;
	private LayoutInflater inflater;
	private Context context;
	public WorksAdapter(Context context,ArrayList<Works> lists){
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
			view = inflater.inflate(R.layout.item_works, null);
			
			holder.siv_item_works_picurl = (SmartImageView) view.findViewById(R.id.siv_item_works_picurl);
			//这里依旧是那个问题
			holder.tv_item_works_name = (TextView) view.findViewById(R.id.tv_item_works_name);
			holder.tv_item_works_systime = (TextView) view.findViewById(R.id.tv_item_works_systime);
			view.setTag(holder);
		}else {
			holder = (ViewHolder) view.getTag();
		}
		holder.siv_item_works_picurl.setImageUrl(lists.get(position).getPicurl(),R.drawable.icon_logo);
		holder.tv_item_works_name.setText(lists.get(position).getName());
		holder.tv_item_works_systime.setText(JsonToBeanUtils.formatter1.format(lists.get(position).getSystime()));
		return view;
	}
	
	private class ViewHolder{
		private SmartImageView siv_item_works_picurl;
		private TextView tv_item_works_name;
		private TextView tv_item_works_systime;
	}
}
