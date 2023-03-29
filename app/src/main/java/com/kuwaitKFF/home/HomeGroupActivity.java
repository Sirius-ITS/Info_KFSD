package com.kuwaitKFF.home;

import com.kuwaitKFF.R;
import com.kuwaitKFF.base.TabbarActivity;
import com.kuwaitKFF.common.MyCommon;
import com.kuwaitKFF.common.MySharedPref;
import com.kuwaitKFF.splash.SplashActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

public class HomeGroupActivity extends FragmentActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_group);
		 
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		HomeFragment bf = new HomeFragment();

		ft.replace(R.id.groupContent, bf, "HomeFrag");
		ft.disallowAddToBackStack();
		ft.commit();

		Log.d("MzTagFRAGG", "FRAGG");

	}


	@Override
	public void onBackPressed() {
		if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
			Intent i;
			if (MySharedPref.getLanguage(HomeGroupActivity.this).equals(
					MyCommon.LANGUAGE_ENG)) {

				Log.d("MzTagXXX", "Make English Tabs");

				i = new Intent();
				i.setClass(HomeGroupActivity.this, TabbarActivity.class);
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
						| Intent.FLAG_ACTIVITY_CLEAR_TOP);
				i.putExtra(TabbarActivity.TAB_ID, new Integer(0));
				startActivity(i);
				finish();
			} else if(MySharedPref.getLanguage(HomeGroupActivity.this).equals(MyCommon.LANGUAGE_AR)){

				Log.d("MzTagXXX", "Make Arabic Tabs");

				i = new Intent();
				i.setClass(HomeGroupActivity.this, TabbarActivity.class);
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
						| Intent.FLAG_ACTIVITY_CLEAR_TOP);
				i.putExtra(TabbarActivity.TAB_ID, new Integer(3));
				startActivity(i);
				finish();
			}
		} else {
			this.finish();
		}
	}

}
