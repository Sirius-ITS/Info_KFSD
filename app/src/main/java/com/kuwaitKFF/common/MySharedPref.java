package com.kuwaitKFF.common;



import android.app.Activity;
import android.app.Application;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class MySharedPref {
	
	private static final String PREFS_NAME = "Alshaya_SharedPrefrence_Name";
	private static final String KEY_LANGUAGE = "Key_Language";

	
	public static void setLanguage(Activity a, String language) {
		SharedPreferences sp = a.getSharedPreferences(PREFS_NAME, 0);
		Editor editor = sp.edit();
		editor.putString(KEY_LANGUAGE, language);
		editor.commit();
	}
	public static String getLanguage(Activity a) {

		SharedPreferences sp = a.getSharedPreferences(PREFS_NAME, 0);
		return sp.getString(KEY_LANGUAGE, "");
	}

	public static String getLanguage(Application a) {
		SharedPreferences sp = a.getSharedPreferences(PREFS_NAME, 0);
		return sp.getString(KEY_LANGUAGE, "");

	}
	
	
	
}
