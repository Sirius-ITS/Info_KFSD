package com.kuwaitKFF.decisions;

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

public class DecisionsFragment extends BaseFragment implements
		OnItemClickListener {

	Kfsd kfsd;
	View view;
	ArrayList<DecisionResponseBean> decisionResponseBeansList;
	ListView listView;
	RequestParams params;
	TextView title;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		kfsd = (Kfsd) getActivity().getApplication();

		view = LayoutInflater.from(getActivity()).inflate(
				R.layout.fragment_circular, null);

		listView = (ListView) view.findViewById(R.id.listDisplay);
		
		title = (TextView)view.findViewById(R.id.textTitle);
		title.setText(getResources().getString(R.string.decisions));
		
	

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
		Log.d("MzTTDec", "callWebService()");

		if (MySharedPref.getLanguage(getActivity()).equals(
				MyCommon.LANGUAGE_ENG)) {
			params = new RequestParams(MyCommon.WS_PARA_LANG, ""
					+ MyCommon.VALUE_ENG);

		} else {
			params = new RequestParams(MyCommon.WS_PARA_LANG, ""
					+ MyCommon.VALUE_AR);

		}

		MyHttpConnection.get(MyCommon.WS_METHOD_DECISION, params,
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
			Log.d("MzTTDec", String.valueOf(responseBody));

			try {

				JSONObject json = null;
				try {
					json = new JSONObject(new String(responseBody, "UTF-8"));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				JSONArray jsonArray = json.getJSONArray("KfsdMobileDecision");
				decisionResponseBeansList = new ArrayList<DecisionResponseBean>();
				Log.d("MzTTDec", jsonArray.toString());

				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonObject = jsonArray.getJSONObject(i);
					DecisionResponseBean decisionResponseBean = new DecisionResponseBean();
					decisionResponseBean.setDecisionSerial(jsonObject
							.getString("DecisionSerial"));
					decisionResponseBean.setDecisionSubject(jsonObject
							.getString("DecisionSubject"));
					decisionResponseBean.setDecisionFilePath(jsonObject
							.getString("DecisionFileFullPath"));
					decisionResponseBean.setDecisionFileExtension(jsonObject
							.getString("DecisionFileExtension"));
					decisionResponseBean.setDecisionLangFlag(jsonObject
							.getString("DecisionLangFlag"));
					decisionResponseBean.setDecisionDate(jsonObject
							.getString("DecisionDate"));

					decisionResponseBeansList.add(decisionResponseBean);
				}

				//				//sort Ascending
				Collections.sort(decisionResponseBeansList, new Comparator() {

					public int compare(Object o1, Object o2) {
						DecisionResponseBean p1 = (DecisionResponseBean) o1;
						DecisionResponseBean p2 = (DecisionResponseBean) o2;
						return p1.getDecisionDate().compareToIgnoreCase(p2.getDecisionDate());
					}
				});

				//Reverse to get descending
				Collections.reverse(decisionResponseBeansList);



				listView.setAdapter(new DecisionsListAdapter(getActivity(),
						decisionResponseBeansList));

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
		
		DecisionsListAdapter decisionsListAdapter = (DecisionsListAdapter) listView.getAdapter();

		if(!decisionsListAdapter.getItem(position).getDecisionSerial().equals("0"))
		{
			Intent i = new Intent();
			i.setClass(getActivity(), DecisionDetailsFragment.class);
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
					| Intent.FLAG_ACTIVITY_CLEAR_TOP);
			Bundle b = new Bundle();
			b.putString("pdfUrl", decisionsListAdapter.getItem(position).getDecisionFilePath());
			i.putExtras(b);
			startActivity(i);

		}

		// FragmentTransaction ft = getFragmentManager().beginTransaction();
		// DecisionDetailsFragment mnFrg = new DecisionDetailsFragment();
		//
		// ft.replace(R.id.groupContent, mnFrg);
		// ft.addToBackStack(null);
		// ft.commit();

	}

}
