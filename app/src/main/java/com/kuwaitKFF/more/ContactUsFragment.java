package com.kuwaitKFF.more;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.Spinner;
import android.widget.TextView;


import com.google.gson.Gson;
import com.kuwaitKFF.R;
import com.kuwaitKFF.base.BaseActivity;
import com.kuwaitKFF.common.MyCommon;
import com.kuwaitKFF.common.MyHttpConnection;
import com.kuwaitKFF.main.Kfsd;
import com.kuwaitKFF.more.bean.ContactMsgType;
import com.kuwaitKFF.more.bean.ContactUsRequest;
import com.loopj.android.http.AsyncHttpResponseHandler;

import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

public class ContactUsFragment extends BaseActivity  {

	Kfsd kfsd;
	View view;
	ArrayList<String> listDataHeader;

	
	MoreExpandableListAdapter listAdapter;
	ExpandableListView expListView;

	HashMap<String, ArrayList<String>> listDataChild;
	Spinner msgTypeText;
	
	public ContactUsFragment() {
		super(R.layout.fragment_contact_us);
	}

	@Override
	public void initialization() {
		ButterKnife.bind(this);

	}

	@Override
	public void implementation() {
		kfsd = (Kfsd) getApplication();

		final EditText nameText = (EditText) findViewById(R.id.name);
		final EditText phoneText = (EditText) findViewById(R.id.phone);
		final EditText emailText = (EditText) findViewById(R.id.email);
		final EditText subjectText = (EditText) findViewById(R.id.subject);
		final EditText bodyText = (EditText) findViewById(R.id.body);
		msgTypeText = (Spinner) findViewById(R.id.msgType);

		msgTypeText.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
				((TextView)adapterView.getChildAt(0)).setTextColor(Color.rgb(52, 90, 152));
				((TextView)adapterView.getChildAt(0)).setTextSize(17f);
				((TextView)adapterView.getChildAt(0)).setGravity(Gravity.CENTER_HORIZONTAL);
			}

			@Override
			public void onNothingSelected(AdapterView<?> adapterView) {

			}
		});

		MyHttpConnection.get(MyCommon.WS_METHOD_CONTACT_TYPE, asyncMsgType);
		
		setHeader(getResources().getString(R.string.suggestion), true, true);
		Button button = (Button) findViewById(R.id.submitButton);
		button .setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(nameText.getText() == null || nameText.getText().toString().isEmpty())
				{
					Show_Alert_Dialog(R.string.M_contact_name);
					return;
				}
				if(emailText == null || emailText.getText().toString().isEmpty())
				{
					Show_Alert_Dialog(R.string.M_contact_email);
					return;
				}

				if(subjectText == null || subjectText.getText().toString().isEmpty())
				{
					Show_Alert_Dialog(R.string.M_contact_sub);
					return;
				}
				if(phoneText == null || phoneText.getText().toString().isEmpty())
				{
					Show_Alert_Dialog(R.string.M_contact_phone);
					return;
				}
				if(bodyText == null || bodyText.getText().toString().isEmpty())
				{
					Show_Alert_Dialog(R.string.M_contact_comment);
					return;
				}
				String lang = null;
				Bundle extras = getIntent().getExtras();
				if (extras != null) {
				   lang = extras.getString("lang");
				}

				ContactMsgType type = (ContactMsgType)msgTypeText.getSelectedItem();

				ContactUsRequest req;

				if(lang!= null && lang.equalsIgnoreCase(MyCommon.LANGUAGE_ENG))
				{
					req = new ContactUsRequest(MyCommon.VALUE_ENG, type.getContactUsTypeCode().toString(),
							nameText.getText().toString(), emailText.getText().toString(), phoneText.getText().toString(),
							subjectText.getText().toString(), bodyText.getText().toString());
				}

				else
				{
					 req = new ContactUsRequest(MyCommon.VALUE_AR, type.getContactUsTypeCode().toString(),
							nameText.getText().toString(), emailText.getText().toString(), phoneText.getText().toString(),
							subjectText.getText().toString(), bodyText.getText().toString());
				}



//				Map<String, String> map = new HashMap<String, String>();
//				map.put("contactUsMail", "" +emailText.getText().toString());
//				map.put("contactUsName", "" +nameText.getText().toString());
//				map.put("contactUsSub", "" +subjectText.getText().toString());
//				map.put("contactUsCell", "" +phoneText.getText().toString());
//				map.put("contactUsCont", "" +bodyText.getText().toString());

//				ContactMsgType type = (ContactMsgType)msgTypeText.getSelectedItem();
//				map.put("contactUsType", "" +type.getContactUsTypeCode());
				
				//RequestParams params = new RequestParams(map);



				Gson gson = new Gson();
				String convert = gson.toJson(req);

				Log.d("MzTagContactUs",convert);

				//JSONObject obj = new JSONObject(convert);

				StringEntity entity = new StringEntity(convert,"UTF-8");

//						String s = EntityUtils.toString(entity);
//						Log.d("MzTagContactUs", s);
//					MyHttpConnection.post(MyCommon.WS_METHOD_ADD_CONTACT_TYPE, params, asyncContactResponseHandler);

				MyHttpConnection.postWithJsonEntity(kfsd, MyCommon.WS_METHOD_ADD_CONTACT_TYPE, entity, asyncContactResponseHandler);
			}
		});
		
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

	AsyncHttpResponseHandler asyncContactResponseHandler = new AsyncHttpResponseHandler() {

		@Override
		public void onFinish() {
			Log.d("MzTag","onFinish(222)");
			DismissProgress();
			super.onFinish();
		}

		@Override
		public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
			Log.d("MzTag","onSuccess(222)");


			// response = MyCommon.getJsonFromWebServiceResponse(response);
			try {
				JSONObject json = null;
				try {
					json = new JSONObject(new String(responseBody, "UTF-8"));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				JSONObject obj = (JSONObject)json.get("KfsdMobileAddContact");
				Log.d("MzTag",json.toString());
				Integer flag = (Integer)obj.get("responseFlag");
				String msg = (String)obj.get("responseMsg");
				System.out.println("responseMsg "+msg);
				if(flag == 1)
				{

					Show_Alert_Dialog(msg, new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							finish();
						}
					});
				}
				else if(flag == 0)
				{
					Show_Alert_Dialog(msg, new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
//							finish();
						}
					});;
				}

			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
			Log.d("MzTag","onFailure(222)");
			Show_Alert_Dialog(R.string.M_Unable_To_Fetch_Data);
		}

		@Override
		public void onStart() {
			Log.d("MzTag","onStart(222)");

			DisplayProgress();
			super.onStart();
		}

	};
	
	AsyncHttpResponseHandler asyncMsgType = new AsyncHttpResponseHandler() {

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
				JSONArray array = json.getJSONArray("KfsdMobileContactUs");
				List<ContactMsgType> msgTypeArray = new ArrayList<ContactMsgType>();
				if(array != null && array.length()>0)
				{
					for(int i =0; i<array.length(); i++)
					{
						JSONObject obj = array.getJSONObject(i);
						ContactMsgType msgType = new ContactMsgType();
						msgType.setContactUsTypeCode(obj.getString("ContactUsTypeCode"));
						msgType.setContactUsTypeDescAr(obj.getString("ContactUsTypeDescAr"));
						msgType.setContactUsTypeDescEn(obj.getString("ContactUsTypeDescEn"));
						msgTypeArray.add(msgType);
					}
				}

				String lang = null;
				Bundle extras = getIntent().getExtras();
				if (extras != null) {
					lang = extras.getString("lang");
				}
				ContactMsgType[] msges = new ContactMsgType[msgTypeArray.size()];
				ContactSpinnerAdapter dataAdapter = new ContactSpinnerAdapter(getApplicationContext(),
						android.R.layout.simple_spinner_item, msgTypeArray.toArray(msges), lang);
				msgTypeText.setAdapter(dataAdapter);
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
