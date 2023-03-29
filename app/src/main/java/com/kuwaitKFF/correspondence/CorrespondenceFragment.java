package com.kuwaitKFF.correspondence;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.kuwaitKFF.R;
import com.kuwaitKFF.base.BaseActivity;
import com.kuwaitKFF.common.MyCommon;
import com.kuwaitKFF.common.MyHttpConnection;
import com.kuwaitKFF.common.MySharedPref;
import com.kuwaitKFF.main.Kfsd;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class CorrespondenceFragment extends BaseActivity implements OnItemClickListener  {

	public CorrespondenceFragment(int layoutResId) {
		super(R.layout.fragment_correspondence);
	}

	Kfsd kfsd;
	ArrayList<CorrespondanceResponseBean> correspondanceList;
	ListView listView;
	
	public CorrespondenceFragment() {
		super(R.layout.fragment_correspondence);
	}

	

	@Override
	public void initialization() {
		ButterKnife.bind(this);
	}

	@Override
	public void implementation() {
		kfsd = (Kfsd) getApplication();
		
		listView = (ListView) findViewById(R.id.listDisplay);
		listView.setOnItemClickListener(this);
		
		callWebService();
		
		setHeader(getResources().getString(R.string.correspondence), true, true);
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
		System.out.println("RUPA in on item click");
		Intent i = new Intent();
		i.setClass(this, CorrespondenceSnapshotActivity.class);
		System.out.println("RUpa correspondanceList.get(arg2).getEmpEntityId()"+correspondanceList.get(arg2).getEmpEntityId());
		i.putExtra("entityId", correspondanceList.get(arg2).getEmpEntityId());
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
				| Intent.FLAG_ACTIVITY_CLEAR_TOP);

		startActivity(i);

				
	}

	public void callWebService() {
		RequestParams params = new RequestParams();
		params.put(MyCommon.WS_PARA_CIVILID, "" + MyCommon.sLoginResponseBean.getCivilId());
		

		MyHttpConnection.get(MyCommon.WS_METHOD_EMP_ENTITY, params,
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
			try
			{

				JSONObject json = null;
				try {
					json = new JSONObject(new String(responseBody, "UTF-8"));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				JSONArray jsonArray = json.getJSONArray("KfsdMobileEmpEntity");
				Log.d("MzTagCorr", jsonArray.toString());
				correspondanceList = new ArrayList<CorrespondanceResponseBean>();
				for(int i=0; i<jsonArray.length(); i++)
				{
					JSONObject jsonObj = jsonArray.getJSONObject(i);
					CorrespondanceResponseBean bean = new CorrespondanceResponseBean();
					bean.setEmpEntityId(jsonObj.getString("EmpEntityId"));
					bean.setEmpCivilId(jsonObj.getString("EmpCivilId"));
					bean.setEmpEntityNameAr(jsonObj.getString("EmpEntityNameAr"));
					bean.setEmpEntityNameEn(jsonObj.getString("EmpEntityNameEn"));
					bean.setEmpEntityNewCmsCount(jsonObj.getString("EmpEntityNewCmsCount"));
					System.out.println("Rupa inside correspondance");
					correspondanceList.add(bean);
				}
				String lang = MySharedPref.getLanguage(CorrespondenceFragment.this);
				listView.setAdapter(new CorrespondenceListAdapter(getApplicationContext(), R.layout.list_item_correspondence,  correspondanceList, lang));


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


//	@Override
//	public boolean onQueryTextChange(String newText) {
//		if (TextUtils.isEmpty(newText)) {
//			listView.clearTextFilter();
//		} else {
//			listView.setFilterText(newText.toString());
//		}
//		return true;
//	}
//
//	@Override
//	public boolean onQueryTextSubmit(String arg0) {
//		return false;
//	}



}
