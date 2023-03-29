package com.kuwaitKFF.more;

import java.util.ArrayList;
import java.util.HashMap;

import com.kuwaitKFF.base.BaseFragment;
import com.kuwaitKFF.base.TabbarActivity;
import com.kuwaitKFF.common.LanguageHelper;
import com.kuwaitKFF.common.MyCommon;
import com.kuwaitKFF.common.MySharedPref;
import com.kuwaitKFF.main.Kfsd;

import com.kuwaitKFF.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import android.widget.LinearLayout;
import android.widget.TextView;

import android.widget.ExpandableListView;

public class SettingsFragment extends BaseFragment implements OnClickListener  {

	Kfsd kfsd;
	View view;
	ArrayList<String> listDataHeader;
	
	LinearLayout englishText,arabicText;

	
	MoreExpandableListAdapter listAdapter;
	ExpandableListView expListView;

	HashMap<String, ArrayList<String>> listDataChild;
	Intent i;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		kfsd = (Kfsd) getActivity().getApplication();

		view = LayoutInflater.from(getActivity()).inflate(
				R.layout.fragment_settings, null);
		
		englishText = (LinearLayout) view.findViewById(R.id.englishText);
		englishText.setOnClickListener(this);
		arabicText = (LinearLayout) view.findViewById(R.id.arabicText);
		arabicText.setOnClickListener(this);
		
		setHeader(view, getResources().getString(R.string.change_language), true, true);

		return view;
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

				getFragmentManager().popBackStack();

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
		case R.id.englishText:
			saveSelectedLanguage(MyCommon.LANGUAGE_ENG);
			startNewFragment();
			break;
			
        case R.id.arabicText:
        	saveSelectedLanguage(MyCommon.LANGUAGE_AR);
        	startNewFragment();
			break;


		default:
			break;
		}
		
	}

	
	private void saveSelectedLanguage(String language) {
		LanguageHelper helper = new LanguageHelper(getActivity());
		helper.changeLanguage(language);
	}
		
	
	public void startNewFragment()
	{
		if (MySharedPref.getLanguage(getActivity()).equals(
				MyCommon.LANGUAGE_ENG)) {
	
			i = new Intent();
			i.setClass(getActivity(), TabbarActivity.class);
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
					| Intent.FLAG_ACTIVITY_CLEAR_TOP);
			i.putExtra(TabbarActivity.TAB_ID, new Integer(0));
			startActivity(i);
			getActivity().finish();
		} else {
		
			i = new Intent();
			i.setClass(getActivity(), TabbarActivity.class);
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
					| Intent.FLAG_ACTIVITY_CLEAR_TOP);
			i.putExtra(TabbarActivity.TAB_ID, new Integer(3));
			startActivity(i);
			getActivity().finish();
		}
	}

}
