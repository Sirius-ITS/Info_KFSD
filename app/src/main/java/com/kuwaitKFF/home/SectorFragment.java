package com.kuwaitKFF.home;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

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

public class SectorFragment extends BaseActivity implements OnClickListener {

	Kfsd kfsd;
	View view;
	ArrayList<String> listDataHeader;

	HashMap<String, ArrayList<String>> listDataChild;
	EditText queryText;
	TextView captcha;
	TextView queryLabel;
	Spinner querySpinner;
	Button submitButton;
	String queryTextStr;

	// TableLayout mainTable;
	Button link1, link2;
	EditText captchaCheck;

	TextView currentPage;
	Intent i;

	ViewPager pager = null;
	JSONArray jsonArray = null;
	//MyFragmentPagerAdapter pagerAdapter = null;
	MYPagerAdapter pagerAdapter = null;
	int pager_position = -1;
	FragmentManager fm;
	public SectorFragment() {
		super(R.layout.fragment_sector);
	}

	@Override
	public void initialization() {
		ButterKnife.bind(this);

	}

	@Override
	public void implementation() {
		kfsd = (Kfsd) getApplication();

		// mainTable = (TableLayout) findViewById(R.id.mailTable);


		captcha = (TextView) findViewById(R.id.captcha);

		queryText = (EditText) findViewById(R.id.querytext);
		querySpinner = (Spinner) findViewById(R.id.querySpinner);
		queryLabel = (TextView) findViewById(R.id.queryLabel);

		String[]  arraySpinner = new String[] {
				getString(R.string.file_no), getString(R.string.transaction_no)
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

				refreshTxtBoxes();
				resetView();
				if(position == 0)
					queryLabel.setText(R.string.file_no);
				else if(position == 1)
					queryLabel.setText(R.string.transaction_no);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}


		});


		currentPage = (TextView) findViewById(R.id.currentPage12);

		System.out.println("Captcha " + captcha);
		captchaCheck = (EditText) findViewById(R.id.captchaCheck);
		int randomPIN = (int) (Math.random() * 9000) + 1000;
		captcha.setText(String.valueOf(randomPIN));

		link1 = (Button) findViewById(R.id.linkText1);
		link1.setOnClickListener(this);
		link2 = (Button) findViewById(R.id.linkText2);
		link2.setOnClickListener(this);

		// write by imran
		link1.setVisibility(View.INVISIBLE);
		link2.setVisibility(View.INVISIBLE);

		/** Getting a reference to the ViewPager defined the layout file */
		pager = (ViewPager) findViewById(R.id.pager);

		/** Getting fragment manager */
		fm = getSupportFragmentManager();

		/** Instantiating FragmentPagerAdapter */
		//pagerAdapter = new MyFragmentPagerAdapter(fm);
		pagerAdapter = new MYPagerAdapter();

		/** Setting the pagerAdapter to the pager object */
		pager.setAdapter(pagerAdapter);

		pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

			@Override
			public void onPageSelected(int positions) {
//				pager_position = positions;

			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				Log.d("MzTagJSON","onPageScrolled()");

				try {
					pager_position = arg0;

					Log.d("MzTagJSON", "getting JSON Object Number" + arg0);

					int noteCount = Integer.parseInt(jsonArray.getJSONObject(arg0).getString("ReqNoteCount"));
					int orgCount = Integer.parseInt( jsonArray.getJSONObject(arg0).getString("ReqOrgCount") );

					Log.d("MzTagJSON", "note count: " + noteCount);
					Log.d("MzTagJSON", "org  count: " + orgCount);

					currentPage.setText( (arg0 + 1) + "/" + jsonArray.length());
//					data.putString("ReqNoteCount", jsonObj.optString("ReqNoteCount"));
//				data.putString("ReqOrgCount", jsonObj.optString("ReqOrgCount"));
//				///

//				int noteCount = Integer.parseInt(reqNoteCount);
//				int orgCount = Integer.parseInt(reqOrgCount);
//
				if(noteCount <= 0)
					link2.setVisibility(View.INVISIBLE);
				else
					link2.setVisibility(View.VISIBLE);

				if(orgCount <= 0)
					link1.setVisibility(View.INVISIBLE);
				else
					link1.setVisibility(View.VISIBLE);


				} catch (JSONException e) {
					e.printStackTrace();
				}catch (Exception e){e.printStackTrace();}




			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				Log.d("MzTag","onPageScrollStateChanged()");

			}
		});

		submitButton = (Button) findViewById(R.id.submitButton);
		submitButton.setOnClickListener(this);

		setHeader(getResources().getString(R.string.sector), true, true);

	}

	public class MYPagerAdapter extends PagerAdapter {
		int PAGE_COUNT = 0;
		
		TextView tablFileNo, tableTranscNo, tableTranscType,
		tableProcType, tableProcDate, tableOrganisational,
		tableResponsible;
		Date date;
		String formattedDate = "";
		
		@Override
		public int getCount() {
			if (jsonArray != null && jsonArray.length() > 0) {
				PAGE_COUNT = jsonArray.length();
			}
			return PAGE_COUNT;
		}
		@Override
		   public Object instantiateItem(ViewGroup container, int position) {
			 // Inflate a new layout from our resources
	       
	        View view=LayoutInflater.from(getApplicationContext()).inflate(R.layout.fragment_sector_pager,container,false);
			tablFileNo = (TextView) view.findViewById(R.id.fileNoText);
			tableTranscNo = (TextView) view.findViewById(R.id.transactionNo);
			tableTranscType = (TextView) view.findViewById(R.id.transactionType);
			tableProcType = (TextView) view.findViewById(R.id.procedureType);
			tableProcDate = (TextView) view.findViewById(R.id.procedureDate);
			tableOrganisational = (TextView) view.findViewById(R.id.organizational);
			tableResponsible = (TextView) view.findViewById(R.id.responsible);

			tablFileNo.setTypeface(null, Typeface.BOLD);
			tableTranscNo.setTypeface(null, Typeface.BOLD);
			tableTranscType.setTypeface(null, Typeface.BOLD);
			tableProcType.setTypeface(null, Typeface.BOLD);
			tableProcDate.setTypeface(null, Typeface.BOLD);
			tableOrganisational.setTypeface(null, Typeface.BOLD);
			tableResponsible.setTypeface(null, Typeface.BOLD);

			tablFileNo.setTextColor(Color.BLACK);
			tableTranscNo.setTextColor(Color.BLACK);
			tableTranscType.setTextColor(Color.BLACK);
			tableProcType.setTextColor(Color.BLACK);
			tableProcDate.setTextColor(Color.BLACK);
			tableOrganisational.setTextColor(Color.BLACK);
			tableResponsible.setTextColor(Color.BLACK);

//			tablFileNo.setTextSize(17f);
//			tableTranscNo.setTextSize(17f);
//			tableTranscType.setTextSize(17f);
//			tableProcType.setTextSize(17f);
//			tableProcDate.setTextSize(17f);
//			tableOrganisational.setTextSize(17f);
//			tableResponsible.setTextSize(17f);

			Bundle bundle=getBundle(position);
			
			if(bundle!=null){
			
				tablFileNo.setText(bundle.getString("ReqFileNo"));
				tableTranscNo.setText(bundle.getString("ReqSerial"));
				tableProcDate.setText(dateFormatForDate(bundle
						.getString("ReqActionDate")));
				tableTranscType.setText(bundle
						.getString("ReqTypeDesc"));
				tableProcType.setText(bundle.getString("ReqAction"));
				tableResponsible.setText(bundle
						.getString("ReqEmpName"));
				tableOrganisational.setText(bundle
						.getString("ReqOrgName"));
			}
			
			 container.addView(view);

	        return view;
		}
		public String dateFormatForDate(String dateVal) {

			SimpleDateFormat formatter = new SimpleDateFormat(
					"yyyy-MM-dd'T'HH:mm:ss");
			SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy/MM/dd");

			if(dateVal!=null&&!dateVal.isEmpty()&&!dateVal.equals("null")){
			
				try {
					date = formatter.parse(dateVal);
					formattedDate = formatter1.format(date);
				} catch (ParseException e) {				
					e.printStackTrace();
				}
			}
			

			return formattedDate;

		}


		@Override
		public boolean isViewFromObject(View view, Object object) {
			// TODO Auto-generated method stub
			return object == view;
		}
		
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
		    container.removeView((View) object);
		    
		}
		
		private Bundle getBundle(int pos){
			Bundle data = new Bundle();
			JSONObject jsonObj;
			try {
				jsonObj = jsonArray.getJSONObject(pos);
				data.putString("ReqFileNo", jsonObj.optString("ReqFileNo"));
				data.putString("ReqSerial", jsonObj.optString("ReqSerial"));
				data.putString("ReqActionDate",
						jsonObj.optString("ReqActionDate"));

				if (MySharedPref.getLanguage(SectorFragment.this)
						.equalsIgnoreCase(MyCommon.LANGUAGE_ENG)) {

					data.putString("ReqTypeDesc",
							jsonObj.optString("ReqTypeDescEn"));
				} else {
					data.putString("ReqTypeDesc",
							jsonObj.optString("ReqTypeDescAr"));

				}
				if (MySharedPref.getLanguage(SectorFragment.this)
						.equalsIgnoreCase(MyCommon.LANGUAGE_ENG)) {
					data.putString("ReqAction",
							jsonObj.optString("ReqActionEn"));
				} else {
					data.putString("ReqAction",
							jsonObj.optString("ReqActionAr"));
				}

				if (MySharedPref.getLanguage(SectorFragment.this)
						.equalsIgnoreCase(MyCommon.LANGUAGE_ENG)) {
					if (jsonObj.getString("ReqEmpNameEn") != null
							&& !"null".equalsIgnoreCase(jsonObj
									.getString("ReqEmpNameEn")))
						data.putString("ReqEmpName",
								jsonObj.optString("ReqEmpNameEn"));

				} else {
					if (jsonObj.getString("ReqEmpNameAr") != null
							&& !"null".equalsIgnoreCase(jsonObj
									.getString("ReqEmpNameAr")))
						data.putString("ReqEmpName",
								jsonObj.optString("ReqEmpNameAr"));

				}

				if (MySharedPref.getLanguage(SectorFragment.this)
						.equalsIgnoreCase(MyCommon.LANGUAGE_ENG)) {
					if (jsonObj.getString("ReqOrgNameEn") != null
							&& !"null".equalsIgnoreCase(jsonObj
									.getString("ReqOrgNameEn")))
						data.putString("ReqOrgName",
								jsonObj.optString("ReqOrgNameEn"));

				} else {
					if (jsonObj.getString("ReqOrgNameAr") != null
							&& !"null".equalsIgnoreCase(jsonObj
									.getString("ReqOrgNameAr")))
						data.putString("ReqOrgName",
								jsonObj.optString("ReqOrgNameAr"));
				}


				///herre
//				data.putString("ReqNoteCount", jsonObj.optString("ReqNoteCount"));
//				data.putString("ReqOrgCount", jsonObj.optString("ReqOrgCount"));
//				///

//				int noteCount = Integer.parseInt(reqNoteCount);
//				int orgCount = Integer.parseInt(reqOrgCount);
//
//				if(noteCount <= 0)
//					link2.setVisibility(View.INVISIBLE);
//				if(orgCount <= 0)
//					link1.setVisibility(View.INVISIBLE);

			} catch (JSONException e) {
				e.printStackTrace();
			}
			return data;
		}	
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			
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

			if(captchaCheck.getText().toString().equals("")) {
				Show_Alert_Dialog(R.string.M_captcha_empty);
				break;
			}

			if (captchaCheck.getText() != null
					&& !captchaCheck.getText().toString()
							.equalsIgnoreCase(captcha.getText().toString())) {
				Show_Alert_Dialog(R.string.M_Please_Enter_correct);
				break;
			}
			pager_position = -1;
			queryTextStr = queryText.getText().toString();

			if (queryTextStr.isEmpty()) {

				if(querySpinner.getSelectedItemPosition() == 0)
					Show_Alert_Dialog(R.string.M_Enter_File_No);
				else
					Show_Alert_Dialog(R.string.M_Enter_Transaction_No);

			} else if (kfsd.checkNetworkRechability()) {

				callWebService();

			} else
				Show_No_Internet();

			break;

		case R.id.linkText1:

			if (jsonArray != null && jsonArray.length() > 0&&pager_position>-1) {

				String objSerialNo = "";
				try {
					objSerialNo = jsonArray.getJSONObject(pager_position)
							.getString("ReqSerial");
				} catch (JSONException e) {

					e.printStackTrace();
				}

				i = new Intent();

				Bundle bundle = new Bundle();

				// Add your data to bundle
				bundle.putString("mycvalue", objSerialNo);

				i.putExtras(bundle);
				i.setClass(SectorFragment.this, CreditSectorFragment.class);
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
						| Intent.FLAG_ACTIVITY_CLEAR_TOP);

				startActivity(i);

			}

			break;

		case R.id.linkText2:
			// write by imran
			if (jsonArray != null && jsonArray.length() > 0&&pager_position>-1) {
				String objSerialNo = "";
				try {
					objSerialNo = jsonArray.getJSONObject(pager_position)
							.getString("ReqSerial");
				} catch (JSONException e) {

					e.printStackTrace();
				}
				i = new Intent();
				// Create the bundle
				Bundle bundle = new Bundle();

				// Add your data to bundle
				bundle.putString("mycvalue", objSerialNo);

				// Add the bundle to the intent
				i.putExtras(bundle);

				i.setClass(SectorFragment.this, RemarkSectorFragment.class);
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
						| Intent.FLAG_ACTIVITY_CLEAR_TOP);

				startActivity(i);
			}

			break;

		default:
			break;
		}

	}

	public void callWebService() {

		RequestParams params;

		if(querySpinner.getSelectedItemPosition() == 0) {
			params = new RequestParams(MyCommon.WS_PARA_FILENO, "" + queryTextStr);
			params.put(MyCommon.WS_PARA_SERIALID, "" + "");
		}
		else {
			params = new RequestParams(MyCommon.WS_PARA_SERIALID, "" + queryTextStr);
			params.put(MyCommon.WS_PARA_FILENO, "" + "");
		}



		MyHttpConnection.get(MyCommon.WS_METHOD_SECTOR, params,
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
			currentPage.setVisibility(View.VISIBLE);
			try {

				JSONObject json = null;
				try {
					json = new JSONObject(new String(responseBody, "UTF-8"));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				if (json != null&&json.has("KfsdMobileSectorReq")) {

					jsonArray = json.getJSONArray("KfsdMobileSectorReq");

					Log.d("MzTag", jsonArray.toString());

					if (jsonArray != null && jsonArray.length() > 0) {
						pager_position=1;/** Instantiating FragmentPagerAdapter */
						pager.setVisibility(View.VISIBLE);
						pagerAdapter=new MYPagerAdapter();
						//pagerAdapter = new MyFragmentPagerAdapter(fm);
						pager.invalidate();
						pager.setAdapter(pagerAdapter);
						//pagerAdapter.notifyDataSetChanged();

//						link1.setVisibility(View.VISIBLE); // write by imran
//						link2.setVisibility(View.VISIBLE); // write by imran
					}else{
						resetView();
						Show_Alert_Dialog(R.string.M_Unable_To_Fetch_Data);
					}

				}else{
					resetView();
					Show_Alert_Dialog(R.string.M_Unable_To_Fetch_Data);
				}


			} catch (JSONException e) {
				e.printStackTrace();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}

		@Override
		public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
			resetView();
			Show_Alert_Dialog(R.string.M_Unable_To_Fetch_Data);
		}

		@Override
		public void onStart() {
			DisplayProgress();
			super.onStart();
		}

	};

	public void hideSoftKeyboard() {
		if (getCurrentFocus() != null) {
			InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
			inputMethodManager.hideSoftInputFromWindow(getCurrentFocus()
					.getWindowToken(), 0);
		}
	}


	public void refreshTxtBoxes()
	{
		queryText.setText("");
		captchaCheck.setText("");
	}

	public void resetView(){
		jsonArray=new JSONArray();
		pagerAdapter.notifyDataSetChanged();
		pager.setVisibility(View.INVISIBLE);
		link1.setVisibility(View.INVISIBLE); // write by imran
		link2.setVisibility(View.INVISIBLE);
		currentPage.setVisibility(View.INVISIBLE);
	}

}
