package com.kuwaitKFF.correspondence;

import java.util.ArrayList;

import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.kuwaitKFF.R;
import com.kuwaitKFF.base.BaseActivity;
import com.kuwaitKFF.main.Kfsd;

import butterknife.ButterKnife;

public class CorrespondenceFinalFragment extends BaseActivity implements OnItemClickListener  {

	Kfsd kfsd;
	View view;
	ArrayList<String> circularList;
	ListView listView;
	
	
	public CorrespondenceFinalFragment() {
		super(R.layout.fragment_correspondence_final);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initialization() {
		ButterKnife.bind(this);

	}

	@Override
	public void implementation() {
		kfsd = (Kfsd) getApplication();
		
		listView = (ListView)findViewById(R.id.listDisplay);

		circularList = new ArrayList<String>();
		circularList.add("News1");
		circularList.add("News3");
		circularList.add("News3");
		circularList.add("News4");
		circularList.add("News5");
		
		listView.setAdapter(new CorrespondenceFinalListAdapter(this, circularList));
		listView.setOnItemClickListener(this);
		
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

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
//		FragmentTransaction ft = getFragmentManager().beginTransaction();
//		CorrespondenceDetailsFragment mnFrg = new CorrespondenceDetailsFragment();
//
//		ft.replace(R.id.groupContent, mnFrg);
//		ft.addToBackStack(null);
//		ft.commit();
		
	}

	

}
