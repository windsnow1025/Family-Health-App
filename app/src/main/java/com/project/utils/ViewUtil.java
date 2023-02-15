package com.project.utils;

import android.app.Activity;
import android.content.Context;
import android.text.InputFilter;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.lang.reflect.Field;

public class ViewUtil {

	/*获取最大长度限制*/
	public static int getMaxLength(EditText et) {
		int length = 0;
		try {
			InputFilter[] inputFilters = et.getFilters();
			for (InputFilter filter : inputFilters) {
				Class<?> c = filter.getClass();
				if (c.getName().equals("android.text.InputFilter$LengthFilter")) {
					Field[] f = c.getDeclaredFields();
					for (Field field : f) {
						if (field.getName().equals("mMax")) {
							field.setAccessible(true);
							length = (Integer) field.get(filter);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return length;
	}

	/*隐藏输入法键盘*/
	public static void hideMethod(Activity activity, View view) {
		InputMethodManager inputMethodManager=(InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
		inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),0);
	}
	
}
