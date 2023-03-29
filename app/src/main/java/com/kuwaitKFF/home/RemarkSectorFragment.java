package com.kuwaitKFF.home;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;


import androidx.core.view.GestureDetectorCompat;

import com.kuwaitKFF.R;
import com.kuwaitKFF.base.BaseActivity;
import com.kuwaitKFF.common.MyCommon;
import com.kuwaitKFF.common.MyHttpConnection;
import com.kuwaitKFF.common.MySharedPref;
import com.kuwaitKFF.home.adapter.RemarkAdapter;
import com.kuwaitKFF.main.Kfsd;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class RemarkSectorFragment extends BaseActivity implements OnClickListener {

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
	ListView vflipper;
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

	Animation animFlipInForeward;
	Animation animFlipOutForeward;
	Animation animFlipInBackward;
	Animation animFlipOutBackward;
	ArrayList<RemarkSectorResponseBean> remarkSectorResponseBeansList;
	
	Date dateStart,dateEnd;

	public RemarkSectorFragment() {
		super(R.layout.fragment_credit_sector);

	}

	@Override
	public void initialization() {
		ButterKnife.bind(this);

	}

	@Override
	public void implementation() {
		kfsd = (Kfsd) getApplication();
		

		vflipper = (ListView) findViewById(R.id.mainFlipper);

//		vflipper.setOnTouchListener(new OnTouchListener() {
//
//			@Override
//			public boolean onTouch(View v, MotionEvent event) {
//				// TODO Auto-generated method stub
//				if (event.getAction() == MotionEvent.ACTION_MOVE) {
//					return gestureDetector.onTouchEvent(event);
//				} else {
//
//					gestureDetector.onTouchEvent(event);
//
//				}
//
//				return true;
//			}
//		});


		animFlipInForeward = AnimationUtils.loadAnimation(this, R.anim.flipin);
		animFlipOutForeward = AnimationUtils
				.loadAnimation(this, R.anim.flipout);
		animFlipInBackward = AnimationUtils.loadAnimation(this,
				R.anim.flipin_reverse);
		animFlipOutBackward = AnimationUtils.loadAnimation(this,
				R.anim.flipout_reverse);

		setHeader(getResources().getString(R.string.link2), true, true);
		clickedSubmit();

	}

	public void clickedSubmit() {
		
				if (kfsd.checkNetworkRechability())
				callWebService();
			else
				Show_No_Internet();

				

	}

	public void callWebService() {

		Bundle bundle = getIntent().getExtras();

		//Extract the data…
		String mycvalue = bundle.getString("mycvalue"); 
		RequestParams params = new RequestParams(MyCommon.WS_PARA_SERIALID,
				"" + mycvalue); 
       MyHttpConnection.get(MyCommon.WS_METHOD_REMARK_SECTOR, params,
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
				}else
				{

					JSONArray jsonArray = json.getJSONArray("KfsdMobileNoteReq");

					Log.d("MzTagREMARKS", jsonArray.toString());

					remarkSectorResponseBeansList = new ArrayList<RemarkSectorResponseBean>();

					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject jsonObject = jsonArray.getJSONObject(i);
						RemarkSectorResponseBean remarkSectorResponseBean = new RemarkSectorResponseBean();
						remarkSectorResponseBean.setReqSerial(jsonObject
								.getString("NoteReqSerial"));
						remarkSectorResponseBean.setRouteSerial(jsonObject
								.getString("NoteRouteSerial"));
						remarkSectorResponseBean.setOrgId(jsonObject
								.getString("NoteOrgId"));
						remarkSectorResponseBean.setNoteCode(jsonObject
								.getString("NoteCode"));
						remarkSectorResponseBean.setNoteDescAr(jsonObject
								.getString("NoteDescAr"));
						remarkSectorResponseBean.setNoteDescEn(jsonObject
								.getString("NoteDescEn"));
						remarkSectorResponseBean.setNoteExecDate(jsonObject
								.getString("NoteExecuteDate"));
						remarkSectorResponseBean.setNoteOrgNameAr(jsonObject
								.getString("NoteOrgNameAr"));
						remarkSectorResponseBean.setNoteOrgNameEn(jsonObject
								.getString("NoteOrgNameEn"));

						remarkSectorResponseBeansList.add(remarkSectorResponseBean);

					}
					//RemarkSectorResponseBean[] array = new RemarkSectorResponseBean[remarkSectorResponseBeansList.size()];
					//System.out.println("remarkSectorResponseBeansList "+remarkSectorResponseBeansList.size());
					RemarkAdapter adapter = new RemarkAdapter(getApplicationContext(), R.layout.row_credit_sector, MySharedPref.getLanguage(RemarkSectorFragment.this));

					if(remarkSectorResponseBeansList != null && !remarkSectorResponseBeansList.isEmpty())
					{
						adapter.addAll(remarkSectorResponseBeansList);
					}
					else
					{
						adapter.add(new RemarkSectorResponseBean());
					}
					vflipper.setAdapter(adapter);

//				if (MySharedPref.getLanguage(RemarkSectorFragment.this).equals(
//						MyCommon.LANGUAGE_ENG)) {
//					setUpTableNew(remarkSectorResponseBeansList);
//
//				} else {
//					setUpTableForArabic(remarkSectorResponseBeansList);
//				}

					//vflipper.setVisibility(View.VISIBLE);

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

//	private void SwipeRight() {
//		vflipper.setInAnimation(animFlipInBackward);
//		vflipper.setOutAnimation(animFlipOutBackward);
//		vflipper.showPrevious();
//	}
//
//	private void SwipeLeft() {
//		vflipper.setInAnimation(animFlipInForeward);
//		vflipper.setOutAnimation(animFlipOutForeward);
//		vflipper.showNext();
//	}

//	@Override
//	public boolean onTouchEvent(MotionEvent event) {
//		// TODO Auto-generated method stub
//		return gestureDetector.onTouchEvent(event);
//	}

//	SimpleOnGestureListener simpleOnGestureListener = new SimpleOnGestureListener() {
//
//		@Override
//		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
//				float velocityY) {
//
//			float sensitvity = 50;
//			if ((e1.getX() - e2.getX()) > sensitvity) {
//				SwipeLeft();
//			} else if ((e2.getX() - e1.getX()) > sensitvity) {
//				SwipeRight();
//			}
//
//			return true;
//		}
//
//	};

//	GestureDetector gestureDetector = new GestureDetector(
//			simpleOnGestureListener);

//	public void clickedEndDateDialog(View view) {
//
//		dialogCaldroidFragment = new CaldroidFragment();
//		dialogCaldroidFragment.setCaldroidListener(endDateSelectListener);
//
//		// If activity is recovered from rotation
//		final String dialogTag = "CALDROID_DIALOG_FRAGMENT";
//		// Setup arguments
//		Bundle bundle = new Bundle();
//		// Setup dialogTitle
//		bundle.putString(CaldroidFragment.DIALOG_TITLE, "Select a date");
//
//		Calendar calendar = Calendar.getInstance();
//		// dialogCaldroidFragment.setMinDate(calendar.getTime());
//		dialogCaldroidFragment.setArguments(bundle);
//
//		dialogCaldroidFragment.show(getSupportFragmentManager(), dialogTag);
//	}

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

//	public void clickedDateDialog(View view) {
//
//		dialogCaldroidFragment = new CaldroidFragment();
//		dialogCaldroidFragment.setCaldroidListener(dateSelectListener);
//
//		// If activity is recovered from rotation
//		final String dialogTag = "CALDROID_DIALOG_FRAGMENT";
//		// Setup arguments
//		Bundle bundle = new Bundle();
//		// Setup dialogTitle
//		bundle.putString(CaldroidFragment.DIALOG_TITLE, "Select a date");
//
//		Calendar calendar = Calendar.getInstance();
//		// dialogCaldroidFragment.setMinDate(calendar.getTime());
//		dialogCaldroidFragment.setArguments(bundle);
//
//		dialogCaldroidFragment.show(getSupportFragmentManager(), dialogTag);
//	}

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

	private void setUpTableNew(ArrayList<RemarkSectorResponseBean> remarkSectorResponseBeansList) {
		for (int i = 0; i < remarkSectorResponseBeansList.size(); i++) {

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
			t1.setText(getResources().getString(R.string.serial_num));
			t1.setTextColor(Color.parseColor("#003E94"));
			t1.setPadding(5, 5, 5, 5);
			// t1.setGravity(Gravity.CENTER);

			TextView t2 = new TextView(this);
			TableRow.LayoutParams params2 = new TableRow.LayoutParams(0,
					LayoutParams.WRAP_CONTENT);
			params2.width = LayoutParams.MATCH_PARENT;
			params2.height = LayoutParams.WRAP_CONTENT;

			params2.weight = 1;
			t2.setLayoutParams(params2);
			t2.setText(remarkSectorResponseBeansList.get(i).getReqSerial());
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
			tr1t1.setText(getResources().getString(R.string.remark));
			tr1t1.setTextColor(Color.parseColor("#003E94"));
			tr1t1.setPadding(5, 5, 5, 5);
			// tr1t1.setGravity(Gravity.CENTER);

			TextView tr1t2 = new TextView(this);
			TableRow.LayoutParams tr1params2 = new TableRow.LayoutParams(0,
					LayoutParams.WRAP_CONTENT);
			tr1params2.width = LayoutParams.MATCH_PARENT;
			tr1params2.height = LayoutParams.WRAP_CONTENT;

			tr1params2.weight = 1;
			tr1t2.setLayoutParams(tr1params2);
			tr1t2.setText(remarkSectorResponseBeansList.get(i).getNoteDescEn());
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
			tr3t1.setText(getResources().getString(R.string.remark_date));
			tr3t1.setTextColor(Color.parseColor("#003E94"));
			tr3t1.setPadding(5, 5, 5, 5);
			// tr3t1.setGravity(Gravity.CENTER);

			TextView tr3t2 = new TextView(this);
			TableRow.LayoutParams tr3params2 = new TableRow.LayoutParams(0,
					LayoutParams.WRAP_CONTENT);
			tr3params2.width = LayoutParams.MATCH_PARENT;
			tr3params2.height = LayoutParams.WRAP_CONTENT;

			tr3params2.weight = 1;
			tr3t2.setLayoutParams(tr3params2);
			tr3t2.setText(remarkSectorResponseBeansList.get(i).getNoteExecDate());
			// tr3t2.setGravity(Gravity.CENTER);

			tr3.addView(tr3t1);
			tr3.addView(tr3t2);

		    tbl.addView(tr);
			tbl.addView(tr1);
			tbl.addView(tr3);
			

			lLayout.addView(tbl);

			vflipper.addView(lLayout);

		}

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

		default:
			break;
		}

	}

	private void setUpTableForArabic(ArrayList<RemarkSectorResponseBean> remarkSectorResponseBeansList) {
		for (int i = 0; i < remarkSectorResponseBeansList.size(); i++) {
	
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
			t1.setText(remarkSectorResponseBeansList.get(i).getReqSerial());
			t1.setTextColor(Color.parseColor("#003E94"));
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
			t2.setText(getResources().getString(R.string.serial_num));
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
			tr1t1.setText(remarkSectorResponseBeansList.get(i).getNoteDescAr());
			tr1t1.setTextColor(Color.parseColor("#003E94"));
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
			tr1t2.setText(getResources().getString(R.string.remark));
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
			tr3t1.setText(remarkSectorResponseBeansList.get(i).getNoteExecDate());
			tr3t1.setTextColor(Color.parseColor("#003E94"));
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
			tr3t2.setText(getResources().getString(R.string.remark_date));
			tr3t2.setGravity(Gravity.RIGHT);
	
			tr3.addView(tr3t1);
			tr3.addView(tr3t2);
	
			tbl.addView(tr);
			tbl.addView(tr1);
			tbl.addView(tr3);
		
	
			lLayout.addView(tbl);
			vflipper.addView(lLayout);
		}
	
	}

}
