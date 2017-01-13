package com.volley;

import android.content.Context;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;

public abstract class VolleyInterface {
//	public Context context;
	public static Listener<String> listener;
	public static ErrorListener errorlistener;
//	public VolleyInterface(Context mcontext) {
////		this.context=mcontext;
//	}
	public abstract void onSuccess(String result);
	public abstract void onfailure(VolleyError error);
	public Listener<String> loadingListener(){
		listener=new Listener<String>() {
			@Override
			public void onResponse(String arg0) {
				//弹出加载对话框
				onSuccess(arg0);
			}
		};
		return listener;
	}
	public ErrorListener failListener(){
		errorlistener=new ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError arg0) {
				onfailure(arg0);
				//提示请求失败
			}
		};
		return errorlistener;
	}
}
