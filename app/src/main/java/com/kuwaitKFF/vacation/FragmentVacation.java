package com.kuwaitKFF.vacation;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import com.kuwaitKFF.base.BaseActivity;
import com.kuwaitKFF.common.MyCommon;
import com.kuwaitKFF.common.MyHttpConnection;
import com.kuwaitKFF.common.MySharedPref;
import com.kuwaitKFF.main.Kfsd;
import com.kuwaitKFF.R;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.ViewFlipper;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.GestureDetector;

import androidx.core.view.GestureDetectorCompat;

import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class FragmentVacation extends BaseActivity implements OnClickListener {

	Kfsd kfsd;
	View view;
	ImageView imageView1;
	ImageView imageView2;
	ImageView imageView3;
	LinearLayout relative1;
	RelativeLayout.LayoutParams params;
	boolean openCloseFlag = false;
	ImageView loginImage;
	Button submitButton;

	TextView text;
	TextView attachmentLink;

	ViewFlipper vflipper;
	LinearLayout lLayout;
	TextView tv;
	TableLayout tbl;
	Button prev;
	Button next;

	View v0;
	View v1;
	Date dateStart,dateEnd;

	float tableTxtSize = 17f;

	ArrayList<String> data;
	private float initialX;
	private GestureDetectorCompat mDetector;

	Animation slide_in_left, slide_out_right;
	Animation slide_in_right, slide_out_left;

	TextView startDate, endDate;
	private CaldroidFragment dialogCaldroidFragment;
	SimpleDateFormat formatter;
	
	SimpleDateFormat formatterForDisplay;
	String dateForSendingToServer = "";

	RelativeLayout bottom;

	Animation animFlipInForeward;
	Animation animFlipOutForeward;
	Animation animFlipInBackward;
	Animation animFlipOutBackward;
	LinearLayout mainLinear;
	int deviceTotalWidth=0;
	boolean attachmentVisible = true;
	TextView title;
	TextView currentPage;
	
	ArrayList<VacationResponseBean> vacationResponseBeansList;
	
	Date date;
	String formattedDate = null;
	private LinearLayout topHeadingTxt;
	
	
	
	public FragmentVacation() {
		super(R.layout.fragment_vacations);

	}

	@Override
	public void initialization() {
		ButterKnife.bind(this);

	}

	@Override
	public void implementation() {
		kfsd = (Kfsd)getApplication();
		
		DisplayMetrics metrics = getResources().getDisplayMetrics();
		deviceTotalWidth = metrics.widthPixels;

		topHeadingTxt = (LinearLayout) findViewById(R.id.topHeadingTxt);
		
		title = (TextView)findViewById(R.id.titleText);
		title.setText(getResources().getString(R.string.vacations));
		formatter = new SimpleDateFormat("MM/dd/yyyy");
		if (MySharedPref.getLanguage(FragmentVacation.this).equals(
				MyCommon.LANGUAGE_ENG)) {
		formatterForDisplay = new SimpleDateFormat("dd-MM-yyyy");
		}
		else{
			formatterForDisplay = new SimpleDateFormat("yyyy-MM-dd");
		}

		submitButton = (Button) findViewById(R.id.submitButton);
		submitButton.setOnClickListener(this);

		attachmentLink = (TextView)findViewById(R.id.attachmentLink);
		attachmentLink.setOnClickListener(this);
			
		currentPage = (TextView) findViewById(R.id.currentPage);
		currentPage.setOnClickListener(this);
		
		
		// prev = (Button) view.findViewById(R.id.prevButton);
		// prev.setOnClickListener(this);
		//
		// next = (Button) view.findViewById(R.id.nextButton);
		// next.setOnClickListener(this);

		// bottom = (RelativeLayout) view.findViewById(R.id.bottomLayout);

		vflipper = (ViewFlipper)findViewById(R.id.mainFlipper);

		vflipper.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				if (event.getAction() == MotionEvent.ACTION_MOVE) {
					return gestureDetector.onTouchEvent(event);
				} else {

					gestureDetector.onTouchEvent(event);
					

				}

				return true;
			}
		});

		data = new ArrayList<String>();
		data.add("hiiiii");
		data.add("hello");

		startDate = (TextView)findViewById(R.id.startDateText);
		startDate.setOnClickListener(this);

		endDate = (TextView)findViewById(R.id.endDateText);
		endDate.setOnClickListener(this);

		animFlipInForeward = AnimationUtils.loadAnimation(this,
				R.anim.flipin);
		animFlipOutForeward = AnimationUtils.loadAnimation(this,
				R.anim.flipout);
		animFlipInBackward = AnimationUtils.loadAnimation(this,
				R.anim.flipin_reverse);
		animFlipOutBackward = AnimationUtils.loadAnimation(this,
				R.anim.flipout_reverse);


		if (MySharedPref.getLanguage(FragmentVacation.this).equals(
				MyCommon.LANGUAGE_ENG)) {
			setHeader(getResources().getString(R.string.welcome)+" "+MyCommon.sLoginResponseBean.getNameEn(), true,true,true);
		}else
		{
			setHeader(getResources().getString(R.string.welcome)+" "+MyCommon.sLoginResponseBean.getNameAr(), true,true,true);
		}


	}

	
	private void SwipeRight() 
	{
		vflipper.setInAnimation(animFlipInBackward);
		vflipper.setOutAnimation(animFlipOutBackward);
		vflipper.showPrevious();
		setUpAttachmentTable();
		currentPage.setText((vflipper.getDisplayedChild()+1)+"-"+vflipper.getChildCount());
	}

	private void SwipeLeft() {
		vflipper.setInAnimation(animFlipInForeward);
		vflipper.setOutAnimation(animFlipOutForeward);
		vflipper.showNext();
		setUpAttachmentTable();
		currentPage.setText((vflipper.getDisplayedChild()+1)+"-"+vflipper.getChildCount());
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		return gestureDetector.onTouchEvent(event);
	}

	SimpleOnGestureListener simpleOnGestureListener = new SimpleOnGestureListener() {

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {

			float sensitvity = 50;
			if ((e1.getX() - e2.getX()) > sensitvity) {
				SwipeLeft();
				VacationResponseBean bean = vacationResponseBeansList.get(vflipper.getDisplayedChild());
				//changeTableValue();
			} else if ((e2.getX() - e1.getX()) > sensitvity) {
				SwipeRight();
				//changeTableValue(data.get(vflipper.getDisplayedChild()));
			}

			return true;
		}

	};

	GestureDetector gestureDetector = new GestureDetector(
			simpleOnGestureListener);

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
			endDate.setText(dateString);

		}

		@Override
		public void onChangeMonth(int month, int year) {
		}

	};

	public void clickedDateDialog(View view) {

		dialogCaldroidFragment = new CaldroidFragment();
		dialogCaldroidFragment.setCaldroidListener(dateSelectListener);

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
	final CaldroidListener dateSelectListener = new CaldroidListener() {

		@Override
		public void onSelectDate(Date date, View view) {
			// Toast.makeText(getApplicationContext(), formatter.format(date),
			// Toast.LENGTH_SHORT).show();
			dialogCaldroidFragment.dismiss();
			String dateString = fetchEnglishFromArabicNumber(formatterForDisplay
					.format(date));
			dateForSendingToServer = formatter.format(date);
			startDate.setText(dateString);

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
	
	public String dateFormatForDate(String dateVal) {


		SimpleDateFormat formatter = new SimpleDateFormat(
				"yyyy-MM-dd'T'HH:mm:ss");
		SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy/MM/dd");

		try {
			date = formatter.parse(dateVal);
			formattedDate = formatter1.format(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return formattedDate;

	}



	private void setUpTableNew(ArrayList<VacationResponseBean> vacationResponseBeansList) 
	{
			for (int i = 0; i < vacationResponseBeansList.size(); i++) {

				lLayout = new LinearLayout(this);
				lLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
						LayoutParams.WRAP_CONTENT));


				lLayout.setOrientation(LinearLayout.HORIZONTAL);
				lLayout.setGravity(Gravity.CENTER);
				// lLayout.setOnTouchListener(this);
				lLayout.setId(i);

				tbl = new TableLayout(this);
				tbl.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
						LayoutParams.WRAP_CONTENT));

				TableRow tr = new TableRow(this);
				tr.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
						LayoutParams.WRAP_CONTENT));
				tr.setBackgroundColor(Color.parseColor("#5B83B6"));

				TextView t1 = new TextView(this);
				TableRow.LayoutParams params1 = new TableRow.LayoutParams(0,
						LayoutParams.WRAP_CONTENT);
				params1.weight = 1;
				params1.width = LayoutParams.MATCH_PARENT;
				params1.height = LayoutParams.WRAP_CONTENT;
				params1.leftMargin = 10;
				t1.setLayoutParams(params1);
				t1.setText(getResources().getString(R.string.submission_date));
				t1.setTextColor(Color.parseColor("#003E94"));
				t1.setTextSize(tableTxtSize);
				t1.setPadding(5, 5, 5, 5);
				// t1.setGravity(Gravity.CENTER);

				TextView t2 = new TextView(this);
				TableRow.LayoutParams params2 = new TableRow.LayoutParams(0,
						LayoutParams.WRAP_CONTENT);
				params2.width = LayoutParams.MATCH_PARENT;
				params2.height = LayoutParams.WRAP_CONTENT;

				params2.weight = 1;
				t2.setLayoutParams(params2);
				t2.setText(dateFormatForDate(vacationResponseBeansList.get(i).getLeaveReqDate()));
				t2.setTextSize(tableTxtSize);
				t2.setGravity(Gravity.RIGHT);

				tr.addView(t1);
				tr.addView(t2);

				TableRow tr1 = new TableRow(this);
				tr1.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
						LayoutParams.WRAP_CONTENT));
				tr1.setBackgroundColor(Color.parseColor("#D7DFEA"));

				TextView tr1t1 = new TextView(this);
				TableRow.LayoutParams tr1params1 = new TableRow.LayoutParams(0,
						LayoutParams.WRAP_CONTENT);
				tr1params1.weight = 1;
				tr1params1.width = LayoutParams.MATCH_PARENT;
				tr1params1.height = LayoutParams.WRAP_CONTENT;
				tr1params1.leftMargin = 10;
				tr1t1.setLayoutParams(tr1params1);
				tr1t1.setText(getResources().getString(R.string.vacation_type));
				tr1t1.setTextColor(Color.parseColor("#003E94"));
				tr1t1.setTextSize(tableTxtSize);
				tr1t1.setPadding(5, 5, 5, 5);
//			 tr1t1.setGravity(Gravity.CENTER);

				TextView tr1t2 = new TextView(this);
				TableRow.LayoutParams tr1params2 = new TableRow.LayoutParams(0,
						LayoutParams.WRAP_CONTENT);
				tr1params2.width = LayoutParams.MATCH_PARENT;
				tr1params2.height = LayoutParams.WRAP_CONTENT;

				tr1params2.weight = 1;
				tr1t2.setLayoutParams(tr1params2);
				tr1t2.setText(vacationResponseBeansList.get(i).getLeaveDescEn());
				tr1t2.setTextSize(tableTxtSize);
				 tr1t2.setGravity(Gravity.RIGHT);

				tr1.addView(tr1t1);
				tr1.addView(tr1t2);

				TableRow tr3 = new TableRow(this);
				tr3.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
						LayoutParams.WRAP_CONTENT));
				tr3.setBackgroundColor(Color.parseColor("#5B83B6"));

				TextView tr3t1 = new TextView(this);
				TableRow.LayoutParams tr3params1 = new TableRow.LayoutParams(0,
						LayoutParams.WRAP_CONTENT);
				tr3params1.weight = 1;
				tr3params1.width = LayoutParams.MATCH_PARENT;
				tr3params1.height = LayoutParams.WRAP_CONTENT;
				tr3params1.leftMargin = 10;
				tr3t1.setLayoutParams(tr3params1);
				tr3t1.setText(getResources().getString(R.string.start_date));
				tr3t1.setTextColor(Color.parseColor("#003E94"));
				tr3t1.setTextSize(tableTxtSize);
				tr3t1.setPadding(5, 5, 5, 5);
				// tr3t1.setGravity(Gravity.CENTER);

				TextView tr3t2 = new TextView(this);
				TableRow.LayoutParams tr3params2 = new TableRow.LayoutParams(0,
						LayoutParams.WRAP_CONTENT);
				tr3params2.width = LayoutParams.MATCH_PARENT;
				tr3params2.height = LayoutParams.WRAP_CONTENT;

				tr3params2.weight = 1;
				tr3t2.setLayoutParams(tr3params2);
				tr3t2.setText(dateFormatForDate(vacationResponseBeansList.get(i).getLeaveStartDate()));
				tr3t2.setTextSize(tableTxtSize);
				 tr3t2.setGravity(Gravity.RIGHT);

				tr3.addView(tr3t1);
				tr3.addView(tr3t2);

				TableRow tr4 = new TableRow(this);
				tr4.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
						LayoutParams.WRAP_CONTENT));
				tr4.setBackgroundColor(Color.parseColor("#D7DFEA"));

				TextView tr4t1 = new TextView(this);
				TableRow.LayoutParams tr4params1 = new TableRow.LayoutParams(0,
						LayoutParams.WRAP_CONTENT);
				tr4params1.weight = 1;
				tr4params1.width = LayoutParams.MATCH_PARENT;
				tr4params1.height = LayoutParams.WRAP_CONTENT;
				tr4params1.leftMargin = 10;




				tr4t1.setLayoutParams(tr4params1);
				tr4t1.setText(getResources().getString(R.string.end_date));
				tr4t1.setTextColor(Color.parseColor("#003E94"));
				tr4t1.setTextSize(tableTxtSize);
				tr4t1.setPadding(5, 5, 5, 5);
				tr4t1.setGravity(Gravity.LEFT);

				TextView tr4t2 = new TextView(this);
				TableRow.LayoutParams tr4params2 = new TableRow.LayoutParams(0,
						LayoutParams.WRAP_CONTENT);
				tr4params2.width = LayoutParams.MATCH_PARENT;
				tr4params2.height = LayoutParams.WRAP_CONTENT;

				tr4params2.weight = 1;
				tr4t2.setLayoutParams(tr4params2);
				tr4t2.setText(dateFormatForDate(vacationResponseBeansList.get(i).getLeaveEndDate()));
				tr4t2.setTextSize(tableTxtSize);
				tr4t2.setGravity(Gravity.RIGHT);

				tr4.addView(tr4t1);
				tr4.addView(tr4t2);

				TableRow tr5 = new TableRow(this);
				tr5.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
						LayoutParams.WRAP_CONTENT));
				tr5.setBackgroundColor(Color.parseColor("#5B83B6"));

				TextView tr5t1 = new TextView(this);
				TableRow.LayoutParams tr5params1 = new TableRow.LayoutParams(0,
						LayoutParams.WRAP_CONTENT);
				tr5params1.weight = 1;

				tr5params1.width = 0;
				tr5params1.height = LayoutParams.WRAP_CONTENT;
				tr5params1.leftMargin = 10;
				tr5t1.setLayoutParams(tr5params1);
				tr5t1.setText(getResources().getString(R.string.vacation_status));
				tr5t1.setTextColor(Color.parseColor("#003E94"));
				tr5t1.setTextSize(tableTxtSize);
				tr5t1.setPadding(5, 5, 5, 5);
				tr5t1.setGravity(Gravity.LEFT);

				TextView tr5t2 = new TextView(this);
				TableRow.LayoutParams tr5params2 = new TableRow.LayoutParams(0,
						LayoutParams.WRAP_CONTENT);
				tr5params2.width = 0;
				tr5params2.height = LayoutParams.WRAP_CONTENT;


				tr5params2.weight = 1;
				tr5t2.setLayoutParams(tr5params2);

				tr5t2.setText(vacationResponseBeansList.get(i).getLeaveReqStatusDescEn());
				tr5t2.setTextSize(tableTxtSize - 2);
				tr5t2.setGravity(Gravity.RIGHT);
				tr5t2.setPadding(0, 0, 1, 5);

				tr5.addView(tr5t1);
				tr5.addView(tr5t2);


				tbl.addView(tr);
				tbl.addView(tr1);
				tbl.addView(tr3);
				tbl.addView(tr4);
				tbl.addView(tr5);



				lLayout.addView(tbl);

				vflipper.addView(lLayout);

			}

			setUpAttachmentTable();
			currentPage.setText((vflipper.getDisplayedChild()+1)+"-"+vflipper.getChildCount());

	}

	public int convertToPixel(int dp) {
		final float scale = this.getResources().getDisplayMetrics().density;
		int pixels = (int) (dp * scale + 0.5f);
		return pixels;

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
	
	
public void clickedSubmit() {
		
		try {
			 dateStart = formatterForDisplay.parse(startDate.getText().toString());
			 dateEnd = formatterForDisplay.parse(endDate.getText().toString());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	

    	
		if (startDate.getText().toString()
				.equalsIgnoreCase(MyCommon.DEFAULT_STRING)) {
			Show_Alert_Dialog(R.string.M_Enter_Start_Date);
		} else if (endDate.getText().toString()
				.equalsIgnoreCase(MyCommon.DEFAULT_STRING)) {

			Show_Alert_Dialog(R.string.M_Enter_End_Date);
		} else if(dateEnd.compareTo(dateStart) <0) {
			Show_Alert_Dialog(R.string.M_End_Date_Less);
		}else {

			if (kfsd.checkNetworkRechability())
				callWebService();
			else
				Show_No_Internet();

		}

	}

public String dateFormatForDate1(String dateVal) {


	SimpleDateFormat formatter = new SimpleDateFormat(
			"yy-MM-dd");
	SimpleDateFormat formatter1 = new SimpleDateFormat("dd-MM-yy");

	try {
		date = formatter.parse(dateVal);
		formattedDate = formatter1.format(date);
	} catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

	return formattedDate;

}
public void callWebService() {

	if (MySharedPref.getLanguage(FragmentVacation.this).equals(
			MyCommon.LANGUAGE_ENG))
	{
		RequestParams params = new RequestParams(MyCommon.WS_PARA_START_DATE,
				"" + startDate.getText().toString()); // / startDate.getText().toString()

		params.put(MyCommon.WS_PARA_END_DATE, "" + endDate.getText().toString()); // endDate.getText().toString()

		params.put(MyCommon.WS_PARA_CIVILID, "" + MyCommon.sLoginResponseBean.getCivilId());

		MyHttpConnection.get(MyCommon.WS_METHOD_VACATION, params,
				asyncLoginResponseHandler);
	}
	else
	{
		RequestParams params = new RequestParams(MyCommon.WS_PARA_START_DATE,
				"" + dateFormatForDate1(startDate.getText().toString())); // / startDate.getText().toString()
		params.put(MyCommon.WS_PARA_END_DATE, "" + dateFormatForDate1(endDate.getText().toString())); // endDate.getText().toString()

		params.put(MyCommon.WS_PARA_CIVILID, "" + MyCommon.sLoginResponseBean.getCivilId());

		Log.d("MzTagVac", params.toString());

		MyHttpConnection.get(MyCommon.WS_METHOD_VACATION, params,
				asyncLoginResponseHandler);
	}
	

}

AsyncHttpResponseHandler asyncLoginResponseHandler = new AsyncHttpResponseHandler() {

	@Override
	public void onFinish() {
		DismissProgress();
		super.onFinish();
	}

	@Override
	public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
		vflipper.removeAllViewsInLayout();
		vflipper.setVisibility(View.INVISIBLE);
		attachmentLink.setVisibility(View.INVISIBLE);


		//setUpAttachmentTable("this is first");
		//mainLinear.removeAllViews();
		//response = MyCommon.getJsonFromWebServiceResponse(response);

		try
		{

			System.out.println("#123vacationResponseBeansListResponse "+responseBody);
			JSONObject json = null;
				try {
					json = new JSONObject(new String(responseBody, "UTF-8"));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			int result = json.getInt("success");

			System.out.println("#123vacationResponseBeansListresult "+result);

			if(result==0)
			{
				if(mainLinear != null)
					mainLinear.removeAllViews();
				topHeadingTxt.setVisibility(View.GONE);
				currentPage.setVisibility(View.INVISIBLE);
				Show_Alert_Dialog(getResources().getString(R.string.No_data_available));
			}
			else
			{


				JSONArray jsonArray = json.getJSONArray("KfsdMobileTotalLeaves");
				vacationResponseBeansList = new ArrayList<VacationResponseBean>();

				Log.d("MzTagVac", jsonArray.toString());

				for (int i = 0; i < jsonArray.length(); i++)
				{
					JSONObject jsonObject = jsonArray.getJSONObject(i);
					VacationResponseBean vacationResponseBean = new VacationResponseBean();
					vacationResponseBean.setCivilId(jsonObject
							.getString("EmpCivilId"));
					vacationResponseBean.setLeaveCode(jsonObject
							.getString("EmpLeaveCode"));

					vacationResponseBean.setLeaveDescAr(jsonObject
							.getString("EmpLeaveDescAr"));

					vacationResponseBean.setLeaveDescEn(jsonObject
							.getString("EmpLeaveDescEn"));

					vacationResponseBean.setLeaveEndDate(jsonObject
							.getString("EmpLeaveEndDate"));

					vacationResponseBean.setLeaveReqAppFinalDate(jsonObject
							.getString("EmpLeaveReqAppFnalDate"));

					vacationResponseBean.setLeaveReqAppFlag(jsonObject
							.getString("EmpLeaveReqAppFlag"));

					vacationResponseBean.setLeaveReqDate(jsonObject
							.getString("EmpLeaveReqDate"));

					vacationResponseBean.setLeaveReqSerial(jsonObject
							.getString("EmpLeaveReqSerial"));

					vacationResponseBean.setLeaveReqStatusDescAr(jsonObject
							.getString("EmpLeaveReqStatusDescAr"));

					vacationResponseBean.setLeaveReqStatusDescEn(jsonObject
							.getString("EmpLeaveReqStatusDescEn"));

					vacationResponseBean.setLeaveStartDate(jsonObject
							.getString("EmpLeaveStartDate"));


					JSONArray attachmentsJsonArray = jsonObject.getJSONArray("attachments");
					ArrayList<AttachmentBean> attachmentBeanList = new ArrayList<AttachmentBean>();

					for (int j = 0; j < attachmentsJsonArray.length(); j++)
					{
						JSONObject myAttachments = attachmentsJsonArray.getJSONObject(j);
						AttachmentBean attachmentBean = new AttachmentBean();
						attachmentBean.setEmpAppStatusDescAr(myAttachments.getString("EmpAppStatusDescAr"));
						attachmentBean.setEmpLeaveReqAppEmpNameAr(myAttachments.getString("EmpLeaveReqAppEmpNameAr"));
						attachmentBean.setEmpLeaveReqAppEmpNameEn(myAttachments.getString("EmpLeaveReqAppEmpNameEn"));
						attachmentBean.setEmpLeaveReqAppEntNameAr(myAttachments.getString("EmpLeaveReqAppEntNameAr"));
						attachmentBean.setEmpLeaveReqAppEntNameEn(myAttachments.getString("EmpLeaveReqAppEntNameEn"));
						attachmentBeanList.add(attachmentBean);
					}
					vacationResponseBean.setAttachmentBeanList(attachmentBeanList);
					vacationResponseBeansList.add(vacationResponseBean);
				}

				//				//sort Ascending
				Collections.sort(vacationResponseBeansList, new Comparator() {

					public int compare(Object o1, Object o2) {
						VacationResponseBean p1 = (VacationResponseBean) o1;
						VacationResponseBean p2 = (VacationResponseBean) o2;
						return p1.getLeaveStartDate().compareToIgnoreCase(p2.getLeaveStartDate());
					}
				});

				//Reverse to get descending
				Collections.reverse(vacationResponseBeansList);

				if(vacationResponseBeansList != null && vacationResponseBeansList.size()>0)
				{
					if (MySharedPref.getLanguage(FragmentVacation.this).equals(MyCommon.LANGUAGE_ENG))
					{
						setUpTableNew(vacationResponseBeansList);
					}
					else
					{
						setUpTableForArabic(vacationResponseBeansList);
					}

					vflipper.setVisibility(View.VISIBLE);
					vflipper.setDisplayedChild(0);
					attachmentLink.setVisibility(View.VISIBLE);

					currentPage.setVisibility(View.VISIBLE);
					currentPage.setText((vflipper.getDisplayedChild() + 1) + "-" + vflipper.getChildCount());
					setUpAttachmentTable();
				}


			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
		Log.d("MzTagVac","onFailure()");
		vflipper.removeAllViewsInLayout();
		vflipper.setVisibility(View.INVISIBLE);
		//mainLinear.removeAllViews();
		currentPage.setVisibility(View.INVISIBLE);
		attachmentLink.setVisibility(View.INVISIBLE);
		Show_Alert_Dialog(R.string.M_Unable_To_Fetch_Data);
	}

	@Override
	public void onStart() {
		DisplayProgress();
		super.onStart();
	}

};




	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.submitButton:
			
			clickedSubmit();

			// bottom.setVisibility(View.VISIBLE);
			break;
			
		case R.id.attachmentLink:
			if(attachmentVisible)
			{
				topHeadingTxt.setVisibility(View.VISIBLE);
				mainLinear.setVisibility(View.VISIBLE);
				attachmentVisible =false;
			}else
			{
				topHeadingTxt.setVisibility(View.GONE);
				mainLinear.setVisibility(View.GONE);
				
				attachmentVisible =true;
			}
            break;
		// case R.id.prevButton:
		// vflipper.showPrevious();
		// break;
		//
		// case R.id.nextButton:
		// vflipper.showNext();
		// break;

		case R.id.startDateText:
			clickedDateDialog(v);
			break;

		case R.id.endDateText:
			clickedEndDateDialog(v);
			break;

		default:
			break;
		}

	}

	private void setUpTableForArabic(ArrayList<VacationResponseBean> vacationResponseBeansList) {

		for (int i = 0; i < vacationResponseBeansList.size(); i++)
		{

			lLayout = new LinearLayout(this);
			lLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.WRAP_CONTENT));

			lLayout.setOrientation(LinearLayout.HORIZONTAL);
			lLayout.setGravity(Gravity.CENTER);
			// lLayout.setOnTouchListener(this);
			// lLayout.setId(1);

			tbl = new TableLayout(this);
			tbl.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.WRAP_CONTENT));

			TableRow tr = new TableRow(this);
			tr.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.WRAP_CONTENT));
			tr.setBackgroundColor(Color.parseColor("#5B83B6"));

			TextView t1 = new TextView(this);
			TableRow.LayoutParams params1 = new TableRow.LayoutParams(0,
					LayoutParams.WRAP_CONTENT);
			params1.weight = 1;
			params1.width = LayoutParams.MATCH_PARENT;;
			params1.height = LayoutParams.WRAP_CONTENT;
			params1.leftMargin = 10;
			t1.setLayoutParams(params1);
			t1.setText(dateFormatForDate(vacationResponseBeansList.get(i).getLeaveReqDate()));
			t1.setTextColor(Color.parseColor("#003E94"));
			t1.setTextSize(tableTxtSize);
			t1.setPadding(5, 5, 5, 5);
//			 t1.setGravity(Gravity.RIGHT);

			TextView t2 = new TextView(this);
			TableRow.LayoutParams params2 = new TableRow.LayoutParams(0,
					LayoutParams.WRAP_CONTENT);
			params2.width = LayoutParams.MATCH_PARENT;
			params2.height = LayoutParams.WRAP_CONTENT;

			params2.weight = 1;
			params2.rightMargin = 10;
			t2.setLayoutParams(params2);
			t2.setText(getResources().getString(R.string.submission_date));
			t2.setTextSize(tableTxtSize);
//			t2.setGravity(Gravity.RIGHT);

			tr.addView(t1);
			tr.addView(t2);

			TableRow tr1 = new TableRow(this);
			tr1.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.WRAP_CONTENT));
			tr1.setBackgroundColor(Color.parseColor("#D7DFEA"));

			TextView tr1t1 = new TextView(this);
			TableRow.LayoutParams tr1params1 = new TableRow.LayoutParams(0,
					LayoutParams.WRAP_CONTENT);
			tr1params1.weight = 1;
			tr1params1.width = LayoutParams.MATCH_PARENT;;
			tr1params1.height = LayoutParams.WRAP_CONTENT;
			tr1params1.leftMargin = 10;
			tr1t1.setLayoutParams(tr1params1);
			tr1t1.setText(vacationResponseBeansList.get(i).getLeaveDescAr());
			tr1t1.setTextColor(Color.parseColor("#003E94"));
			tr1t1.setTextSize(tableTxtSize);
			tr1t1.setPadding(5, 5, 5, 5);
			 tr1t1.setGravity(Gravity.LEFT);

			TextView tr1t2 = new TextView(this);
			TableRow.LayoutParams tr1params2 = new TableRow.LayoutParams(0,
					LayoutParams.WRAP_CONTENT);
			tr1params2.width = LayoutParams.MATCH_PARENT;
			tr1params2.height = LayoutParams.WRAP_CONTENT;
			tr1params2.rightMargin = 10;
			tr1params2.weight = 1;
			tr1t2.setLayoutParams(tr1params2);
			tr1t2.setText(getResources().getString(R.string.vacation_type));
			tr1t2.setTextSize(tableTxtSize);
//			tr1t2.setGravity(Gravity.RIGHT);

			tr1.addView(tr1t1);
			tr1.addView(tr1t2);

			TableRow tr3 = new TableRow(this);
			tr3.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.WRAP_CONTENT));
			tr3.setBackgroundColor(Color.parseColor("#5B83B6"));

			TextView tr3t1 = new TextView(this);
			TableRow.LayoutParams tr3params1 = new TableRow.LayoutParams(0,
					LayoutParams.WRAP_CONTENT);
			tr3params1.weight = 1;
			tr3params1.width = LayoutParams.MATCH_PARENT;
			tr3params1.height = LayoutParams.WRAP_CONTENT;
			tr3params1.leftMargin = 10;
			tr3t1.setLayoutParams(tr3params1);
			tr3t1.setText(dateFormatForDate(vacationResponseBeansList.get(i).getLeaveStartDate()));
			tr3t1.setTextColor(Color.parseColor("#003E94"));
			tr3t1.setPadding(5, 5, 5, 5);
			tr3t1.setTextSize(tableTxtSize);
//			 tr3t1.setGravity(Gravity.RIGHT);

			TextView tr3t2 = new TextView(this);
			TableRow.LayoutParams tr3params2 = new TableRow.LayoutParams(0,
					LayoutParams.WRAP_CONTENT);
			tr3params2.width = LayoutParams.MATCH_PARENT;
			tr3params2.height = LayoutParams.WRAP_CONTENT;
			tr3params2.rightMargin = 10;
			tr3params2.weight = 1;
			tr3t2.setLayoutParams(tr3params2);
			tr3t2.setText(getResources().getString(R.string.start_date));
			tr3t2.setTextSize(tableTxtSize);
//			tr3t2.setGravity(Gravity.RIGHT);

			tr3.addView(tr3t1);
			tr3.addView(tr3t2);

			TableRow tr4 = new TableRow(this);
			tr4.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.WRAP_CONTENT));
			tr4.setBackgroundColor(Color.parseColor("#D7DFEA"));

			TextView tr4t1 = new TextView(this);
			TableRow.LayoutParams tr4params1 = new TableRow.LayoutParams(0,
					LayoutParams.WRAP_CONTENT);
			tr4params1.weight = 1;
			tr4params1.width = LayoutParams.MATCH_PARENT;
			tr4params1.height = LayoutParams.WRAP_CONTENT;
			tr4params1.leftMargin = 10;
			tr4t1.setLayoutParams(tr4params1);
			tr4t1.setText(dateFormatForDate(vacationResponseBeansList.get(i).getLeaveEndDate()));
			tr4t1.setTextColor(Color.parseColor("#003E94"));
			tr4t1.setPadding(5, 5, 5, 5);
			tr4t1.setTextSize(tableTxtSize);
//			 tr4t1.setGravity(Gravity.RIGHT);

			TextView tr4t2 = new TextView(this);
			TableRow.LayoutParams tr4params2 = new TableRow.LayoutParams(0,
					LayoutParams.WRAP_CONTENT);
			tr4params2.width = LayoutParams.MATCH_PARENT;
			tr4params2.height = LayoutParams.WRAP_CONTENT;
			tr4params2.rightMargin = 10;
			tr4params2.weight = 1;
			tr4t2.setLayoutParams(tr4params2);
			tr4t2.setText(getResources().getString(R.string.end_date));
			tr4t2.setTextSize(tableTxtSize);
//			tr4t2.setGravity(Gravity.RIGHT);

			tr4.addView(tr4t1);
			tr4.addView(tr4t2);


			TableRow tr5 = new TableRow(this);
			tr5.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.WRAP_CONTENT));
			tr5.setBackgroundColor(Color.parseColor("#5B83B6"));

			TextView tr5t1 = new TextView(this);
			TableRow.LayoutParams tr5params1 = new TableRow.LayoutParams(0,
					LayoutParams.WRAP_CONTENT);
			tr5params1.weight = 1;
			tr5params1.width = 0;;
			tr5params1.height = LayoutParams.WRAP_CONTENT;
			tr5params1.leftMargin = 10;
			tr5t1.setLayoutParams(tr5params1);
			tr5t1.setText(vacationResponseBeansList.get(i).getLeaveReqStatusDescAr());
			tr5t1.setTextColor(Color.parseColor("#003E94"));
			tr5t1.setTextSize(15f);
			tr5t1.setPadding(5, 5, 5, 5);
			tr5t1.setGravity(Gravity.LEFT);

			TextView tr5t2 = new TextView(this);
			TableRow.LayoutParams tr5params2 = new TableRow.LayoutParams(0,
					LayoutParams.WRAP_CONTENT);
			tr5params2.width = 0;
			tr5params2.height = LayoutParams.WRAP_CONTENT;
			tr5params2.rightMargin = 10;
			tr5params2.weight = 1;
			tr5t2.setLayoutParams(tr5params2);
			tr5t2.setText(getResources().getString(R.string.vacation_status));
			tr5t2.setTextSize(tableTxtSize);
//			tr5t2.setGravity(Gravity.RIGHT);

			tr5.addView(tr5t1);
			tr5.addView(tr5t2);

			tbl.addView(tr);
			tbl.addView(tr1);
			tbl.addView(tr3);
			tbl.addView(tr4);
			tbl.addView(tr5);

			lLayout.addView(tbl);
			vflipper.addView(lLayout);
		}

//		setUpAttachmentTable(data.get(vflipper.getDisplayedChild()));
		setUpAttachmentTable();
		currentPage.setText((vflipper.getDisplayedChild()+1)+"-"+vflipper.getChildCount());

	}


	
	public void setUpAttachmentTable() {
		
		mainLinear = (LinearLayout)findViewById(R.id.mainLinear);
		mainLinear.removeAllViews();
		LayoutInflater layoutInflaterForButton = this.getLayoutInflater();
		
		ArrayList<AttachmentBean> attachmentBeansList = vacationResponseBeansList.get(vflipper.getDisplayedChild()).getAttachmentBeanList();
		
		for (int i = 0; i < attachmentBeansList.size(); i++) {
			LinearLayout btnView = (LinearLayout) layoutInflaterForButton.inflate(R.layout.layout_vacation, null);

			if(i%2 != 0)
				btnView.setBackgroundColor(Color.parseColor("#5B83B6"));
			
			TextView statusTxt = (TextView) btnView.findViewById(R.id.statusTxt);
			TextView organizationalTxt = (TextView) btnView.findViewById(R.id.organizationalTxt);
			TextView resTxt = (TextView) btnView.findViewById(R.id.resTxt);
			
			if (MySharedPref.getLanguage(FragmentVacation.this).equals(
					MyCommon.LANGUAGE_ENG)) {
				if(attachmentBeansList.get(i).getEmpAppStatusDescEn()!=null && !attachmentBeansList.get(i).getEmpAppStatusDescEn().equalsIgnoreCase("null")){
					statusTxt.setText(attachmentBeansList.get(i).getEmpAppStatusDescEn());
				}else{
					statusTxt.setText("");
				}
			}else{
				if(!attachmentBeansList.get(i).getEmpAppStatusDescAr().equalsIgnoreCase("null")){
					statusTxt.setText(attachmentBeansList.get(i).getEmpAppStatusDescAr());
				}else{
					statusTxt.setText("");
				}
			}
			
			
			if (MySharedPref.getLanguage(FragmentVacation.this).equals(
					MyCommon.LANGUAGE_ENG)) {
				if(!attachmentBeansList.get(i).getEmpLeaveReqAppEmpNameEn().equalsIgnoreCase("null")){
					resTxt.setText(attachmentBeansList.get(i).getEmpLeaveReqAppEmpNameEn());
				}else{
					resTxt.setText("");
				}
			}else{
				if(!attachmentBeansList.get(i).getEmpLeaveReqAppEmpNameAr().equalsIgnoreCase("null")){
					resTxt.setText(attachmentBeansList.get(i).getEmpLeaveReqAppEmpNameAr());
				}else{
					resTxt.setText("");
				}
			}
			
			if (MySharedPref.getLanguage(FragmentVacation.this).equals(
					MyCommon.LANGUAGE_ENG)) {
				if(!attachmentBeansList.get(i).getEmpLeaveReqAppEntNameEn().equalsIgnoreCase("null")){
					organizationalTxt.setText(attachmentBeansList.get(i).getEmpLeaveReqAppEntNameEn());
				}else{
					organizationalTxt.setText("");
				}
			}else{
				if(!attachmentBeansList.get(i).getEmpLeaveReqAppEntNameAr().equalsIgnoreCase("null")){
					organizationalTxt.setText(attachmentBeansList.get(i).getEmpLeaveReqAppEntNameAr());
				}else{
					organizationalTxt.setText("");
				}
			}
			
			mainLinear.addView(btnView);
			
		}	
}

}
