package com.graduation.chenyou.util;

public class Utils {
	//将数组转成字符串数组
	public static String arrayToStringArray(String[] array) {
		String stringArray = "";
		for(int i = 0; i < array.length; i++){
			if(i == array.length -1){
				stringArray = stringArray + array[i];
			}else{
				stringArray = stringArray + array[i] +",";
			}
		}
		return stringArray;
	}
}
