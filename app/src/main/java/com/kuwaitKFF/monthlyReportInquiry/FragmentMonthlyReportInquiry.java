package com.kuwaitKFF.monthlyReportInquiry;

import android.os.Build;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.databinding.DataBindingUtil;

import com.google.gson.Gson;
import com.kuwaitKFF.R;
import com.kuwaitKFF.base.BaseActivity;
import com.kuwaitKFF.common.MyCommon;
import com.kuwaitKFF.common.MyHttpConnection;
import com.kuwaitKFF.common.MySharedPref;
import com.kuwaitKFF.databinding.FragmentMonthlyReportInquiryBinding;
import com.kuwaitKFF.main.Kfsd;
import com.kuwaitKFF.monthlyReportInquiry.models.empMonthlyDetail.EmpMonthlyDetail;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class FragmentMonthlyReportInquiry extends BaseActivity {

    Kfsd kfsd;
    FragmentMonthlyReportInquiryBinding b;
    private boolean flag = false;
    private boolean monthsFlag = false;
    private int month = 0, year = 0;


    public FragmentMonthlyReportInquiry() {
        super(R.layout.fragment_monthly_report_inquiry);

    }

    @Override
    public void initialization() {
        ButterKnife.bind(this);
        b = DataBindingUtil.setContentView(this, R.layout.fragment_monthly_report_inquiry);

    }

    @Override
    public void implementation() {
        kfsd = (Kfsd) getApplication();

        b.titleText.setText(getResources().getString(R.string.month_report_enquiry));



        if (MySharedPref.getLanguage(FragmentMonthlyReportInquiry.this).equals(
                MyCommon.LANGUAGE_ENG)) {
            setHeader(getResources().getString(R.string.welcome) + " " + MyCommon.sLoginResponseBean.getNameEn(), true, true, true);
        } else {
            setHeader(getResources().getString(R.string.welcome) + " " + MyCommon.sLoginResponseBean.getNameAr(), true, true, true);
        }

        getYearsAndMonths();

        b.searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (year == 0 ){
                    Show_Alert_Dialog(R.string.year_validation);
                }else if (month == 0){
                    Show_Alert_Dialog(R.string.month_validation);
                }else {
                    showMonthlyReport(year, month);
                }
            }
        });

    }


    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // preventing default implementation previous to
        // android.os.Build.VERSION_CODES.ECLAIR
        return keyCode == KeyEvent.KEYCODE_BACK;

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

	public void getYearsAndMonths() {

        ArrayList<String> years = new ArrayList<>();
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        years.add(getResources().getString(R.string.choose));
        for (int i = 1900; i <= thisYear; i++) {
            years.add(Integer.toString(i));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, years);

        b.yearSpinner.setAdapter(adapter);
        b.yearSpinnerParentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b.yearSpinner.performClick();
            }
        });
        b.yearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if (flag) {
                    if (position > 0) {
                        year = Integer.parseInt(years.get(position));
                    } else {
                        year = 0;
                    }
                }
                flag = true;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayList<String> months = new ArrayList<>();
        months.add(getResources().getString(R.string.choose));
        for (int i = 1; i <= 12; i++) {
            months.add(Integer.toString(i));
        }
        ArrayAdapter<String> monthsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, months);

        b.monthSpinner.setAdapter(monthsAdapter);
        b.monthSpinnerParentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b.monthSpinner.performClick();
            }
        });
        b.monthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if (monthsFlag) {
                    if (position > 0) {
                        month = Integer.parseInt(months.get(position));
                    } else {
                        month = 0;
                    }
                }
                monthsFlag = true;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

	}

    public void showMonthlyReport(int year, int month) {
        RequestParams params = new RequestParams();
        params.put(MyCommon.WS_PARA_CIVILID, "" + MyCommon.sLoginResponseBean.getCivilId());
        params.put(MyCommon.WS_METHOD_MONTH,  year+""+month);
        params.put(MyCommon.WS_METHOD_FROM_DATE,  year+""+month+"01");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            params.put(MyCommon.WS_METHOD_TO_DATE,  year + "" + month + YearMonth.of(year, month).lengthOfMonth());
        }else{
            Calendar myCal = new GregorianCalendar(year, month, 1);

            int daysInMonth = myCal.getActualMaximum(Calendar.DAY_OF_MONTH);
            params.put(MyCommon.WS_METHOD_TO_DATE,  year + "" + month + "" + daysInMonth);
        }
        MyHttpConnection.getNew(MyCommon.WS_METHOD_MONTHLY_REPORT_INQUIRY, params, asyncVacationBalanceResponseHandler);


    }

    AsyncHttpResponseHandler asyncVacationBalanceResponseHandler = new AsyncHttpResponseHandler() {

        @Override
        public void onFinish() {
            DismissProgress();
            super.onFinish();
        }

        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
            b.tableLayout.setVisibility(View.VISIBLE);
            try {
                JSONObject json;
                try {
                    json = new JSONObject(new String(responseBody, "UTF-8"));
                    EmpMonthlyDetail sickLeaveData = new Gson().fromJson(json.toString(), EmpMonthlyDetail.class);
                    if (sickLeaveData != null) {
                        if (sickLeaveData.getEmpMonthlyDetail() != null && sickLeaveData.getEmpMonthlyDetail().size() > 0) {
                            if (!String.valueOf(sickLeaveData.getEmpMonthlyDetail().get(0).getABSENTCOUNT()).equals("null")) {
                                b.noOfAbsentDaysValue.setText(String.valueOf(sickLeaveData.getEmpMonthlyDetail().get(0).getABSENTCOUNT()));
                            }
                            if (!String.valueOf(sickLeaveData.getEmpMonthlyDetail().get(0).getABSENTDAYS()).equals("null")) {
                                b.absentDaysValue.setText(String.valueOf(sickLeaveData.getEmpMonthlyDetail().get(0).getABSENTDAYS()));
                            } else {
                                b.absentDaysValue.setText("-");
                            }
                            if (!String.valueOf(sickLeaveData.getEmpMonthlyDetail().get(0).getNOTINCOUNT()).equals("null")) {
                                b.notSigningAttendanceValue.setText(String.valueOf(sickLeaveData.getEmpMonthlyDetail().get(0).getNOTINCOUNT()));
                            }
                            if (!String.valueOf(sickLeaveData.getEmpMonthlyDetail().get(0).getNOTOUTCOUNT()).equals("null")) {
                                b.notSigningLeaveValue.setText(String.valueOf(sickLeaveData.getEmpMonthlyDetail().get(0).getNOTOUTCOUNT()));
                            }
                            if (!String.valueOf(sickLeaveData.getEmpMonthlyDetail().get(0).getSICKCOUNT()).equals("null")) {
                                b.sickHealthCentersValue.setText(String.valueOf(sickLeaveData.getEmpMonthlyDetail().get(0).getSICKCOUNT()));
                            }
                            if (!String.valueOf(sickLeaveData.getEmpMonthlyDetail().get(0).getVACCOUNT()).equals("null")) {
                                b.otherVacationsValue.setText(String.valueOf(sickLeaveData.getEmpMonthlyDetail().get(0).getVACCOUNT()));
                            }
                            if (!String.valueOf(sickLeaveData.getEmpMonthlyDetail().get(0).getMESSIONCOUNT()).equals("null")) {
                                b.costsValue.setText(String.valueOf(sickLeaveData.getEmpMonthlyDetail().get(0).getMESSIONCOUNT()));
                            }
                            if (sickLeaveData.getEmpMonthlyDetail().get(0).getLATEAMOUNTPERMONTH() != null) {
                                b.delayValue.setText(String.valueOf(sickLeaveData.getEmpMonthlyDetail().get(0).getLATEAMOUNTPERMONTH()));
                            }
                            if (!String.valueOf(sickLeaveData.getEmpMonthlyDetail().get(0).getTOTALLATEDISCOUNT()).equals("null")) {
                                b.delayDeductionValue.setText(String.valueOf(sickLeaveData.getEmpMonthlyDetail().get(0).getTOTALLATEDISCOUNT()));
                            }
                            if (!String.valueOf(sickLeaveData.getEmpMonthlyDetail().get(0).getABSENTDAYSCOUNT()).equals("null")) {
                                b.absentDeductionValue.setText(String.valueOf(sickLeaveData.getEmpMonthlyDetail().get(0).getABSENTDAYSCOUNT()));
                            }
                        }else{
                            b.tableLayout.setVisibility(View.GONE);
                            Show_Alert_Dialog(R.string.M_Unable_To_Fetch_Data);
                        }
                    }else{
                        b.tableLayout.setVisibility(View.GONE);
                        Show_Alert_Dialog(R.string.M_Unable_To_Fetch_Data);
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    b.tableLayout.setVisibility(View.GONE);
                    Show_Alert_Dialog(R.string.M_Unable_To_Fetch_Data);
                }

            } catch (JSONException e) {
                e.printStackTrace();
                b.tableLayout.setVisibility(View.GONE);
                Show_Alert_Dialog(R.string.M_Unable_To_Fetch_Data);
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            b.tableLayout.setVisibility(View.GONE);
            Show_Alert_Dialog(R.string.M_Unable_To_Fetch_Data);
        }

        @Override
        public void onStart() {
            DisplayProgress();
            super.onStart();
        }

    };



}
