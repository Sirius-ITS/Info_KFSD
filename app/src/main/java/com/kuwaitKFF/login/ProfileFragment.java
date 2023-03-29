package com.kuwaitKFF.login;


import com.kuwaitKFF.base.BaseActivity;
import com.kuwaitKFF.common.MyCommon;
import com.kuwaitKFF.common.MySharedPref;
import com.kuwaitKFF.main.Kfsd;

import com.kuwaitKFF.R;

import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import butterknife.ButterKnife;

public class ProfileFragment extends BaseActivity implements OnClickListener {

	Kfsd kfsd;
	View view; 
	
	
	TextView nameText,userNameText,fileNoText,civilIdText;

	public ProfileFragment() {
		super(R.layout.fragment_profile);

	}

	@Override
	public void initialization()  {
		ButterKnife.bind(this);

	}

	@Override
	public void implementation() {
		kfsd = (Kfsd) getApplication();
		
		nameText = (TextView)findViewById(R.id.nameText);
		userNameText = (TextView)findViewById(R.id.userNameText);
		fileNoText = (TextView)findViewById(R.id.fileNoText);
		civilIdText = (TextView)findViewById(R.id.civilIdText);
		
	    if(MyCommon.sLoginResponseBean != null)
	    {
	    	if(MySharedPref.getLanguage(this).equalsIgnoreCase(MyCommon.LANGUAGE_AR))
	    	{
	    		nameText.setText(MyCommon.sLoginResponseBean.getNameAr());
	    	}else
	    	{
	    		nameText.setText(MyCommon.sLoginResponseBean.getNameEn());
	    	}
	    	
//	    	if(MySharedPref.getLanguage(this).equalsIgnoreCase(MyCommon.LANGUAGE_AR))
//	    	{
//	    		userNameText.setText(MyCommon.sLoginResponseBean.getNameAr());
//	    	}else
//	    	{
//	    		userNameText.setText(MyCommon.sLoginResponseBean.getNameEn());
//	    	}
//
			userNameText.setText(MyCommon.sLoginResponseBean.getLoginId());
			fileNoText.setText(MyCommon.sLoginResponseBean.getFileNo());
	    	civilIdText.setText(MyCommon.sLoginResponseBean.getCivilId());
	    	
	    }

		setHeader(getResources().getString(R.string.employee_profile), true, true);

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

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		default:
			break;
		}

	}

}
