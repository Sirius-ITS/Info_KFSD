package com.kuwaitKFF.newscreens;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.kuwaitKFF.R;
import com.kuwaitKFF.base.BaseActivity;
import com.kuwaitKFF.common.MyCommon;
import com.kuwaitKFF.common.MyHttpConnection;
import com.kuwaitKFF.common.MySharedPref;
import com.kuwaitKFF.main.Kfsd;

import com.kuwaitKFF.more.bean.PermReqType;
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


public class FragmentPermissionRequest extends BaseActivity implements OnClickListener {

	Kfsd kfsd;
	Button submitButton;

	Spinner permTypeSpinner;

	TextView permDate;
	private CaldroidFragment dialogCaldroidFragment;
	SimpleDateFormat formatter;
	SimpleDateFormat formatterForDisplay;
	String dateForSendingToServer = "";

	TextView title;
	String lang;

	public FragmentPermissionRequest() {
		super(R.layout.fragment_permission_request);

	}

	@Override
	public void initialization() {
		ButterKnife.bind(this);

	}

	@Override
	public void implementation() {
		kfsd = (Kfsd)getApplication();
		

		title = (TextView)findViewById(R.id.titleText);
		title.setText(getResources().getString(R.string.permission_request));


		formatter = new SimpleDateFormat("MM/dd/yyyy");

		if (MySharedPref.getLanguage(FragmentPermissionRequest.this).equals(
				MyCommon.LANGUAGE_ENG)) {
		formatterForDisplay = new SimpleDateFormat("dd-MM-yyyy");
		}
		else{
			formatterForDisplay = new SimpleDateFormat("yyyy-MM-dd");
		}

		permTypeSpinner = (Spinner)findViewById(R.id.permTypeSpinner);

		submitButton = (Button)findViewById(R.id.submitButton);
		submitButton.setOnClickListener(this);

		permDate = (TextView) findViewById(R.id.endDateText);
		permDate.setOnClickListener(this);

		MyHttpConnection.get(MyCommon.WS_METHOD_PERMISSION_TYPE, permissionType);

//		animFlipInForeward = AnimationUtils.loadAnimation(this,
//				R.anim.flipin);
//		animFlipOutForeward = AnimationUtils.loadAnimation(this,
//				R.anim.flipout);
//		animFlipInBackward = AnimationUtils.loadAnimation(this,
//				R.anim.flipin_reverse);
//		animFlipOutBackward = AnimationUtils.loadAnimation(this,
//				R.anim.flipout_reverse);

		if (MySharedPref.getLanguage(FragmentPermissionRequest.this).equals(
				MyCommon.LANGUAGE_ENG)) {
			setHeader(getResources().getString(R.string.welcome)+" "+MyCommon.sLoginResponseBean.getNameEn(), true,true,true);
		}else
		{
			setHeader(getResources().getString(R.string.welcome)+" "+MyCommon.sLoginResponseBean.getNameAr(), true,true,true);
		}

		lang = MySharedPref.getLanguage(FragmentPermissionRequest.this);


		
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
			permDate.setText(dateString);

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

		 if (permDate.getText().toString().equalsIgnoreCase(MyCommon.DEFAULT_STRING)) {
			Show_Alert_Dialog(R.string.M_Enter_Perm_Date);
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

		PermReqType permReqType = (PermReqType)permTypeSpinner.getSelectedItem();

//		PermRequest	permRequest = new PermRequest(lang, permReqType.getPermReqTypeCode().toString(), dateFormatForDate1(permDate.getText().toString()), civilID, "0");

//		Gson gson = new Gson();
//		String convert = gson.toJson(permRequest);

//		Log.d("MzTagPermReq", convert);

			RequestParams params = new RequestParams();
			params.put("duration","0");
			params.put("date",dateFormatForDate1(permDate.getText().toString()));
			params.put("permTypeCd",permReqType.getPermReqTypeCode().toString());
			params.put("civilId",civilID);
			params.put("lang", lang);
			MyHttpConnection.get(MyCommon.WS_METHOD_PERMISSION_INSERT, params, asyncPermResponseHandler);



	}


	AsyncHttpResponseHandler asyncPermResponseHandler = new AsyncHttpResponseHandler() {

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
				JSONObject obj = (JSONObject)json.get("KfsdMobilePerms");
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




	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.submitButton:
			clickedSubmit();
			break;

			
//		case R.id.prevButton:
//			vflipper.setInAnimation(animFlipInForeward);
//			vflipper.setOutAnimation(animFlipOutForeward);
//			vflipper.showPrevious();
//			currentPage.setText((vflipper.getDisplayedChild()+1)+"-"+vflipper.getChildCount());
//			break;
//
//		case R.id.nextButton:
//			vflipper.setInAnimation(animFlipInBackward);
//			vflipper.setOutAnimation(animFlipOutBackward);
//			vflipper.showNext();
//
//			currentPage.setText((vflipper.getDisplayedChild()+1)+"-"+vflipper.getChildCount());
//			break;


		case R.id.endDateText:
			clickedEndDateDialog(v);
			break;

		default:
			break;
		}

	}



	AsyncHttpResponseHandler permissionType = new AsyncHttpResponseHandler() {

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
				JSONArray array = json.getJSONArray("KfsdMobilePermReqType");
				Log.d("MzTagPermReq",array.toString());
				List<PermReqType> msgTypeArray = new ArrayList<>();
				if(array != null && array.length()>0)
				{
					for(int i =0; i<array.length(); i++)
					{
						JSONObject obj = array.getJSONObject(i);
						PermReqType msgType = new PermReqType();
						msgType.setLeaveTypeCode(obj.getString("PermReqTypeCode"));
						msgType.setPermReqTypeDescAr(obj.getString("PermReqTypeDescAr"));
						msgType.setPermReqTypeDescEn(obj.getString("PermReqTypeDescEn"));
						msgTypeArray.add(msgType);
					}
				}


				PermReqType[] msges = new PermReqType[msgTypeArray.size()];
				PermRequestAdapter adapter = new PermRequestAdapter(getApplicationContext(),
						android.R.layout.simple_spinner_item, msgTypeArray.toArray(msges), lang);


				permTypeSpinner.setAdapter(adapter);

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
