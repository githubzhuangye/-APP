package com.graduation.chenyou.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.graduation.chenyou.R;
import com.graduation.chenyou.adapter.TabStudentAdapter;
import com.viewpagerindicator.TabPageIndicator;

public class FragmentStudent extends Fragment {
	   private TabStudentAdapter TabStudentAdapter;
	    private ViewPager vp_student_tab_viewpager;
	    private TabPageIndicator ti_student_tab_title;
	    private static int mCurrentSubFragmentSeq = 0;
	    private String[] tabTitle = {"学生列表","添加学生"};
	    
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setRetainInstance(true);
	    }

	    @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	        View v = inflater.inflate(R.layout.fragment_tab_student, container, false);
	        TabStudentAdapter = new TabStudentAdapter(getFragmentManager(),tabTitle);
	        vp_student_tab_viewpager = (ViewPager) v.findViewById(R.id.vp_student_tab_viewpager);
	        vp_student_tab_viewpager.setAdapter(TabStudentAdapter);
	        
	        ti_student_tab_title = (TabPageIndicator) v.findViewById(R.id.ti_student_tab_title);
	        ti_student_tab_title.setViewPager(vp_student_tab_viewpager, mCurrentSubFragmentSeq);

	        ti_student_tab_title.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
	            @Override
	            public void onPageSelected(int position) {
	                mCurrentSubFragmentSeq = position;
	            }

	            @Override
	            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
	            }

	            @Override
	            public void onPageScrollStateChanged(int state) {
	            }
	        });
	        return v;
	    }
	    

}
