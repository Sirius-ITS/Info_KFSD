package com.kuwaitKFF.newscreens;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

import com.kuwaitKFF.R;
import com.kuwaitKFF.base.BaseActivity;
import com.kuwaitKFF.common.MyCommon;
import com.kuwaitKFF.common.MyHttpConnection;
import com.kuwaitKFF.common.MySharedPref;
import com.kuwaitKFF.main.Kfsd;
import com.kuwaitKFF.more.bean.LeaveReqType;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class FragmentLeaveRequest extends BaseActivity implements OnClickListener {

	Kfsd kfsd;
	Button submitButton;

	Spinner leaveTypeSpinner;

	TextView leaveStartDate;
	TextView leaveEndDate;
	TextView phoneEditText;
	TextView emailEditText;
	CheckBox withSalaryChkBox;
	private CaldroidFragment dialogCaldroidFragment;
	SimpleDateFormat formatter;
	SimpleDateFormat formatterForDisplay;
	String dateForSendingToServer = "";

	TextView title;
	String lang;
	public FragmentLeaveRequest() {
		super(R.layout.fragment_leave_request);

	}

	@Override
	public void initialization() {
		ButterKnife.bind(this);

	}

	@Override
	public void implementation() {
		kfsd = (Kfsd)getApplication();
		

		title = (TextView)findViewById(R.id.titleText);
		title.setText(getResources().getString(R.string.leave_request));


		formatter = new SimpleDateFormat("MM/dd/yyyy");

		if (MySharedPref.getLanguage(FragmentLeaveRequest.this).equals(
				MyCommon.LANGUAGE_ENG)) {
		formatterForDisplay = new SimpleDateFormat("yyyy-MM-dd");
		}
		else{
			formatterForDisplay = new SimpleDateFormat("yyyy-MM-dd");
		}

		leaveTypeSpinner = (Spinner)findViewById(R.id.leaveTypeSpinner);

		submitButton = (Button)findViewById(R.id.submitButton);
		submitButton.setOnClickListener(this);

		leaveStartDate = (TextView) findViewById(R.id.startDateText);
		leaveStartDate.setOnClickListener(this);
		leaveEndDate = (TextView) findViewById(R.id.endDateText);
		leaveEndDate.setOnClickListener(this);

		phoneEditText = (TextView) findViewById(R.id.phoneText);
		emailEditText = (TextView) findViewById(R.id.emailText);
		withSalaryChkBox = (CheckBox) findViewById(R.id.salaryCheckBox);

		MyHttpConnection.get(MyCommon.WS_METHOD_LEAVE_REQUEST_TYPE, leaveTypeResponseHandler); // herre WS_METHOD_LEAVE_TYPE

//		animFlipInForeward = AnimationUtils.loadAnimation(this,
//				R.anim.flipin);
//		animFlipOutForeward = AnimationUtils.loadAnimation(this,
//				R.anim.flipout);
//		animFlipInBackward = AnimationUtils.loadAnimation(this,
//				R.anim.flipin_reverse);
//		animFlipOutBackward = AnimationUtils.loadAnimation(this,
//				R.anim.flipout_reverse);

		if (MySharedPref.getLanguage(FragmentLeaveRequest.this).equals(
				MyCommon.LANGUAGE_ENG)) {
			setHeader(getResources().getString(R.string.welcome)+" "+MyCommon.sLoginResponseBean.getNameEn(), true,true,true);
		}else
		{
			setHeader(getResources().getString(R.string.welcome)+" "+MyCommon.sLoginResponseBean.getNameAr(), true,true,true);
		}

		lang = MySharedPref.getLanguage(FragmentLeaveRequest.this);



	}


	public void clickedEndDateDialog(View view) {

		dialogCaldroidFragment = new CaldroidFragment();
		dialogCaldroidFragment.setCaldroidListener(endDateSelectListener);

		// If activity is recovered from rotation
		final String dialogTag = "CALDROID_DIALOG_FRAGMENT";
		// Setup arguments
		Bundle bundle = new Bundle();
		// Setup dialogTitle
		bundle.putString(CaldroidFragment.DIALOG_TITLE, "Select a date");

		Calendar calendar = Calendar.getInstance();
		// dialogCaldroidFragment.setMinDate(calendar.getTime());
		dialogCaldroidFragment.setArguments(bundle);

		dialogCaldroidFragment.show(getSupportFragmentManager(), dialogTag);
	}

	public void clickedStartDateDialog(View view) {

		dialogCaldroidFragment = new CaldroidFragment();
		dialogCaldroidFragment.setCaldroidListener(startDateSelectListener);

		// If activity is recovered from rotation
		final String dialogTag = "CALDROID_DIALOG_FRAGMENT";
		// Setup arguments
		Bundle bundle = new Bundle();
		// Setup dialogTitle
		bundle.putString(CaldroidFragment.DIALOG_TITLE, "Select a date");

		Calendar calendar = Calendar.getInstance();
		// dialogCaldroidFragment.setMinDate(calendar.getTime());
		dialogCaldroidFragment.setArguments(bundle);

		dialogCaldroidFragment.show(getSupportFragmentManager(), dialogTag);
	}

	// Setup listener
	final CaldroidListener endDateSelectListener = new CaldroidListener() {

		@Override
		public void onSelectDate(Date date, View view) {
			// Toast.makeText(getApplicationContext(), formatter.format(date),
			// Toast.LENGTH_SHORT).show();
			dialogCaldroidFragment.dismiss();
			String dateString = fetchEnglishFromArabicNumber(formatterForDisplay
					.format(date));
			dateForSendingToServer = formatter.format(date);
			leaveEndDate.setText(dateString);

		}

		@Override
		public void onChangeMonth(int month, int year) {
		}

	};
	final CaldroidListener startDateSelectListener = new CaldroidListener() {

		@Override
		public void onSelectDate(Date date, View view) {
			// Toast.makeText(getApplicationContext(), formatter.format(date),
			// Toast.LENGTH_SHORT).show();
			dialogCaldroidFragment.dismiss();
			String dateString = fetchEnglishFromArabicNumber(formatterForDisplay
					.format(date));
			dateForSendingToServer = formatter.format(date);
			leaveStartDate.setText(dateString);

		}

		@Override
		public void onChangeMonth(int month, int year) {
		}

	};


	public String fetchEnglishFromArabicNumber(String englishNumber) {
		englishNumber = englishNumber.replace("٠", "0");
		englishNumber = englishNumber.replace("١", "1");
		englishNumber = englishNumber.replace("٢", "2");
		englishNumber = englishNumber.replace("٣", "3");
		englishNumber = englishNumber.replace("٤", "4");
		englishNumber = englishNumber.replace("٥", "5");
		englishNumber = englishNumber.replace("٦", "6");
		englishNumber = englishNumber.replace("٧", "7");
		englishNumber = englishNumber.replace("٨", "8");
		englishNumber = englishNumber.replace("٩", "9");
		return englishNumber;
	}



	/**
	 * Overrides the default implementation for KeyEvent.KEYCODE_BACK so that
	 * all systems call onBackPressed().
	 */

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {

			return true;
		}
		return false;

	}

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
	
	public void clickedSubmit() {

		 if (phoneEditText.getText().toString().equalsIgnoreCase(MyCommon.DEFAULT_STRING) || leaveTypeSpinner.getSelectedItemPosition() == -1) {
			Show_Alert_Dialog(R.string.M_Enter_Details);
		}
		 else if (leaveStartDate.getText().toString().equalsIgnoreCase(MyCommon.DEFAULT_STRING)) {
			Show_Alert_Dialog(R.string.M_Enter_Start_Date);
		}
		else if (leaveEndDate.getText().toString().equalsIgnoreCase(MyCommon.DEFAULT_STRING)) {
			Show_Alert_Dialog(R.string.M_Enter_End_Date);
		}
	 else if(leaveEndDate.getText().toString().compareTo(leaveStartDate.getText().toString()) <0) {
		Show_Alert_Dialog(R.string.M_End_Date_Less);
		}
	else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(emailEditText.getText().toString()).matches() && !emailEditText.getText().toString().equalsIgnoreCase(MyCommon.DEFAULT_STRING)) { // email not valid
			 Show_Alert_Dialog(R.string.M_Enter_Valid_email);
		 }
	 else {
			if (kfsd.checkNetworkRechability())
				callWebService();
			else
				Show_No_Internet();

		}

	}


	public void callWebService() {

		String civilID = MyCommon.sLoginResponseBean.getCivilId();
		if(civilID.equals("")) {
			Show_Alert_Dialog(R.string.civil_id_not_available);
			return;
		}

//		LeaveReqType leaveReqType = (LeaveReqType)leaveTypeSpinner.getSelectedItem();

//		LeaveRequest leaveRequest = new LeaveRequest(lang, leaveReqType.getleaveTypeCode().toString(), dateFormatForDate1(leaveStartDate.getText().toString()), dateFormatForDate1(leaveEndDate.getText().toString()), civilID);

//		Gson gson = new Gson();
//		String convert = gson.toJson(leaveRequest);

//		Log.d("MzTagLEAVEReq", convert);

//		try {
//			StringEntity entity = new StringEntity(convert,"UTF-8");
//			MyHttpConnection.postWithJsonEntity(kfsd, MyCommon.WS_METHOD_LEAVE_INSERT, entity, asyncLeaveResponseHandler);
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		}

		LeaveReqType leaveReqType = (LeaveReqType)leaveTypeSpinner.getSelectedItem();

		RequestParams params = new RequestParams();
		params.put("startDate",dateFormatForDate1(leaveStartDate.getText().toString()));
		params.put("endDate",dateFormatForDate1(leaveEndDate.getText().toString()));
		params.put("mobile",phoneEditText.getText().toString());

		if (!emailEditText.getText().toString().equalsIgnoreCase(MyCommon.DEFAULT_STRING)) // email is optional
			params.put("email",emailEditText.getText().toString());

		if(withSalaryChkBox.isChecked())
			params.put("payment","1");
		else
			params.put("payment","0");

		if (MySharedPref.getLanguage(FragmentLeaveRequest.this).equals(MyCommon.LANGUAGE_ENG))
			params.put("lang", MyCommon.VALUE_ENG);
		else
			params.put("lang", MyCommon.VALUE_AR);

		params.put("leaveTypeCd",leaveReqType.getleaveTypeCode().toString());
		params.put("civilId",civilID);
        params.put("reqType", "1"); // future compatability


		MyHttpConnection.get(MyCommon.WS_METHOD_LEAVE_INSERT, params, asyncLeaveResponseHandler);

	}


	AsyncHttpResponseHandler asyncLeaveResponseHandler = new AsyncHttpResponseHandler() {

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
				Log.d("MzTag",json.toString());
				JSONObject obj = (JSONObject)json.get("KfsdMobileLeaves");
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
				else
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



	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.submitButton:
			clickedSubmit();
			break;

		case R.id.endDateText:
			clickedEndDateDialog(v);
			break;

		case R.id.startDateText:
			clickedStartDateDialog(v);
			break;

		default:
			break;
		}

	}


//	AsyncHttpResponseHandler leaveTypeResponseHandler = new AsyncHttpResponseHandler() {
//
//		@Override
//		public void onFailure(Throwable arg0, String arg1) {
//			Show_Alert_Dialog(R.string.M_Unable_To_Fetch_Data);
//			super.onFailure(arg0, arg1);
//		}
//
//		@Override
//		public void onFinish() {
//			DismissProgress();
//			super.onFinish();
//		}
//
//		@Override
//		public void onStart() {
//			DisplayProgress();
//			super.onStart();
//		}
//
//		@Override
//		public void onSuccess(String response) {
//			// response = MyCommon.getJsonFromWebServiceResponse(response);
//			try {
//				JSONObject json = new JSONObject(response);
//				Log.d("MzTagLEAVEReq",json.toString());
//				JSONArray array = json.getJSONArray("KfsdMobileLeaveType");
//				List<LeaveReqType> msgTypeArray = new ArrayList<>();
//
//				if(array != null && array.length()>0)
//				{
//					for(int i =0; i<array.length(); i++)
//					{
//						JSONObject obj = array.getJSONObject(i);
//						LeaveReqType msgType = new LeaveReqType();
//						msgType.setleaveTypeCode(obj.getString("LeaveReqTypeCode"));
//						msgType.setLeaveTypeDescAr(obj.getString("LeaveReqTypeDescAr"));
//						msgType.setLeaveTypeDescEn(obj.getString("LeaveReqTypeDescEn"));
//						msgTypeArray.add(msgType);
//					}
//				}
//
//
//				LeaveReqType[] msges = new LeaveReqType[msgTypeArray.size()];
//				LeaveRequestAdapter adapter = new LeaveRequestAdapter(getApplicationContext(),
//						android.R.layout.simple_spinner_item, msgTypeArray.toArray(msges), MySharedPref.getLanguage(FragmentLeaveRequest.this));
//
//				leaveTypeSpinner.setAdapter(adapter);
//
//			} catch (JSONException e) {
//				e.printStackTrace();
//			}
//			super.onSuccess(response);
//		}
//
//	};

	AsyncHttpResponseHandler leaveTypeResponseHandler = new AsyncHttpResponseHandler() {

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
				Log.d("MzTagLEAVEReq",json.toString());
				JSONArray array = json.getJSONArray("KfsdMobileLeaveReqType");
				List<LeaveReqType> msgTypeArray = new ArrayList<>();
				if(array != null && array.length()>0)
				{
					for(int i =0; i<array.length(); i++)
					{
						JSONObject obj = array.getJSONObject(i);
						LeaveReqType msgType = new LeaveReqType();
						msgType.setleaveTypeCode(obj.getString("LeaveReqTypeCode"));
						msgType.setLeaveTypeDescAr(obj.getString("LeaveReqTypeDescAr"));
						msgType.setLeaveTypeDescEn(obj.getString("LeaveReqTypeDescEn"));
						msgTypeArray.add(msgType);
					}
				}

				LeaveReqType[] msges = new LeaveReqType[msgTypeArray.size()];
				LeaveRequestAdapter adapter = new LeaveRequestAdapter(getApplicationContext(),
						android.R.layout.simple_spinner_item, msgTypeArray.toArray(msges), lang);

				leaveTypeSpinner.setAdapter(adapter);

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


	public String dateFormatForDate1(String dateVal) {

		String formattedDate = dateVal;
		SimpleDateFormat formatter = new SimpleDateFormat(
				"yy-MM-dd");
		SimpleDateFormat formatter1 = new SimpleDateFormat("dd-MM-yy");

		try {
			Date date = formatter.parse(dateVal);
			 formattedDate = formatter1.format(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return formattedDate;

	}

}
