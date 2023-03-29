package com.kuwaitKFF.circular;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.kuwaitKFF.base.BaseFragment;
import com.kuwaitKFF.common.MyCommon;
import com.kuwaitKFF.common.MyHttpConnection;
import com.kuwaitKFF.common.MySharedPref;
import com.kuwaitKFF.main.Kfsd;

import com.kuwaitKFF.R;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import cz.msebera.android.httpclient.Header;

public class CircularFragment extends BaseFragment implements OnItemClickListener  {

	Kfsd kfsd;
	View view;
	ArrayList<CircularResponseBean> circularResponseBeansList;
	ListView listView;
	private RequestParams params;
	TextView title;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		kfsd = (Kfsd) getActivity().getApplication();


		view = LayoutInflater.from(getActivity()).inflate(
				R.layout.fragment_circular, null);
		
		listView = (ListView) view.findViewById(R.id.listDisplay);
		
		

		title = (TextView)view.findViewById(R.id.textTitle);
		title.setText(getResources().getString(R.string.circular));
		
		if (kfsd.checkNetworkRechability())
			callWebService();
		else
			Show_No_Internet();


		listView.setOnItemClickListener(this);
		
		//setHeader(view, getResources().getString(R.string.login), true, true);
		if (MySharedPref.getLanguage(getActivity()).equals(
				MyCommon.LANGUAGE_ENG)) {
			setHeader(view,getResources().getString(R.string.welcome)+" "+MyCommon.sLoginResponseBean.getNameEn(), true,true,true);
		}else
		{
			setHeader(view,getResources().getString(R.string.welcome)+" "+MyCommon.sLoginResponseBean.getNameAr(), true,true,true);
		}
	

		return view;
	}
	
	
	public void callWebService() {

		if (MySharedPref.getLanguage(getActivity()).equals(
				MyCommon.LANGUAGE_ENG)) {
			params = new RequestParams(MyCommon.WS_PARA_LANG, ""
					+ MyCommon.VALUE_ENG);

		} else {
			params = new RequestParams(MyCommon.WS_PARA_LANG, ""
					+ MyCommon.VALUE_AR);

		}

		MyHttpConnection.get(MyCommon.WS_METHOD_CIRCULAR, params,
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
			Log.d("MzTag", String.valueOf(responseBody));
			// response = MyCommon.getJsonFromWebServiceResponse(response);
			try {

				JSONObject json = null;
				try {
					json = new JSONObject(new String(responseBody, "UTF-8"));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				JSONArray jsonArray = json.getJSONArray("KfsdMobileCircular");
				circularResponseBeansList = new ArrayList<CircularResponseBean>();


				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonObject = jsonArray.getJSONObject(i);
					CircularResponseBean circularResponseBean = new CircularResponseBean();
					circularResponseBean.setCircularSerial(jsonObject
							.getString("CircularSerial"));
					circularResponseBean.setCircularSubject(jsonObject
							.getString("CircularSubject"));
					circularResponseBean.setCircularFilePath(jsonObject
							.getString("CircularFileFullPath"));
					circularResponseBean.setCircularFileExtension(jsonObject
							.getString("CircularFileExtension"));
					circularResponseBean.setCircularLangFlag(jsonObject
							.getString("CircularLangFlag"));
					circularResponseBean.setCircularDate(jsonObject
							.getString("CircularDate"));

					circularResponseBeansList.add(circularResponseBean);
				}

				//				//sort Ascending
				Collections.sort(circularResponseBeansList, new Comparator() {

					public int compare(Object o1, Object o2) {
						CircularResponseBean p1 = (CircularResponseBean) o1;
						CircularResponseBean p2 = (CircularResponseBean) o2;
						return p1.getCircularDate().compareToIgnoreCase(p2.getCircularDate());
					}
				});

				//Reverse to get descending
				Collections.reverse(circularResponseBeansList);


				listView.setAdapter(new CircularListAdapter(getActivity(),
						circularResponseBeansList));

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

				getFragmentManager().popBackStack();

			} catch (Exception e) {
				e.printStackTrace();
			}

			return true;

		}

		return false;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

		CircularListAdapter circularListAdapter = (CircularListAdapter) listView.getAdapter();


		if(!circularListAdapter.getItem(position).getCircularSerial().equals("0"))
		{
			Intent  i = new Intent();
			i.setClass(getActivity(), CircularDetailsFragment.class);
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
					| Intent.FLAG_ACTIVITY_CLEAR_TOP);
			Bundle b = new Bundle();
			b.putString("pdfUrl", circularListAdapter.getItem(position).getCircularFilePath());

			i.putExtras(b);
			startActivity(i);

		}

//		FragmentTransaction ft = getFragmentManager().beginTransaction();
//		CircularDetailsFragment mnFrg = new CircularDetailsFragment();
//
//		ft.replace(R.id.groupContent, mnFrg);
//		ft.addToBackStack(null);
//		ft.commit();
		
	}

	

}
