package com.kuwaitKFF.sickVacationBalance;

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
import com.kuwaitKFF.databinding.FragmentSickVacationsBalanceBinding;
import com.kuwaitKFF.main.Kfsd;
import com.kuwaitKFF.sickVacationBalance.models.sickLeaveData.SickLeaveData;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;

import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class FragmentSickVacationBalance extends BaseActivity {

    Kfsd kfsd;
    FragmentSickVacationsBalanceBinding b;
    private boolean flag = false;


    public FragmentSickVacationBalance() {
        super(R.layout.fragment_sick_vacations_balance);

    }

    @Override
    public void initialization() {
        ButterKnife.bind(this);
        b = DataBindingUtil.setContentView(this, R.layout.fragment_sick_vacations_balance);

    }

    @Override
    public void implementation() {
        kfsd = (Kfsd) getApplication();

        b.titleText.setText(getResources().getString(R.string.sick_leave_balance));



        if (MySharedPref.getLanguage(FragmentSickVacationBalance.this).equals(
                MyCommon.LANGUAGE_ENG)) {
            setHeader(getResources().getString(R.string.welcome) + " " + MyCommon.sLoginResponseBean.getNameEn(), true, true, true);
        } else {
            setHeader(getResources().getString(R.string.welcome) + " " + MyCommon.sLoginResponseBean.getNameAr(), true, true, true);
        }

        getYears();

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

	public void getYears() {

        ArrayList<String> years = new ArrayList<>();
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        years.add(getResources().getString(R.string.choose));
        for (int i = 1900; i <= thisYear; i++) {
            years.add(Integer.toString(i));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, years);

        b.yearSpinner.setAdapter(adapter);
        b.spinnerParentLayout.setOnClickListener(new View.OnClickListener() {
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
                        showSickVacationBalance(years.get(position));
                    } else {
                        b.tableLayout.setVisibility(View.GONE);
                        Show_Alert_Dialog(R.string.year_validation);
                    }
                }
                flag = true;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


	}

    public void showSickVacationBalance(String year) {
        RequestParams params = new RequestParams();
        params.put(MyCommon.WS_PARA_CIVILID, "" + MyCommon.sLoginResponseBean.getCivilId());
        params.put(MyCommon.WS_METHOD_YEAR,  year);
		MyHttpConnection.getNew(MyCommon.WS_METHOD_SICK_VACATION_BALANCE, params, asyncVacationBalanceResponseHandler);


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
                    SickLeaveData sickLeaveData = new Gson().fromJson(json.toString(), SickLeaveData.class);
                    b.sickHealthCentersValue.setText(String.valueOf(sickLeaveData.getSickLeaveData().get(0).getSICKCOUNT()));
                    b.sickQuarantineValue.setText(String.valueOf(sickLeaveData.getSickLeaveData().get(0).getSICKHEALTHCOUNT()));
                    b.sickHospitalsValue.setText(String.valueOf(sickLeaveData.getSickLeaveData().get(0).getSICKHOSPITALCOUNT()));
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
