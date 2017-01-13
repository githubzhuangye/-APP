package com.volley;

import java.util.Map;

import com.android.volley.AuthFailureError;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.StringRequest;

/**
 * volley的二次回调封装
 * @author dingliuquan
 *
 */
public class VolleyRequest {
	private static StringRequest stringRequest;
	public static void RequestGet(String url,String tag,VolleyInterface VI){
		Application_Volley.gethttp().cancelAll(tag);
		stringRequest=new StringRequest(Method.GET, url,
				VI.loadingListener(),VI.failListener());
		//添加tag并执行
		stringRequest.setTag(tag);
		Application_Volley.gethttp().add(stringRequest);
		Application_Volley.gethttp().start();
	}
	public static void RequestPost(String url,String tag,
			final Map<String,String> params,VolleyInterface VI){
		Application_Volley.gethttp().cancelAll(tag);
		stringRequest=new StringRequest(Method.POST, url,
				VI.loadingListener(),VI.failListener()){
			protected Map<String, String> getParams()
					throws AuthFailureError {
				return params;
			}
		};
		//添加tag并执行
		stringRequest.setTag(tag);
		Application_Volley.gethttp().add(stringRequest);
		Application_Volley.gethttp().start();
	}
}
