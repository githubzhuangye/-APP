package com.volley;

import com.android.volley.RequestQueue;

import com.android.volley.toolbox.Volley;

import android.app.Application;
/**
 * 全局application
 * @author dingliuquan
 *
 */
public class Application_Volley extends Application {
	public static RequestQueue quenes;
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		quenes=Volley.newRequestQueue(getApplicationContext());
	}
	public static RequestQueue gethttp(){
		
		return quenes;
	}
}
