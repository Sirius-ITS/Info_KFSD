package com.kuwaitKFF.vacationsbalance;

import android.annotation.SuppressLint;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.databinding.DataBindingUtil;

import com.google.gson.Gson;
import com.kuwaitKFF.R;
import com.kuwaitKFF.base.BaseActivity;
import com.kuwaitKFF.common.MyCommon;
import com.kuwaitKFF.common.MyHttpConnection;
import com.kuwaitKFF.common.MySharedPref;
import com.kuwaitKFF.databinding.FragmentVacationsBalanceBinding;
import com.kuwaitKFF.main.Kfsd;
import com.kuwaitKFF.vacationsbalance.models.leaveBalanceData.LeaveBalance;
import com.kuwaitKFF.vacationsbalance.models.vacationTypes.ListOfvacationTypesItem;
import com.kuwaitKFF.vacationsbalance.models.vacationTypes.VacationTypes;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class FragmentVacationBalance extends BaseActivity implements OnClickListener {

    Kfsd kfsd;
    FragmentVacationsBalanceBinding b;
    private int vacationTypePosition = 1;
    private String vacationTypeCode = "";


    public FragmentVacationBalance() {
        super(R.layout.fragment_vacations_balance);

    }

    @Override
    public void initialization() {
        ButterKnife.bind(this);
        b = DataBindingUtil.setContentView(this, R.layout.fragment_vacations_balance);

    }

    @Override
    public void implementation() {
        kfsd = (Kfsd) getApplication();

        b.titleText.setText(getResources().getString(R.string.vacations_balance));

        b.submitButton.setOnClickListener(this);


        if (MySharedPref.getLanguage(FragmentVacationBalance.this).equals(
                MyCommon.LANGUAGE_ENG)) {
            setHeader(getResources().getString(R.string.welcome) + " " + MyCommon.sLoginResponseBean.getNameEn(), true, true, true);
        } else {
            setHeader(getResources().getString(R.string.welcome) + " " + MyCommon.sLoginResponseBean.getNameAr(), true, true, true);
        }

        getVacationTypes();

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


    public void clickedSubmit(String vacationTypeCode, int position) {
        if (position > 0){
            showVacationBalance(vacationTypeCode);
        }else{
            Show_Alert_Dialog(R.string.vacation_type_validation);
            b.tableLayout.setVisibility(View.GONE);
        }
    }

	public void getVacationTypes() {

		MyHttpConnection.getNew(MyCommon.WS_METHOD_VACATION_TYPES,
                asyncVacationTypesResponseHandler);


	}

    public void showVacationBalance(String vacationType) {
        RequestParams params = new RequestParams();
        params.put(MyCommon.WS_METHOD_LEAVE_TYPE, vacationType);
        params.put(MyCommon.WS_PARA_CIVILID, "" + MyCommon.sLoginResponseBean.getCivilId());
        params.put(MyCommon.WS_METHOD_YEAR,  Calendar.getInstance().get(Calendar.YEAR));
		MyHttpConnection.getNew(MyCommon.WS_METHOD_VACATION_BALANCE, params, asyncVacationBalanceResponseHandler);


    }

    AsyncHttpResponseHandler asyncVacationTypesResponseHandler = new AsyncHttpResponseHandler() {

        @Override
        public void onFinish() {
            DismissProgress();
            super.onFinish();
        }

        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

            try {
                JSONObject json;
                try {
                    json = new JSONObject(new String(responseBody, "UTF-8"));
                    VacationTypes vacationTypes = new Gson().fromJson(json.toString(), VacationTypes.class);
                    List<String> types = new ArrayList<>();
                    types.add(getResources().getString(R.string.choose));
                    for (ListOfvacationTypesItem typesItem: vacationTypes.getListOfvacationTypes()){
                        types.add(typesItem.getVacationDesc());
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(
                            FragmentVacationBalance.this, android.R.layout.simple_spinner_item, types);

                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    b.leaveTypeSpinner.setAdapter(adapter);
                    b.leaveTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                            if (position > 0) {
                                vacationTypeCode = vacationTypes.getListOfvacationTypes().get(position - 1).getVacationCode();
                            }
                            vacationTypePosition = position;
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
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
                    LeaveBalance vacationBalance = new Gson().fromJson(json.toString(), LeaveBalance.class);
                    b.cumulativeValue.setText(String.valueOf(vacationBalance.getLeaveBalanceData().get(0).getBALANCETRANSFERVALUE()));
                    b.annualValue.setText(String.valueOf(vacationBalance.getLeaveBalanceData().get(0).getBALANCENEWVALUE()));
                    b.frozenBalanceValue.setText(String.valueOf(vacationBalance.getLeaveBalanceData().get(0).getBALANCEADDEDVALUE()));
                    b.formerExcludedValue.setText(String.valueOf(vacationBalance.getLeaveBalanceData().get(0).getBALANCEPENALTYVALUE()));
                    b.currentlyExcluded.setText(String.valueOf(vacationBalance.getLeaveBalanceData().get(0).getBALANCEPENALTYRESTVALUE()));
                    b.noOfVacationsValue.setText(String.valueOf(vacationBalance.getLeaveBalanceData().get(0).getLEAVECOUNT()));
                    b.totalDurationValue.setText(String.valueOf(vacationBalance.getLeaveBalanceData().get(0).getLEAVETOTALDURATION()));
                    b.actualDurationValue.setText(String.valueOf(vacationBalance.getLeaveBalanceData().get(0).getLEAVEACTUALDURATION()));
                    b.cashRedemptionValue.setText(String.valueOf(vacationBalance.getLeaveBalanceData().get(0).getVACEXCHANGEVALUE()));
                    b.residualValue.setText(String.valueOf(vacationBalance.getLeaveBalanceData().get(0).getLEAVERESTDURATION()));
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


    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.submitButton) {
            clickedSubmit(vacationTypeCode, vacationTypePosition);
        }

    }


}
