package com.kuwaitKFF.task;

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
import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
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

@SuppressLint("SimpleDateFormat")
public class FragmentTask extends BaseActivity implements OnClickListener {

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
	LinearLayout mainLinear;
	ViewFlipper vflipper;
	LinearLayout lLayout;
	TextView tv;
	TableLayout tbl;
	Button prev;
	Button next;

	View v0;
	View v1;
	TextView title;

	ArrayList<String> data;
	private float initialX;
	private GestureDetectorCompat mDetector;

	Animation slide_in_left, slide_out_right;
	Animation slide_in_right, slide_out_left;

	TextView startDate, endDate;
	private CaldroidFragment dialogCaldroidFragment;
	SimpleDateFormat formatter;
	SimpleDateFormat formatterForDisplay;
	Date date;
	String formattedDate = null;
	String dateForSendingToServer = "";



	RelativeLayout bottom;
	float tableTxtSize = 17f;

	Animation animFlipInForeward;
	Animation animFlipOutForeward;
	Animation animFlipInBackward;
	Animation animFlipOutBackward;
	ArrayList<TaskResponseBean> taskResponseBeansList;
	TextView currentPage;
	Date dateStart,dateEnd;

	public FragmentTask() {
		super(R.layout.fragment_permission);

	}

	@Override
	public void initialization() {
		ButterKnife.bind(this);

	}

	@Override
	public void implementation() {
		kfsd = (Kfsd) getApplication();
		

		title = (TextView)findViewById(R.id.titleText);
		title.setText(getResources().getString(R.string.requested_task));

		formatter = new SimpleDateFormat("MM/dd/yyyy");
		

		if (MySharedPref.getLanguage(FragmentTask.this).equals(
				MyCommon.LANGUAGE_ENG)) {
		formatterForDisplay = new SimpleDateFormat("dd-MM-yyyy");
		}
		else
		{
			formatterForDisplay = new SimpleDateFormat("yyyy-MM-dd");
		}

		submitButton = (Button) findViewById(R.id.submitButton);
		submitButton.setOnClickListener(this);

		currentPage = (TextView) findViewById(R.id.currentPage);
		currentPage.setOnClickListener(this);
		
//		prev = (Button) findViewById(R.id.prevButton);
//		prev.setOnClickListener(this);
//
//		next = (Button) findViewById(R.id.nextButton);
//		next.setOnClickListener(this);

//		bottom = (RelativeLayout) findViewById(R.id.bottomLayout);

		vflipper = (ViewFlipper) findViewById(R.id.mainFlipper);

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

		startDate = (TextView) findViewById(R.id.startDateText);
		startDate.setOnClickListener(this);

		endDate = (TextView) findViewById(R.id.endDateText);
		endDate.setOnClickListener(this);

		animFlipInForeward = AnimationUtils.loadAnimation(this, R.anim.flipin);
		animFlipOutForeward = AnimationUtils
				.loadAnimation(this, R.anim.flipout);
		animFlipInBackward = AnimationUtils.loadAnimation(this,
				R.anim.flipin_reverse);
		animFlipOutBackward = AnimationUtils.loadAnimation(this,
				R.anim.flipout_reverse);

		if (MySharedPref.getLanguage(FragmentTask.this).equals(
				MyCommon.LANGUAGE_ENG)) {
			setHeader(getResources().getString(R.string.welcome)+" "+MyCommon.sLoginResponseBean.getNameEn(), true,true,true);
		}else
		{
			setHeader(getResources().getString(R.string.welcome)+" "+MyCommon.sLoginResponseBean.getNameAr(), true,true,true);
		}
		

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

		if (MySharedPref.getLanguage(FragmentTask.this).equals(
				MyCommon.LANGUAGE_ENG))
		{
			RequestParams params = new RequestParams(MyCommon.WS_PARA_START_DATE,
					"" + startDate.getText().toString()); // / startDate.getText().toString()
			params.put(MyCommon.WS_PARA_END_DATE, "" + endDate.getText().toString()); // endDate.getText().toString()

			params.put(MyCommon.WS_PARA_CIVILID, "" + MyCommon.sLoginResponseBean.getCivilId());

			MyHttpConnection.get(MyCommon.WS_METHOD_TASK, params,
					asyncLoginResponseHandler);
		}
		else
		{
			RequestParams params = new RequestParams(MyCommon.WS_PARA_START_DATE,
					"" + dateFormatForDate1(startDate.getText().toString())); // / startDate.getText().toString()
			params.put(MyCommon.WS_PARA_END_DATE, "" + dateFormatForDate1(endDate.getText().toString())); // endDate.getText().toString()

			params.put(MyCommon.WS_PARA_CIVILID, "" + MyCommon.sLoginResponseBean.getCivilId());

			MyHttpConnection.get(MyCommon.WS_METHOD_TASK, params,
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
			Log.d("MzTag", String.valueOf(responseBody));

			vflipper.removeAllViewsInLayout();
			vflipper.setVisibility(View.INVISIBLE);
			// response = MyCommon.getJsonFromWebServiceResponse(response);
			try {
				JSONObject json = null;
				try {
					json = new JSONObject(new String(responseBody, "UTF-8"));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				int result = json.getInt("success");
				if(result==0)
				{
					Show_Alert_Dialog(getResources().getString(R.string.No_data_available));
					currentPage.setVisibility(View.INVISIBLE);
				}else
				{



					JSONArray jsonArray = json.getJSONArray("KFSDMobileExcept");
					taskResponseBeansList = new ArrayList<TaskResponseBean>();

					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject jsonObject = jsonArray.getJSONObject(i);
						TaskResponseBean taskResponseBean = new TaskResponseBean();
						taskResponseBean.setEmpCivilId(jsonObject
								.getString("EmpCivilId"));
						taskResponseBean.setEmpTaskCode(jsonObject
								.getString("EmpExceptCode"));
						taskResponseBean.setEmpTaskDescAr(jsonObject
								.getString("EmpExceptDescAr"));
						taskResponseBean.setEmpTaskDescEn(jsonObject
								.getString("EmpExceptDescEn"));
						taskResponseBean.setEmpTaskReasonCode(jsonObject
								.getString("EmpExceptReasonCode"));
						taskResponseBean.setEmpTaskReasonDescAr(jsonObject
								.getString("EmpExceptReasonDescAr"));
						taskResponseBean.setEmpTaskReasonDescEn(jsonObject
								.getString("EmpExceptReasonDescEn"));
						taskResponseBean.setEmpTaskSerial(jsonObject
								.getString("EmpExceptSerial"));

						taskResponseBean.setEndDate(dateFormat(jsonObject
								.getString("endDate")));
						taskResponseBean.setStartDate(dateFormat(jsonObject
								.getString("startDate")));

						taskResponseBeansList.add(taskResponseBean);

					}


					//				//sort Ascending
					Collections.sort(taskResponseBeansList, new Comparator() {

						public int compare(Object o1, Object o2) {
							TaskResponseBean p1 = (TaskResponseBean) o1;
							TaskResponseBean p2 = (TaskResponseBean) o2;
							return p1.getStartDate().compareToIgnoreCase(p2.getStartDate());
						}
					});

					//Reverse to get descending
					Collections.reverse(taskResponseBeansList);

					if (MySharedPref.getLanguage(FragmentTask.this).equals(MyCommon.LANGUAGE_ENG)) {
						setUpTableNew(taskResponseBeansList);
					} else {
						setUpTableForArabic(taskResponseBeansList);
					}

					vflipper.setVisibility(View.VISIBLE);
					currentPage.setVisibility(View.VISIBLE);
//				bottom.setVisibility(View.VISIBLE);
				}

			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
			vflipper.removeAllViewsInLayout();
			vflipper.setVisibility(View.INVISIBLE);
			Show_Alert_Dialog(R.string.M_Unable_To_Fetch_Data);
			currentPage.setVisibility(View.INVISIBLE);
		}

		@Override
		public void onStart() {
			DisplayProgress();
			super.onStart();
		}


	};

	public String dateFormat(String dateVal) {

		//
		// final String[] formats = {
		// "yyyy-MM-dd'T'HH:mm:ss'Z'", "yyyy-MM-dd'T'HH:mm:ssZ",
		// "yyyy-MM-dd'T'HH:mm:ss", "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
		// "yyyy-MM-dd'T'HH:mm:ss.SSSZ", "yyyy-MM-dd HH:mm:ss",
		// "MM/dd/yyyy HH:mm:ss", "MM/dd/yyyy'T'HH:mm:ss.SSS'Z'",
		// "MM/dd/yyyy'T'HH:mm:ss.SSSZ", "MM/dd/yyyy'T'HH:mm:ss.SSS",
		// "MM/dd/yyyy'T'HH:mm:ssZ", "MM/dd/yyyy'T'HH:mm:ss",
		// "yyyy:MM:dd HH:mm:ss", "yyyyMMdd", };
		//
		//
		//
		// for (String parse : formats) {
		// SimpleDateFormat sdf = new SimpleDateFormat(parse);
		//
		// try {
		// sdf.parse(dateVal);
		// System.out.println("Printing the value of " + parse);
		// } catch (ParseException e) {
		//
		// }
		// //System.out.println("It should be parsed as --------"+parse);
		//
		//
		// }

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

	private void SwipeRight() {
		vflipper.setInAnimation(animFlipInBackward);
		vflipper.setOutAnimation(animFlipOutBackward);
		vflipper.showPrevious();

		currentPage.setText((vflipper.getDisplayedChild()+1)+"-"+vflipper.getChildCount());
	}

	private void SwipeLeft() {
		vflipper.setInAnimation(animFlipInForeward);
		vflipper.setOutAnimation(animFlipOutForeward);
		vflipper.showNext();

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
			} else if ((e2.getX() - e1.getX()) > sensitvity) {
				SwipeRight();
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

	private void setUpTableNew(ArrayList<TaskResponseBean> taskResponseBeansList) {
		for (int i = 0; i < taskResponseBeansList.size(); i++) {

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
			params1.width = LayoutParams.MATCH_PARENT;
			params1.height = LayoutParams.WRAP_CONTENT;
			params1.leftMargin = 10;
			t1.setLayoutParams(params1);
			t1.setText(getResources().getString(R.string.task1));
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
			t2.setText(taskResponseBeansList.get(i).getEmpTaskDescEn());
			t2.setTextSize(tableTxtSize);

			// t1.setGravity(Gravity.CENTER);

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
			tr1t1.setText(getResources().getString(R.string.task2));
			tr1t1.setTextColor(Color.parseColor("#003E94"));
			tr1t1.setTextSize(tableTxtSize);
			tr1t1.setPadding(5, 5, 5, 5);
			// tr1t1.setGravity(Gravity.CENTER);

			TextView tr1t2 = new TextView(this);
			TableRow.LayoutParams tr1params2 = new TableRow.LayoutParams(0,
					LayoutParams.WRAP_CONTENT);
			tr1params2.width = LayoutParams.MATCH_PARENT;
			tr1params2.height = LayoutParams.WRAP_CONTENT;

			tr1params2.weight = 1;
			tr1t2.setLayoutParams(tr1params2);
			tr1t2.setText(taskResponseBeansList.get(i).getEmpTaskReasonDescEn());
			tr1t2.setTextSize(tableTxtSize);
			// tr1t2.setGravity(Gravity.CENTER);

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
			tr3t1.setText(getResources().getString(R.string.from));
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
			tr3t2.setText(taskResponseBeansList.get(i).getStartDate());
			tr3t2.setTextSize(tableTxtSize);
			// tr3t2.setGravity(Gravity.CENTER);

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
			tr4t1.setText(getResources().getString(R.string.to_date));
			tr4t1.setTextColor(Color.parseColor("#003E94"));
			tr4t1.setTextSize(tableTxtSize);
			tr4t1.setPadding(5, 5, 5, 5);
			// tr4t1.setGravity(Gravity.CENTER);

			TextView tr4t2 = new TextView(this);
			TableRow.LayoutParams tr4params2 = new TableRow.LayoutParams(0,
					LayoutParams.WRAP_CONTENT);
			tr4params2.width = LayoutParams.MATCH_PARENT;
			tr4params2.height = LayoutParams.WRAP_CONTENT;

			tr4params2.weight = 1;
			tr4t2.setLayoutParams(tr4params2);
			tr4t2.setText(taskResponseBeansList.get(i).getEndDate());
			tr4t2.setTextSize(tableTxtSize);
			// tr4t2.setGravity(Gravity.CENTER);

			tr4.addView(tr4t1);
			tr4.addView(tr4t2);

			tbl.addView(tr);
			tbl.addView(tr1);
			tbl.addView(tr3);
			tbl.addView(tr4);

			lLayout.addView(tbl);

			vflipper.addView(lLayout);

		}

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

	private void setUpTableForArabic(ArrayList<TaskResponseBean> taskResponseBeansList) {
		for (int i = 0; i < taskResponseBeansList.size(); i++) {

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
			params1.width = LayoutParams.MATCH_PARENT;
			params1.height = LayoutParams.WRAP_CONTENT;
			params1.leftMargin = 10;
			t1.setLayoutParams(params1);
			t1.setText(taskResponseBeansList.get(i).getEmpTaskDescAr());
			t1.setTextColor(Color.parseColor("#003E94"));
			t1.setTextSize(tableTxtSize);
			t1.setPadding(5, 5, 5, 5);
			t1.setGravity(Gravity.LEFT);

			TextView t2 = new TextView(this);
			TableRow.LayoutParams params2 = new TableRow.LayoutParams(0,
					LayoutParams.WRAP_CONTENT);
			params2.width = LayoutParams.MATCH_PARENT;
			params2.height = LayoutParams.WRAP_CONTENT;

			params2.weight = 1;
			params2.rightMargin = 10;
			t2.setLayoutParams(params2);
			t2.setText(getResources().getString(R.string.task1));
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
			tr1t1.setText(taskResponseBeansList.get(i).getEmpTaskReasonDescAr());
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
			tr1t2.setText(getResources().getString(R.string.task2));
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
			tr3t1.setText(taskResponseBeansList.get(i).getStartDate());
			tr3t1.setTextColor(Color.parseColor("#003E94"));
			tr3t1.setTextSize(tableTxtSize);
			tr3t1.setPadding(5, 5, 5, 5);
			// tr3t1.setGravity(Gravity.CENTER);

			TextView tr3t2 = new TextView(this);
			TableRow.LayoutParams tr3params2 = new TableRow.LayoutParams(0,
					LayoutParams.WRAP_CONTENT);
			tr3params2.width = LayoutParams.MATCH_PARENT;
			tr3params2.height = LayoutParams.WRAP_CONTENT;
			tr3params2.rightMargin = 10;
			tr3params2.weight = 1;
			tr3t2.setLayoutParams(tr3params2);
			tr3t2.setText(getResources().getString(R.string.from));
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
			tr4t1.setText(taskResponseBeansList.get(i).getEndDate());
			tr4t1.setTextColor(Color.parseColor("#003E94"));
			tr4t1.setTextSize(tableTxtSize);
			tr4t1.setPadding(5, 5, 5, 5);
			// tr4t1.setGravity(Gravity.CENTER);

			TextView tr4t2 = new TextView(this);
			TableRow.LayoutParams tr4params2 = new TableRow.LayoutParams(0,
					LayoutParams.WRAP_CONTENT);
			tr4params2.width = LayoutParams.MATCH_PARENT;
			tr4params2.height = LayoutParams.WRAP_CONTENT;
			tr4params2.rightMargin = 10;
			tr4params2.weight = 1;
			tr4t2.setLayoutParams(tr4params2);
			tr4t2.setText(getResources().getString(R.string.to_date));
			tr4t2.setTextSize(tableTxtSize);
			tr4t2.setGravity(Gravity.RIGHT);

			tr4.addView(tr4t1);
			tr4.addView(tr4t2);

			tbl.addView(tr);
			tbl.addView(tr1);
			tbl.addView(tr3);
			tbl.addView(tr4);

			lLayout.addView(tbl);

			vflipper.addView(lLayout);

		}
		
		currentPage.setText((vflipper.getDisplayedChild()+1)+"-"+vflipper.getChildCount());

	}

}
