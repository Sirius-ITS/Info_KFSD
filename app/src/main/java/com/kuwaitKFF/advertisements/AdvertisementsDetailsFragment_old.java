package com.kuwaitKFF.advertisements;

import java.util.ArrayList;


import com.kuwaitKFF.base.BaseActivity;
import com.kuwaitKFF.main.Kfsd;

import com.kuwaitKFF.R;

import android.view.KeyEvent;
import android.view.View;
import android.widget.ListView;

import butterknife.ButterKnife;

public class AdvertisementsDetailsFragment_old extends BaseActivity   {

	Kfsd kfsd;
	View view;
	ArrayList<String> circularList;
	ListView listView;
	
	public AdvertisementsDetailsFragment_old() {
		super(R.layout.fragment_circular_details);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initialization() {
		ButterKnife.bind(this);

	}

	@Override
	public void implementation() {
		kfsd = (Kfsd) getApplication();

		setHeader(getResources().getString(R.string.login), true, true);

	}


	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			// preventing default implementation previous to
			// android.os.Build.VERSION_CODES.ECLAIR
			return true;
		}
		return false;

	}

	/**
	 * Overrides the default implementation for KeyEvent.KEYCODE_BACK so that
	 * all systems call onBackPressed().
	 */

	public boolean onKeyUp(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			try {

				  finish();

			} catch (Exception e) {
				e.printStackTrace();
			}

			return true;

		}

		return false;
	}

	

	

}
