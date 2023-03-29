package com.kuwaitKFF.news;

import com.kuwaitKFF.R;

import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

public class NewsGroupActivity extends FragmentActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_group);

		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		NewsFragment bf = new NewsFragment();

		ft.replace(R.id.groupContent, bf,
				getResources().getString(R.string.news));
		ft.disallowAddToBackStack();
	
		ft.commit();

	}

	@Override
	public void onBackPressed() {
		if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
			getSupportFragmentManager().popBackStack();
		} else {
			this.finish();
		}
	}

}
