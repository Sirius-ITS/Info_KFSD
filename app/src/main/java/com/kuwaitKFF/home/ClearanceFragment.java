package com.kuwaitKFF.home;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;


import com.kuwaitKFF.base.BaseActivity;
import com.kuwaitKFF.common.MyCommon;
import com.kuwaitKFF.common.MyHttpConnection;
import com.kuwaitKFF.main.Kfsd;

import com.kuwaitKFF.R;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.graphics.Color;

import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;

import android.widget.ArrayAdapter;
import android.widget.EditText;

import android.widget.Spinner;
import android.widget.TextView;

import android.widget.Button;
import android.widget.TableLayout;

import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class ClearanceFragment extends BaseActivity implements OnClickListener {

	Kfsd kfsd;
	View view;
	ArrayList<String> listDataHeader;
	TextView captcha,tblDate,tblCivilId,tblFileNo,notification,infraction;
	Button submitButton;
	String sQueryText;
	EditText  captchaCheck;

	Spinner querySpinner;
	EditText enteredTextView;
	TextView queryLabel;

	HashMap<String, ArrayList<String>> listDataChild;
	TableLayout mainTable;
	
	public ClearanceFragment() {
		super(R.layout.fragment_clearance);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initialization() {
		ButterKnife.bind(this);

	}

	@Override
	public void implementation() {
		kfsd = (Kfsd) getApplication();
		
//		civilId = (TextView) findViewById(R.id.civilIdText);
//		fileNo = (TextView) findViewById(R.id.fileNoText);
		tblDate = (TextView) findViewById(R.id.dateText);
		tblCivilId = (TextView) findViewById(R.id.tblCivilIdText);
		tblFileNo = (TextView) findViewById(R.id.tblfileNoText);
		notification = (TextView) findViewById(R.id.notificationText);
		infraction = (TextView) findViewById(R.id.infractionText);
		mainTable = (TableLayout) findViewById(R.id.mailTable);
		captcha = (TextView) findViewById(R.id.captcha);
		System.out.println("Captcha " +captcha );
		captchaCheck = (EditText) findViewById(R.id.captchaCheck);
		int randomPIN = (int)(Math.random()*9000)+1000;
		captcha.setText(String.valueOf(randomPIN));
		
		submitButton = (Button)findViewById(R.id.submitButton);
		submitButton.setOnClickListener(this);


		querySpinner = (Spinner)findViewById(R.id.querySpinner);
		enteredTextView = (EditText) findViewById(R.id.queryText);
		queryLabel = (TextView) findViewById(R.id.queryLabel);

		String[]  arraySpinner = new String[] {
				getString(R.string.civil_id), getString(R.string.file_no)
		};
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, arraySpinner);

		querySpinner.setAdapter(adapter);

		querySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

				((TextView)parent.getChildAt(0)).setTextColor(Color.rgb(52, 90, 152));
				((TextView)parent.getChildAt(0)).setTextSize(17f);
				((TextView)parent.getChildAt(0)).setGravity(Gravity.CENTER_HORIZONTAL);

				enteredTextView.setText("");
				captchaCheck.setText("");
				mainTable.setVisibility(View.INVISIBLE);

				if(position == 0)
					queryLabel.setText(R.string.civil_id);
				else if(position == 1)
					queryLabel.setText(R.string.file_no);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}


		});

		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date = new Date();
		tblDate.setText(dateFormat.format(date));
		System.out.println(dateFormat.format(date)); 
		
		
		setHeader(getResources().getString(R.string.clearance), true, true);

	}
	
	
	public void callWebService() {

		RequestParams params;

		if(querySpinner.getSelectedItemPosition() == 0)
			 params = new RequestParams(MyCommon.WS_PARA_EMPID, "" +sQueryText);
		else
			 params = new RequestParams(MyCommon.WS_PARA_FILENO, "" +sQueryText);



//		params.put(MyCommon.WS_PARA_FILENO, "" + sFileNo);
		

		MyHttpConnection.get(MyCommon.WS_METHOD_CLEARANCE, params,
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
			Log.d("MzTag444", String.valueOf(responseBody));
			// response = MyCommon.getJsonFromWebServiceResponse(response);

			try {

				JSONObject json = null;
				try {
					json = new JSONObject(new String(responseBody, "UTF-8"));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				JSONObject jsonObj = json.getJSONObject("KfsdMobileClearance");
				String notificationVal = jsonObj.getString("notification");
				String infractionVal = jsonObj.getString("violation");

				if(notificationVal.equals("-1") && infractionVal.equals("-1"))
					Show_Alert_Dialog(R.string.incorrect_civil_id);
				else if(notificationVal.equals("-2") && infractionVal.equals("-2"))
					Show_Alert_Dialog(R.string.unavailable_civil_id);
				else if(notificationVal.equals("-3") && infractionVal.equals("-3"))
					Show_Alert_Dialog(R.string.unavailable_file_no);

				else
				{
					tblCivilId.setText("");
					tblFileNo.setText("");

					if(querySpinner.getSelectedItemPosition() == 0)
						tblCivilId.setText(sQueryText);
					else
						tblFileNo.setText(sQueryText);

					notification.setText(notificationVal);
					infraction.setText(infractionVal);
					mainTable.setVisibility(View.VISIBLE);
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

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.submitButton:
			hideSoftKeyboard();
			sQueryText = enteredTextView.getText().toString();
//			sFileNo = fileNo.getText().toString();

			if(captchaCheck.getText().toString().equals("")) {
				Show_Alert_Dialog(R.string.M_captcha_empty);
				break;
			}

			if(captchaCheck.getText() != null && !captchaCheck.getText().toString().equalsIgnoreCase(captcha.getText().toString()))
			{	
				Show_Alert_Dialog(R.string.M_Please_Enter_correct);
				break;
			}
			
			if ((sQueryText.equalsIgnoreCase(MyCommon.DEFAULT_STRING)) )
			{
				if(querySpinner.getSelectedItemPosition() == 0)
					Show_Alert_Dialog(R.string.M_Enter_Civil_Id);
				else
					Show_Alert_Dialog(R.string.M_Enter_File_No);
			}
//			else if (sFileNo
//					.equalsIgnoreCase(MyCommon.DEFAULT_STRING)) 
//			{
//
//				Show_Alert_Dialog(R.string.M_Enter_File_No);
//
//			
//			}
			else if (kfsd.checkNetworkRechability())
			{
				if(querySpinner.getSelectedItemPosition() == 0) {

					if(sQueryText.trim().length()>0)
					{
						if(sQueryText.trim().length()<12)
						{
							Show_Alert_Dialog(R.string.M_Enter_12_Civil_Id);
						}else
						{
							callWebService();
						}
					}

				}

				else
				{
					callWebService();
				}
				
			}
			else
			{
				Show_No_Internet();
			}
			
			
			break;

		default:
			break;
		}
		
	}

	public void hideSoftKeyboard() {
	    if(getCurrentFocus()!=null) {
	        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
	        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
	    }
	}

}
