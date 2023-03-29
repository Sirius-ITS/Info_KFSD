package com.kuwaitKFF.common;

import java.util.Locale;

import android.app.Activity;
import android.content.res.Resources;
import android.util.DisplayMetrics;

public class LanguageHelper {

	private Activity mContext;
	
	public LanguageHelper(Activity context)
	{
		mContext = context;
	}
	
	public void changeLanguage(String language)
	{
		Resources res = mContext.getResources();
	    
		// Change locale settings in the application.
	    DisplayMetrics dm = res.getDisplayMetrics();
	    android.content.res.Configuration conf = res.getConfiguration();
	    conf.locale = new Locale(language);
	    res.updateConfiguration(conf, dm);
	    
	    updateSavedLanguage(language);
	}
	
	private void updateSavedLanguage(String language)
	{
		MySharedPref.setLanguage(mContext, language);
	}
	
	
	
	
	
	
	
}
