package com.kuwaitKFF.login;

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
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

@SuppressLint("SimpleDateFormat")
public class FragmentAttendance extends BaseActivity implements OnClickListener {

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
	Button submitButton1;
	
	TextView text, empText;
	LinearLayout mainLinear,main1Linear;
	LinearLayout lLayout;
	TextView tv, tvDay;
	TableLayout tbl;
	
	TextView startDate,endDate;
	private CaldroidFragment dialogCaldroidFragment;
	SimpleDateFormat formatter;
	SimpleDateFormat formatterForDisplay;
	String dateForSendingToServer = "";
	
	Date date;
	String formattedDate = null;
	

	View v0;
	View v1;
	
	Date dateStart,dateEnd;
	Spinner empList;
	boolean selectionControl=true;
	
	ArrayList<AttendanceResponseBean> attendanceResponseBeansList;
	ArrayList<EmployeeListResponseBean> employeeListResponseBeansList;
	String[] items;
	String selectedEmpId;
	TextView title;
	
	
	public FragmentAttendance() {
		super(R.layout.fragment_attendance);
	}

	@Override
	public void initialization() {
		ButterKnife.bind(this);
	}

	@Override
	public void implementation() {
		kfsd = (Kfsd)getApplication();
		
		submitButton = (Button)findViewById(R.id.submitButton);
		submitButton.setOnClickListener(this);
		
		title = (TextView)findViewById(R.id.titleText);
		title.setText(getResources().getString(R.string.month_balance_enquiry));

		startDate = (TextView)findViewById(R.id.startDateText);
		startDate.setOnClickListener(this);

		endDate = (TextView)findViewById(R.id.endDateText);
		endDate.setOnClickListener(this);
		
		empList = (Spinner)findViewById(R.id.editText1);
		empText = (TextView) findViewById(R.id.empText);

		if (kfsd.checkNetworkRechability())
			callEmpListWebService();
		else
			Show_No_Internet();

	

		
		formatter = new SimpleDateFormat("MM/dd/yyyy");
		if (MySharedPref.getLanguage(FragmentAttendance.this).equals(
				MyCommon.LANGUAGE_ENG)) {
			formatterForDisplay = new SimpleDateFormat("dd-MM-yyyy");
		}
		else
		{
			formatterForDisplay = new SimpleDateFormat("yyyy-MM-dd");
		}
		

		if (MySharedPref.getLanguage(FragmentAttendance.this).equals(
				MyCommon.LANGUAGE_ENG)) {
			setHeader(getResources().getString(R.string.welcome)+" "+MyCommon.sLoginResponseBean.getNameEn(), true,true,true);
		}else
		{
			setHeader(getResources().getString(R.string.welcome)+" "+MyCommon.sLoginResponseBean.getNameAr(), true,true,true);
		}
		



	}

		private void callEmpListWebService() {
			RequestParams params = new RequestParams(MyCommon.WS_PARA_CIVILID,
					"" + MyCommon.sLoginResponseBean.getCivilId()); //  startDate.getText().toString()

			MyHttpConnection.get(MyCommon.WS_METHOD_EMPLIST, params,
					asyncEmpListResponseHandler);

		}

		AsyncHttpResponseHandler asyncEmpListResponseHandler = new AsyncHttpResponseHandler() {

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
					}
					else
					{


						JSONArray jsonArray = json.getJSONArray("KfsdMobileMgrStaff");
						Log.d("MzTagAttendance", jsonArray.toString());
						employeeListResponseBeansList = new ArrayList<EmployeeListResponseBean>();

						for (int i = 0; i < jsonArray.length(); i++) {
							JSONObject jsonObject = jsonArray.getJSONObject(i);
							EmployeeListResponseBean employeeListResponseBean = new EmployeeListResponseBean();
							employeeListResponseBean.setCivilId(jsonObject.getString("StaffCivilId"));
							employeeListResponseBean.setFileNo(jsonObject.getString("StaffFileNo"));
							employeeListResponseBean.setEmpTypeCode(jsonObject.getString("StaffEmpTypeCode"));
							employeeListResponseBean.setEntityLevelCode(jsonObject.getString("StaffEntityLevelCode"));
							employeeListResponseBean.setManagerCivilId(jsonObject.getString("StaffManagerCivilId"));
							employeeListResponseBean.setManagerEntityId(jsonObject.getString("StaffManagerEntityId"));
							employeeListResponseBean.setNameAr(jsonObject.getString("StaffNameAr"));
							employeeListResponseBean.setNameEn(jsonObject.getString("StaffNameEn"));
							employeeListResponseBeansList.add(employeeListResponseBean);
						}

						setUpEmpListSinner();

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


		public void setUpTableForArabic(ArrayList<AttendanceResponseBean> attendanceResponseBeansList) {
		
		for(int i=0; i<attendanceResponseBeansList.size() ;i++)
			{
			mainLinear = (LinearLayout) findViewById(R.id.mainLinear);
			lLayout = new LinearLayout(this);
			lLayout.setOrientation(LinearLayout.HORIZONTAL);
//			lLayout.setId(1);

			
			tbl = new TableLayout(this);
			tbl.setLayoutParams(new LayoutParams(convertToPixel(280),
					LayoutParams.WRAP_CONTENT));

			TableRow tr = new TableRow(this);
			tr.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.WRAP_CONTENT));

			TextView t1 = new TextView(this);
			TableRow.LayoutParams params1 = new TableRow.LayoutParams(
					0, LayoutParams.WRAP_CONTENT);
			params1.weight = 1;
			params1.width = convertToPixel(0);
			params1.height = LayoutParams.WRAP_CONTENT;
			params1.gravity = Gravity.CENTER_VERTICAL;

			t1.setLayoutParams(params1);
			t1.setText(dateFormat(attendanceResponseBeansList.get(i).getEmpSignPmOutTime()));
			t1.setTypeface(null, Typeface.BOLD);
			//t1.setTextSize(12);
			//t1.setPadding(5, 5, 5, 5);
			t1.setGravity(Gravity.CENTER);


			v1 = new View(this);
			v1.setLayoutParams(new TableRow.LayoutParams(convertToPixel(1),
				convertToPixel(40)));
			v1.setBackgroundColor(Color.parseColor("#003E8F"));

			TextView t2 = new TextView(this);
			TableRow.LayoutParams params2 = new TableRow.LayoutParams(
					0, LayoutParams.WRAP_CONTENT);
			params2.width = convertToPixel(0);
			params2.height = LayoutParams.WRAP_CONTENT;
			params2.gravity = Gravity.CENTER_VERTICAL;

			params2.weight = 1;
			t2.setLayoutParams(params2);
			t2.setText(dateFormat(attendanceResponseBeansList.get(i).getEmpSignPmInTime()));
			t2.setTypeface(null, Typeface.BOLD);
			t2.setGravity(Gravity.CENTER);

			View v2 = new View(this);

			v2.setLayoutParams(new TableRow.LayoutParams(convertToPixel(1),
				convertToPixel(40)));
			v2.setBackgroundColor(Color.parseColor("#003E8F"));

			TextView t3 = new TextView(this);
			TableRow.LayoutParams params3 = new TableRow.LayoutParams(
					0, LayoutParams.WRAP_CONTENT);
			params3.width = convertToPixel(0);
			params3.height = LayoutParams.WRAP_CONTENT;
			params3.gravity = Gravity.CENTER_VERTICAL;

			params3.weight = 1;
			t3.setLayoutParams(params3);
			t3.setText(dateFormat(attendanceResponseBeansList.get(i).getEmpSignAmOutTime()));
			t3.setTypeface(null, Typeface.BOLD);
			t3.setGravity(Gravity.CENTER);

			View v3 = new View(this);

			v3.setLayoutParams(new TableRow.LayoutParams(convertToPixel(1),
				convertToPixel(40)));
			v3.setBackgroundColor(Color.BLACK);

			TextView t4 = new TextView(this);
			TableRow.LayoutParams params4 = new TableRow.LayoutParams(
					0, LayoutParams.WRAP_CONTENT);
			params4.width = convertToPixel(0);
			params4.height = LayoutParams.WRAP_CONTENT;
			params4.gravity = Gravity.CENTER_VERTICAL;

			params4.weight = 1;
			t4.setLayoutParams(params4);
			t4.setText(dateFormat(attendanceResponseBeansList.get(i).getEmpSignAmInTime()));
			t4.setTypeface(null, Typeface.BOLD);
			t4.setGravity(Gravity.CENTER);

			View v4 = new View(this);
			v4.setLayoutParams(new TableRow.LayoutParams(LayoutParams.MATCH_PARENT,
					convertToPixel(1)));
			v4.setBackgroundColor(Color.parseColor("#003E8F"));

			tr.addView(t1);
			

			tr.addView(v1);

			tr.addView(t2);
			tr.addView(v2);

			tr.addView(t3);
			tr.addView(v3);

			tr.addView(t4);

			TableRow tr1 = new TableRow(this);
			tr1.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.WRAP_CONTENT));

			TextView tr1t1 = new TextView(this);
			TableRow.LayoutParams tr1params1 = new TableRow.LayoutParams(
				0, LayoutParams.WRAP_CONTENT);
			tr1params1.width = LayoutParams.MATCH_PARENT;
			tr1params1.height = LayoutParams.MATCH_PARENT;
			tr1params1.gravity = Gravity.CENTER_VERTICAL;

			tr1params1.weight = 1;
			tr1t1.setLayoutParams(tr1params1);
			tr1t1.setText(attendanceResponseBeansList.get(i).getEmpDateNoteAr());
			tr1t1.setTextSize(17f);
			tr1t1.setTypeface(null, Typeface.BOLD);
			tr1t1.setPadding(5, 5, 5, 5);
			tr1t1.setGravity(Gravity.CENTER);

			View tr1v1 = new View(this);
			tr1v1.setLayoutParams(new TableRow.LayoutParams(convertToPixel(1),convertToPixel(37)));
			tr1v1.setBackgroundColor(Color.parseColor("#003E8F"));

			TextView tr1t2 = new TextView(this);
			TableRow.LayoutParams tr1params2 = new TableRow.LayoutParams(
					0, LayoutParams.WRAP_CONTENT);
			tr1params2.width = LayoutParams.MATCH_PARENT;
			tr1params2.height = LayoutParams.WRAP_CONTENT;

			tr1params2.weight = 1;
			tr1t2.setLayoutParams(tr1params2);
			tr1t2.setText(" ");
			tr1t2.setGravity(Gravity.CENTER);

			tr1.addView(tr1t1);
			
			tv = new TextView(this);
//			tv.setId(1);
			SimpleDateFormat formatter = new SimpleDateFormat(
					"yyyy-MM-dd'T'HH:mm:ss");
			Date date;
			String dayvalue;

			try 
			{
				date = formatter.parse(attendanceResponseBeansList.get(i).getEmpAttendanceDate());
				int ivalue = date.getDay();
				
				if(ivalue==0)
				{
					if (MySharedPref.getLanguage(FragmentAttendance.this).equals(
							MyCommon.LANGUAGE_ENG))
					{
						dayvalue="Sunday";
					}
					else
					{
						dayvalue="الأحد";
					}
						
				}
				else if(ivalue==1)
				{
					if (MySharedPref.getLanguage(FragmentAttendance.this).equals(
							MyCommon.LANGUAGE_ENG))
					{
						dayvalue="Monday";
					}
					else
					{
						dayvalue="الإثنين";
					}
				}
				else if(ivalue==2)
				{
					if (MySharedPref.getLanguage(FragmentAttendance.this).equals(
							MyCommon.LANGUAGE_ENG))
					{
						dayvalue="Tuesday";
					}
					else
					{
						dayvalue="الثلاثاء";
					}
				}
				else if(ivalue==3)
				{
					if (MySharedPref.getLanguage(FragmentAttendance.this).equals(
							MyCommon.LANGUAGE_ENG))
					{
						dayvalue="Wednesday";
					}
					else
					{
						dayvalue="الأربعاء";
					}
				}
				else if(ivalue==4)
				{
					if (MySharedPref.getLanguage(FragmentAttendance.this).equals(
							MyCommon.LANGUAGE_ENG))
					{
						dayvalue="Thursday";
					}
					else
					{
						dayvalue="الخميس";
					}
				}
				else if(ivalue==5)
				{
					if (MySharedPref.getLanguage(FragmentAttendance.this).equals(
							MyCommon.LANGUAGE_ENG))
					{
						dayvalue="Friday";
					}
					else
					{
						dayvalue="الجمعه";
					}
				}
				else
				{
					if (MySharedPref.getLanguage(FragmentAttendance.this).equals(
							MyCommon.LANGUAGE_ENG))
					{
						dayvalue="saturday";
					}
					else
					{
						dayvalue="السبت";
					}
				}
				tv.setTypeface(null, Typeface.BOLD);
				tv.setText("\n\n" + dateFormatForDate(attendanceResponseBeansList.get(i).getEmpAttendanceDate())+"\n\n"
						+dayvalue);
			} catch (ParseException e) 
			{
				e.printStackTrace();
			}
            tv.setTextSize(12);
			tv.setLayoutParams(new LayoutParams(convertToPixel(80),
					LayoutParams.WRAP_CONTENT));
		
			//tv.setPadding(5, 5, 5, 5);
			tv.setGravity(Gravity.CENTER);
			

			v0 = new View(this);
			v0.setLayoutParams(new LayoutParams(convertToPixel(2),
					convertToPixel(80)));
			v0.setBackgroundColor(Color.parseColor("#003E8F"));

			//tr1.addView(tr1v1);

			//tr1.addView(tr1t2);

			View v5 = new View(this);
			v5.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
					convertToPixel(1)));
			v5.setBackgroundColor(Color.parseColor("#003E8F"));

			tbl.addView(tr);
			tbl.addView(v4);
			tbl.addView(tr1);
			lLayout.addView(tbl);
			lLayout.addView(v0);
			lLayout.addView(tv);	
			mainLinear.addView(lLayout);
			mainLinear.addView(v5);
			}

		
	}
	
		
		protected void setUpEmpListSinner() {
          items = convertListToArray(employeeListResponseBeansList);
          for(EmployeeListResponseBean bean: employeeListResponseBeansList)
          {
        	  System.out.println("Rupa "+bean.getCivilId());
          }
			
		ArrayAdapter<String>	adapter = new ArrayAdapter<String>(
			        this,
				android.R.layout.simple_spinner_item,
			        items
			);
		
			empList.setSelected(true);
			empList.setSelection(1);
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
			empList.setAdapter(adapter);
			
			empList.setOnItemSelectedListener(new OnItemSelectedListener() {

				
				@Override
				public void onItemSelected(AdapterView<?> parent, View view,
						int position, long id) {

					((TextView)parent.getChildAt(0)).setTextColor(Color.rgb(52, 90, 152));
					((TextView)parent.getChildAt(0)).setTextSize(17f);
					((TextView)parent.getChildAt(0)).setGravity(Gravity.CENTER_HORIZONTAL);

					if(selectionControl)
					{
						selectionControl=false;
					}
					empText.setText(employeeListResponseBeansList.get(position).getFileNo());
//					else if(position ==0)
//					{
//						
//						
//							selectedEmpId = employeeListResponseBeansList.get(0).getCivilId();
//						
//					}else{
//					
//						System.out.println("imran"+position);
//						selectedEmpId = employeeListResponseBeansList.get(position-1).getCivilId();
//					}
					
				}

				@Override
				public void onNothingSelected(AdapterView<?> parent) {
					// TODO Auto-generated method stub
					
				}
			});
			
			
		}
		
		
		private String[] convertListToArray(
				ArrayList<EmployeeListResponseBean> employeeListResponseBeansList) {

			items = new String[employeeListResponseBeansList.size()];
			//items[0] = getResources().getString(R.string.M_Select_Employee_Id);
			for (int i = 0; i < employeeListResponseBeansList.size(); i++) {
				
				if (MySharedPref.getLanguage(FragmentAttendance.this).equals(
						MyCommon.LANGUAGE_ENG)) {
					items[i] =employeeListResponseBeansList.get(i).getNameEn();
				}else{
					items[i] =employeeListResponseBeansList.get(i).getNameAr();
				}
				
				
			}

			return items;
		}


		public String string(String dateVal) {

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
			SimpleDateFormat formatter1 = new SimpleDateFormat("HH:mm");

			try {
				date = formatter.parse(dateVal);
				formattedDate = formatter1.format(date);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return formattedDate;

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
		
		public String dateFormatForDate1(String dateVal) {


			SimpleDateFormat formatter = new SimpleDateFormat(
					"yy-MM-dd");
			SimpleDateFormat formatter1 = new SimpleDateFormat("dd-MM-yyyy");

			try {
				date = formatter.parse(dateVal);
				formattedDate = formatter1.format(date);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return formattedDate;

		}

		public void setUpTable(ArrayList<AttendanceResponseBean> attendanceResponseBeansList) {
			
			for(int i=0; i<attendanceResponseBeansList.size() ;i++)
				{
				mainLinear = (LinearLayout) findViewById(R.id.mainLinear);
				lLayout = new LinearLayout(this);
				lLayout.setOrientation(LinearLayout.HORIZONTAL);
//				lLayout.setId(1);

				tv = new TextView(this);
//				tv.setId(1);
				SimpleDateFormat formatter = new SimpleDateFormat(
						"yyyy-MM-dd'T'HH:mm:ss");
				Date date;
				try {

					date = formatter.parse(attendanceResponseBeansList.get(i).getEmpAttendanceDate());
					int ivalue = date.getDay();

					String dayvalue;

					if(ivalue==0)
						dayvalue="Sunday";
					else if(ivalue==1)
						dayvalue="Monday";
					else if(ivalue==2)
						dayvalue="Tuesday";
					else if(ivalue==3)
						dayvalue="Wednesday";
					else if(ivalue==4)
						dayvalue="Thursday";
					else if(ivalue==5)
						dayvalue="Friday";
					else
						dayvalue="saturday";

//					date = formatter.parse(attendanceResponseBeansList.get(i).getEmpAttendanceDate());
					tv.setText("\n\n" + dateFormatForDate(attendanceResponseBeansList.get(i).getEmpAttendanceDate())+"\n\n"
							+dayvalue);
					tv.setTypeface(null, Typeface.BOLD);
				} catch (ParseException e) {
					e.printStackTrace();
				}
	            tv.setTextSize(12);
				tv.setLayoutParams(new LayoutParams(convertToPixel(80),
						LayoutParams.WRAP_CONTENT));
	
				
			
				//tv.setPadding(5, 5, 5, 5);
				tv.setGravity(Gravity.CENTER);

				v0 = new View(this);
				v0.setLayoutParams(new LayoutParams(convertToPixel(2),
						convertToPixel(80)));
				v0.setBackgroundColor(Color.parseColor("#003E8F"));

				tbl = new TableLayout(this);
				tbl.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
						LayoutParams.WRAP_CONTENT));

				TableRow tr = new TableRow(this);
				tr.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
						LayoutParams.WRAP_CONTENT));

				TextView t1 = new TextView(this);
				TableRow.LayoutParams params1 = new TableRow.LayoutParams(
						0, LayoutParams.WRAP_CONTENT);
				params1.weight = 1;
				params1.width = convertToPixel(0);
				params1.height = LayoutParams.WRAP_CONTENT;
				params1.gravity = Gravity.CENTER_VERTICAL;
				t1.setLayoutParams(params1);
				t1.setText(dateFormat(attendanceResponseBeansList.get(i).getEmpSignAmInTime()));
				//t1.setPadding(5, 5, 5, 5);
				t1.setGravity(Gravity.CENTER);
				t1.setTypeface(null, Typeface.BOLD);

				v1 = new View(this);
				v1.setLayoutParams(new TableRow.LayoutParams(convertToPixel(1),
					convertToPixel(40)));
				v1.setBackgroundColor(Color.parseColor("#003E8F"));

				TextView t2 = new TextView(this);
				TableRow.LayoutParams params2 = new TableRow.LayoutParams(
						0, LayoutParams.WRAP_CONTENT);
				params2.width = convertToPixel(0);
				params2.height = LayoutParams.WRAP_CONTENT;
				params2.gravity = Gravity.CENTER_VERTICAL;

				params2.weight = 1;
				t2.setLayoutParams(params2);
				t2.setText(dateFormat(attendanceResponseBeansList.get(i).getEmpSignAmOutTime()));
				t2.setTypeface(null, Typeface.BOLD);
				t2.setGravity(Gravity.CENTER);

				View v2 = new View(this);

				v2.setLayoutParams(new TableRow.LayoutParams(convertToPixel(1),
					convertToPixel(40)));
				v2.setBackgroundColor(Color.parseColor("#003E8F"));

				TextView t3 = new TextView(this);
				TableRow.LayoutParams params3 = new TableRow.LayoutParams(
						0, LayoutParams.WRAP_CONTENT);
				params3.width = convertToPixel(0);
				params3.height = LayoutParams.WRAP_CONTENT;
				params3.gravity = Gravity.CENTER_VERTICAL;

				params3.weight = 1;
				t3.setLayoutParams(params3);
				t3.setText(dateFormat(attendanceResponseBeansList.get(i).getEmpSignPmInTime()));
				t3.setTypeface(null, Typeface.BOLD);
				t3.setGravity(Gravity.CENTER);

				View v3 = new View(this);

				v3.setLayoutParams(new TableRow.LayoutParams(convertToPixel(1),
					convertToPixel(40)));
				v3.setBackgroundColor(Color.BLACK);

				TextView t4 = new TextView(this);
				TableRow.LayoutParams params4 = new TableRow.LayoutParams(
						0, LayoutParams.WRAP_CONTENT);
				params4.width = convertToPixel(0);
				params4.height = LayoutParams.WRAP_CONTENT;
				params4.gravity = Gravity.CENTER_VERTICAL;

				params4.weight = 1;
				t4.setLayoutParams(params4);
				t4.setText(dateFormat(attendanceResponseBeansList.get(i).getEmpSignPmOutTime()));
				t4.setTypeface(null, Typeface.BOLD);
				t4.setGravity(Gravity.CENTER);

				View v4 = new View(this);
				v4.setLayoutParams(new TableRow.LayoutParams(LayoutParams.MATCH_PARENT,
						convertToPixel(1)));
				v4.setBackgroundColor(Color.parseColor("#003E8F"));

				tr.addView(t1);
				

				tr.addView(v1);

				tr.addView(t2);
				tr.addView(v2);

				tr.addView(t3);
				tr.addView(v3);

				tr.addView(t4);

				TableRow tr1 = new TableRow(this);
				tr1.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
						LayoutParams.WRAP_CONTENT));

				TextView tr1t1 = new TextView(this);
				TableRow.LayoutParams tr1params1 = new TableRow.LayoutParams(
					0, LayoutParams.WRAP_CONTENT);
				tr1params1.width = LayoutParams.MATCH_PARENT;
				tr1params1.height = LayoutParams.WRAP_CONTENT;
				tr1params1.gravity = Gravity.CENTER_VERTICAL;

				tr1params1.weight = 1;
				tr1t1.setLayoutParams(tr1params1);
				tr1t1.setText(attendanceResponseBeansList.get(i).getEmpDateNoteEn());
				tr1t1.setTextSize(17f);
				tr1t1.setTypeface(null, Typeface.BOLD);
				tr1t1.setPadding(5, 5, 5, 5);
				tr1t1.setGravity(Gravity.CENTER);

				View tr1v1 = new View(this);
				tr1v1.setLayoutParams(new TableRow.LayoutParams(convertToPixel(1),convertToPixel(37)));
				tr1v1.setBackgroundColor(Color.parseColor("#003E8F"));

				TextView tr1t2 = new TextView(this);
				TableRow.LayoutParams tr1params2 = new TableRow.LayoutParams(
						0, LayoutParams.WRAP_CONTENT);
				tr1params2.width = LayoutParams.MATCH_PARENT;
				tr1params2.height = LayoutParams.WRAP_CONTENT;

				tr1params2.weight = 1;
				tr1t2.setLayoutParams(tr1params2);
				tr1t2.setText(" ");
				tr1t2.setGravity(Gravity.CENTER);

				tr1.addView(tr1t1);
				//tr1.addView(tr1v1);

				//tr1.addView(tr1t2);

				View v5 = new View(this);
				v5.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
						convertToPixel(1)));
				v5.setBackgroundColor(Color.parseColor("#003E8F"));

				tbl.addView(tr);
				tbl.addView(v4);
				tbl.addView(tr1);
				lLayout.addView(tv);
				lLayout.addView(v0);
				lLayout.addView(tbl);
				mainLinear.addView(lLayout);
				mainLinear.addView(v5);
				}

			
		}

	
	public int convertToPixel(int dp) {
		final float scale = this.getResources().getDisplayMetrics().density;
		int pixels = (int) (dp * scale + 0.5f);
		return pixels;

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
		//dialogCaldroidFragment.setMinDate(calendar.getTime());
		dialogCaldroidFragment.setArguments(bundle);
		dialogCaldroidFragment.show(getSupportFragmentManager(), dialogTag);
		//dialogCaldroidFragment.show(this.getFragmentManager(), dialogTag);
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
		//dialogCaldroidFragment.setMinDate(calendar.getTime());
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

	public String fetchEnglishFromArabicNumber(String englishNumber)
	{
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
		}else if(employeeListResponseBeansList == null)
		{
			System.out.println("imran"+empList.getSelectedItemPosition());
			Show_Alert_Dialog(R.string.M_Select_Employee_Id);
		}
		else 
		{
			if(empList.getSelectedItemPosition() == 0)
			{
				selectedEmpId = employeeListResponseBeansList.get(0).getCivilId();
			}
			else
			{
				selectedEmpId = employeeListResponseBeansList.get(empList.getSelectedItemPosition()).getCivilId();
			}

			if (kfsd.checkNetworkRechability())
				callWebService();
			else
				Show_No_Internet();

		}

	}

	

	public String dateFormat(String dateVal) {

        if(!(dateVal.equalsIgnoreCase("null")))
        {
		SimpleDateFormat formatter = new SimpleDateFormat(
				"yyyy-MM-dd'T'HH:mm:ss");
		SimpleDateFormat formatter1 = new SimpleDateFormat("HH:mm");

		try {
			date = formatter.parse(dateVal);
			formattedDate = formatter1.format(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        }else
        {
        	formattedDate="-";
        }
		return formattedDate;

	}

	
	public void callWebService() {

		if (MySharedPref.getLanguage(FragmentAttendance.this).equals(
				MyCommon.LANGUAGE_ENG))
		{
			System.out.println("kac"+selectedEmpId);
			RequestParams params = new RequestParams(MyCommon.WS_PARA_START_DATE,
					"" +  startDate.getText().toString()); // / startDate.getText().toString()
			params.put(MyCommon.WS_PARA_END_DATE, "" + endDate.getText().toString()); // endDate.getText().toString()

			params.put(MyCommon.WS_PARA_CIVILID, "" + selectedEmpId);

			MyHttpConnection.get(MyCommon.WS_METHOD_ATTENDANCE, params,
					asyncLoginResponseHandler);
		}
		else
			
		{
			System.out.println("kac"+selectedEmpId);
			System.out.println("sdate"+dateFormatForDate1(startDate.getText().toString()));
			System.out.println("edate"+dateFormatForDate1(endDate.getText().toString()));
			RequestParams params = new RequestParams(MyCommon.WS_PARA_START_DATE,
					"" +  dateFormatForDate1(startDate.getText().toString())); // / startDate.getText().toString()
			params.put(MyCommon.WS_PARA_END_DATE, "" + dateFormatForDate1(endDate.getText().toString())); // endDate.getText().toString()

			params.put(MyCommon.WS_PARA_CIVILID, "" + selectedEmpId);

			MyHttpConnection.get(MyCommon.WS_METHOD_ATTENDANCE, params,
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
			try {

				JSONObject json = null;
				try {
					json = new JSONObject(new String(responseBody, "UTF-8"));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				int result = json.getInt("success");
				main1Linear = (LinearLayout) findViewById(R.id.main1Linear);
				if(result==0)
				{
					Show_Alert_Dialog(getResources().getString(R.string.No_data_available));

					if(main1Linear != null)
						main1Linear.setVisibility(View.INVISIBLE);
					if(mainLinear != null)
						mainLinear.setVisibility(View.INVISIBLE);

				}else
				{

					//if(main1Linear !=null)
					//main1Linear.removeAllViewsInLayout();
					if(mainLinear != null)
						mainLinear.removeAllViewsInLayout();

					JSONArray jsonArray = json.getJSONArray("KfsdMobileAttendace");
					Log.d("MzTag",jsonArray.toString());
					attendanceResponseBeansList = new ArrayList<AttendanceResponseBean>();

					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject jsonObject = jsonArray.getJSONObject(i);
						AttendanceResponseBean attendanceResponseBean = new AttendanceResponseBean();

						attendanceResponseBean.setEmpCivilId(jsonObject
								.getString("EmpCivilId"));

						attendanceResponseBean.setEmpAttendanceDate(jsonObject
								.getString("EmpAttendDate"));

						if(jsonObject.has("EmpDateNoteAr"))
						{
							String temp = jsonObject.getString("EmpDateNoteAr");

							if(temp.equals("null"))
								temp = " ";

							attendanceResponseBean.setEmpDateNoteAr(temp);
						}else
						{
							attendanceResponseBean.setEmpDateNoteAr(" ");
						}
						if(jsonObject.has("EmpDateNoteEn"))
						{
							String temp = jsonObject.getString("EmpDateNoteEn");

							if(temp.equals("null"))
								temp = " ";

							attendanceResponseBean.setEmpDateNoteEn(temp);

						}else
						{
							attendanceResponseBean.setEmpDateNoteEn(" ");
						}

						if(jsonObject.has("EmpSignAmInTime"))
						{
							attendanceResponseBean.setEmpSignAmInTime(jsonObject
									.getString("EmpSignAmInTime"));
						}else
						{
							attendanceResponseBean.setEmpSignAmInTime("-");

						}
						if(jsonObject.has("EmpSignAmOutTime"))
						{
							attendanceResponseBean.setEmpSignAmOutTime(jsonObject
									.getString("EmpSignAmOutTime"));

						}else
						{
							attendanceResponseBean.setEmpSignAmOutTime("-");

						}
						if(jsonObject.has("EmpSignPmInTime"))
						{
							attendanceResponseBean.setEmpSignPmInTime(jsonObject
									.getString("EmpSignPmInTime"));
						}else
						{
							attendanceResponseBean.setEmpSignPmInTime("-");

						}
						if(jsonObject.has("EmpSignPmOutTime"))
						{
							attendanceResponseBean.setEmpSignPmOutTime(jsonObject
									.getString("EmpSignPmOutTime"));

						}else
						{
							attendanceResponseBean.setEmpSignPmOutTime("-");

						}


						attendanceResponseBeansList.add(attendanceResponseBean);

					}

					//				//sort Ascending
					Collections.sort(attendanceResponseBeansList, new Comparator() {

						public int compare(Object o1, Object o2) {
							AttendanceResponseBean p1 = (AttendanceResponseBean) o1;
							AttendanceResponseBean p2 = (AttendanceResponseBean) o2;
							return p1.getEmpAttendanceDate().compareToIgnoreCase(p2.getEmpAttendanceDate());
						}
					});

					//Reverse to get descending
					Collections.reverse(attendanceResponseBeansList);

					if (MySharedPref.getLanguage(FragmentAttendance.this).equals(
							MyCommon.LANGUAGE_ENG)) {
						setUpTable(attendanceResponseBeansList);

					} else {
						setUpTableForArabic(attendanceResponseBeansList);
					}
					main1Linear = (LinearLayout) findViewById(R.id.main1Linear);
					main1Linear.setVisibility(View.VISIBLE);
					mainLinear.setVisibility(View.VISIBLE);
				}

			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
			main1Linear = (LinearLayout) findViewById(R.id.main1Linear);
			main1Linear.setVisibility(View.INVISIBLE);
			if(mainLinear!=null)
				mainLinear.setVisibility(View.INVISIBLE);
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
			
			break;
			
			
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

}
