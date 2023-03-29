package com.kuwaitKFF.more;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
import android.widget.ListView;

import com.kuwaitKFF.R;
import com.kuwaitKFF.base.BaseActivity;
import com.kuwaitKFF.common.MyCommon;
import com.kuwaitKFF.common.MyHttpConnection;
import com.kuwaitKFF.common.MySharedPref;
import com.kuwaitKFF.more.bean.PhoneEmailBean;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class PhoneEmailFragment extends BaseActivity {

	List<PhoneEmailBean> phoneEmailList;
	ListView listView;
	
	public PhoneEmailFragment() {
		super(R.layout.activity_phone_email_fragment);
	}
	
	@Override
	public void initialization() {
		ButterKnife.bind(this);
		
	}

	@Override
	public void implementation() {
		listView = (ListView) findViewById(R.id.LV_phoneEmail);
		RequestParams params = null;
		if (MySharedPref.getLanguage(getApplication()).equals(
				MyCommon.LANGUAGE_ENG)) {
			params = new RequestParams(MyCommon.WS_PARA_LANG, ""
					+ MyCommon.VALUE_ENG);

		} else {
			params = new RequestParams(MyCommon.WS_PARA_LANG, ""
					+ MyCommon.VALUE_AR);

		}

		MyHttpConnection.get(MyCommon.WS_METHOD_PHONE_EMAILS, params,
				asyncLoginResponseHandler);
		setHeader(getResources().getString(R.string.phone_mails), true, true);

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
				JSONArray jsonArray = json.getJSONArray("KfsdMobilePhoneEmail");
				Log.d("MzTagInbox", "ssd");
				phoneEmailList = new ArrayList<PhoneEmailBean>();

				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonObject = jsonArray.getJSONObject(i);
					PhoneEmailBean phone = new PhoneEmailBean();
					phone.setLeaderSerial(jsonObject
							.getInt("LeaderSerial"));
					phone.setLeaderDeptName(jsonObject
							.getString("LeaderDeptName"));
					phone.setLeaderName(jsonObject
							.getString("LeaderName"));
					phone.setLeaderContact(jsonObject
							.getString("LeaderContact"));
					phone.setLeaderLangFlag(jsonObject
							.getString("LeaderLangFlag"));

					phoneEmailList.add(phone);
				}

				PhoneEmailAdapter adapter = new PhoneEmailAdapter(getApplicationContext(), R.layout.row_phone_email);
				if(phoneEmailList != null && !phoneEmailList.isEmpty())
				{
					adapter.addAll(phoneEmailList);
				}
				else
				{
					adapter.add(new PhoneEmailBean());
				}
				listView.setAdapter(adapter);

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


	
}
