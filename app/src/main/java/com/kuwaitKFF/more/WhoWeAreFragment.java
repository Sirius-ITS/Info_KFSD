package com.kuwaitKFF.more;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.view.KeyEvent;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.kuwaitKFF.R;
import com.kuwaitKFF.base.BaseActivity;
import com.kuwaitKFF.common.MyCommon;
import com.kuwaitKFF.common.MyHttpConnection;
import com.kuwaitKFF.common.MySharedPref;
import com.kuwaitKFF.main.Kfsd;
import com.loopj.android.http.AsyncHttpResponseHandler;

import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class WhoWeAreFragment extends BaseActivity  {

	Kfsd kfsd;
	View view;
	ArrayList<String> listDataHeader;

	
	MoreExpandableListAdapter listAdapter;
	ExpandableListView expListView;

	HashMap<String, ArrayList<String>> listDataChild;
	TextView textDisplay;
	
	public WhoWeAreFragment() {
		super(R.layout.fragment_who_we_are);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initialization() {
		ButterKnife.bind(this);

	}

	@Override
	public void implementation() {
		kfsd = (Kfsd) getApplication();
		
		
		if (kfsd.checkNetworkRechability())
		{
			callWebService();
		    
		}
		else
			Show_No_Internet();

        textDisplay = (TextView)findViewById(R.id.displayText);
        
		setHeader(getResources().getString(R.string.who_we_are), true, true);

	}
	
	
	public void callWebService() {


		MyHttpConnection.get(MyCommon.WS_METHOD_WHO_WE_ARE,
				asyncLoginResponseHandler);

	}

	AsyncHttpResponseHandler asyncLoginResponseHandler = new AsyncHttpResponseHandler() {


		@Override
		public void onFinish() {
			DismissProgress();
			super.onFinish();
		}

		@Override
		public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
			// response = MyCommon.getJsonFromWebServiceResponse(response);
			try {

				JSONObject json = null;
				try {
					json = new JSONObject(new String(responseBody, "UTF-8"));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				JSONArray jsonArray = json.getJSONArray("KfsdMobileHistory");
				JSONObject jsonObj = jsonArray.getJSONObject(0);

				String historyAr = jsonObj.getString("HistoryAr");
				String historyEn = jsonObj.getString("HistoryEn");

				if(MySharedPref.getLanguage(WhoWeAreFragment.this).equalsIgnoreCase(MyCommon.LANGUAGE_AR))
				{
					textDisplay.setText(historyAr);
				}else
				{
					textDisplay.setText(historyEn);
				}



			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
			Show_Alert_Dialog(R.string.M_Unable_To_Fetch_Data);
		}

		@Override
		public void onStart() {
			DisplayProgress();
			super.onStart();
		}

	};




	
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
