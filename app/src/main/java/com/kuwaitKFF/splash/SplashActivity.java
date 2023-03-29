package com.kuwaitKFF.splash;

import com.kuwaitKFF.R;
import com.kuwaitKFF.base.TabbarActivity;
import com.kuwaitKFF.common.LanguageHelper;
import com.kuwaitKFF.common.MyCommon;
import com.kuwaitKFF.common.MySharedPref;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

import butterknife.ButterKnife;

public class SplashActivity extends Activity {

	RelativeLayout rlSplash;
	Intent i;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		ButterKnife.bind(this);


		implementation();
	}

	public void implementation() {
		playAnimation();


		if(MySharedPref.getLanguage(this).equals(MyCommon.LANGUAGE_ENG))
		{
			Log.d("MzTag333", "English");
			saveSelectedLanguage(MyCommon.LANGUAGE_ENG);
		}
		else if(MySharedPref.getLanguage(this).equals(MyCommon.LANGUAGE_AR))
		{
			saveSelectedLanguage(MyCommon.LANGUAGE_AR);
			Log.d("MzTag333", "Arabic");
		}
		else {
			Log.d("MzTag333", "OTHEERRR");
			saveSelectedLanguage(MyCommon.LANGUAGE_AR);
		}
		//	saveSelectedLanguage(MyCommon.LANGUAGE_AR);
		//	 saveSelectedLanguage(MyCommon.LANGUAGE_ENG);

	}

	/**
	 * Method for saving selected language to sharePref
	 */
	private void saveSelectedLanguage(String language) {
		LanguageHelper helper = new LanguageHelper(this);
		helper.changeLanguage(language);
	}

	private void playAnimation() {
		// Splash animation setting
		Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(this,
				R.anim.splash_hyperspace_jump);
		rlSplash = (RelativeLayout) findViewById(R.id.Splash_RL);
		rlSplash.startAnimation(hyperspaceJumpAnimation);

		try {
			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {


					if (MySharedPref.getLanguage(SplashActivity.this).equals(
							MyCommon.LANGUAGE_ENG)) {

						Log.d("MzTagXXX", "Make English Tabs");
				
						i = new Intent();
						i.setClass(SplashActivity.this, TabbarActivity.class);
						i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
								| Intent.FLAG_ACTIVITY_CLEAR_TOP);
						i.putExtra(TabbarActivity.TAB_ID, new Integer(0));
						startActivity(i);
						finish();
					} else if(MySharedPref.getLanguage(SplashActivity.this).equals(MyCommon.LANGUAGE_AR)){

						Log.d("MzTagXXX", "Make Arabic Tabs");

						i = new Intent();
						i.setClass(SplashActivity.this, TabbarActivity.class);
						i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
								| Intent.FLAG_ACTIVITY_CLEAR_TOP);
						i.putExtra(TabbarActivity.TAB_ID, new Integer(3));
						startActivity(i);
						finish();
					}

				}
			}, 2000);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
