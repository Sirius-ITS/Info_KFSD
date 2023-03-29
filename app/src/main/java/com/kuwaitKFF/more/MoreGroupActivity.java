package com.kuwaitKFF.more;

import com.kuwaitKFF.R;

import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

public class MoreGroupActivity extends FragmentActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_group);

		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		MoreFragment bf = new MoreFragment();

		ft.replace(R.id.groupContent, bf,
				getResources().getString(R.string.more));
		
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
