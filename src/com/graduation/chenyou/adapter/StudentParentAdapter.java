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

public class StudentParentAdapter extends BaseAdapter {
	private ArrayList<Student> lists;
	private LayoutInflater inflater;
	private Context context;
	
	//学号
	public StudentParentAdapter(Context context, ArrayList<Student> lists) {
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
			view = inflater.inflate(R.layout.item_student_parent, null);
			holder.tv_item_student_parent_name = (TextView) view
					.findViewById(R.id.tv_item_student_parent_name);
			holder.tv_item_student_parent_stunum = (TextView) view
					.findViewById(R.id.tv_item_student_parent_stunum);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}

		holder.tv_item_student_parent_name.setText(lists.get(position).getName());
		holder.tv_item_student_parent_stunum.setText(lists.get(position).getStunum().toString());
		return view;
	}

	private class ViewHolder {
		private TextView tv_item_student_parent_name;
		private TextView tv_item_student_parent_stunum;
	}


}
