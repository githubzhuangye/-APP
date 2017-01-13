package com.graduation.chenyou.adapter;

import com.graduation.chenyou.ui.FragmentAddStudent;
import com.graduation.chenyou.ui.FragmentStudentList;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;


public class TabStudentAdapter extends FragmentStatePagerAdapter {
	private String[] tabTilte;
	public TabStudentAdapter(FragmentManager fm,String[] tabTitle) {
		super(fm);
		 this.tabTilte = tabTitle;
	}

	@Override
	public Fragment getItem(int position) {
		switch (position) {
		case 0:
			return new FragmentStudentList();
		case 1:
			return new FragmentAddStudent();
		}
		return null;
	}

	@Override
	public int getCount() {
		return tabTilte.length;
	}
	
	@Override
	public CharSequence getPageTitle(int position) {
		return tabTilte[position];
	}

}

