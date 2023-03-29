package com.kuwaitKFF.cancelOrBreakVacation;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;

import androidx.databinding.DataBindingUtil;

import com.google.gson.Gson;
import com.kuwaitKFF.R;
import com.kuwaitKFF.base.BaseActivity;
import com.kuwaitKFF.cancelOrBreakVacation.models.sendRequestModels.SendRequestItem;
import com.kuwaitKFF.cancelOrBreakVacation.models.vacationData.VacationData;
import com.kuwaitKFF.common.MyCommon;
import com.kuwaitKFF.common.MyHttpConnection;
import com.kuwaitKFF.common.MySharedPref;
import com.kuwaitKFF.databinding.FragmentCancelOrBreakVacationBinding;
import com.kuwaitKFF.main.Kfsd;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Objects;

import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class FragmentCancelOrBreakVacation extends BaseActivity implements OnClickListener {

    Kfsd kfsd;
    FragmentCancelOrBreakVacationBinding b;
    private String vacationSerial = "";


    public FragmentCancelOrBreakVacation() {
        super(R.layout.fragment_cancel_or_break_vacation);

    }

    @Override
    public void initialization() {
        ButterKnife.bind(this);
        b = DataBindingUtil.setContentView(this, R.layout.fragment_cancel_or_break_vacation);

    }

    @Override
    public void implementation() {
        kfsd = (Kfsd) getApplication();

        if (getIntent().getIntExtra("cancelOrBreak", 0) == 0){
            b.titleText.setText(getResources().getString(R.string.cancel_vacation_request));
        }else{
            b.titleText.setText(getResources().getString(R.string.cut_vacation_request));
        }

        b.submitButton.setOnClickListener(this);


        if (MySharedPref.getLanguage(FragmentCancelOrBreakVacation.this).equals(
                MyCommon.LANGUAGE_ENG)) {
            setHeader(getResources().getString(R.string.welcome) + " " + MyCommon.sLoginResponseBean.getNameEn(), true, true, true);
        } else {
            setHeader(getResources().getString(R.string.welcome) + " " + MyCommon.sLoginResponseBean.getNameAr(), true, true, true);
        }

        getVacationData();

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

    public void getVacationData() {
        RequestParams params = new RequestParams();
        if (getIntent().getIntExtra("cancelOrBreak", 0) == 0){
            params.put(MyCommon.WS_METHOD_REQUEST_TYPE, 2);
        }else{
            params.put(MyCommon.WS_METHOD_REQUEST_TYPE, 3);
        }
        params.put(MyCommon.WS_METHOD_EMP_CIVIL_ID, "" + MyCommon.sLoginResponseBean.getCivilId());
        MyHttpConnection.getLeaveRequest("getLeaveRequestView", params, asyncGetVacationDataResponseHandler);
    }


    public void clickedSubmit() {
        if (b.phoneText.getText().toString().equals("")){
            Show_Alert_Dialog(R.string.phone_empty_validation);
        }else if (b.phoneText.getText().length() < 8){
            Show_Alert_Dialog(R.string.wrong_phone_validation);
        }else {
            sendCancelOrBreakVacationRequest();
        }
    }

    public void sendCancelOrBreakVacationRequest() {
        RequestParams params = new RequestParams();
        SimpleDateFormat format1, format2;
        format1 = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        format2 = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        try {
            params.put("leaveTerminateDate", format2.format(Objects.requireNonNull(format1.parse(b.endDateText.getText().toString()))));
            params.put("leaveEndDate", format2.format(Objects.requireNonNull(format1.parse(b.endDateText.getText().toString()))));
            params.put("leaveStartDate", format2.format(Objects.requireNonNull(format1.parse(b.startDateText.getText().toString()))));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(MySharedPref.getLanguage(this).equals(MyCommon.LANGUAGE_ENG)) {
            params.put("langFlag", "EN");
        }else{
            params.put("langFlag", "AR");
        }
        params.put("empCivilId", "" + MyCommon.sLoginResponseBean.getCivilId());
        params.put("leavePaymentFlag", 1);
        params.put("empCellNo", "" + b.phoneText.getText().toString());
        params.put("empMail", "");
        params.put("leaveRelatedSerial", vacationSerial);
        if (getIntent().getIntExtra("cancelOrBreak", 0) == 0){
            params.put("leaveTypeCode", 2);
            params.put("leaveReqTypeCode", 2);
        }else{
            params.put("leaveTypeCode", 3);
            params.put("leaveReqTypeCode", 3);
        }
		MyHttpConnection.getLeaveRequest("addLeaveRequest", params, asyncSendRequestResponseHandler);


    }

    AsyncHttpResponseHandler asyncSendRequestResponseHandler = new AsyncHttpResponseHandler() {

        @Override
        public void onFinish() {
            DismissProgress();
            super.onFinish();
        }

        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
            try {
                JSONArray json;
                try {
                    json = new JSONArray(new String(responseBody, "UTF-8"));
                    if (json.length() > 0) {
                        SendRequestItem sendRequestItem = new Gson().fromJson(json.getJSONObject(0).toString(), SendRequestItem.class);
                        if (sendRequestItem != null){
                            Show_Alert_Dialog(sendRequestItem.getRESPONSEMESSAGE());
                        }else {
                            Show_Alert_Dialog(R.string.M_Unable_To_Fetch_Data);
                        }
                    }else {
                        Show_Alert_Dialog(R.string.M_Unable_To_Fetch_Data);
                    }

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    Show_Alert_Dialog(R.string.M_Unable_To_Fetch_Data);
                }

            } catch (JSONException e) {
                e.printStackTrace();
                Show_Alert_Dialog(R.string.M_Unable_To_Fetch_Data);
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

    AsyncHttpResponseHandler asyncGetVacationDataResponseHandler = new AsyncHttpResponseHandler() {

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
                    VacationData vacationData = new Gson().fromJson(json.toString(), VacationData.class);
                    if (vacationData.getLeaveRequestView() != null && vacationData.getLeaveRequestView().size() > 0) {
                        b.leaveTypeText.setText(vacationData.getLeaveRequestView().get(0).getVACATIONDESC());
                        b.startDateText.setText(vacationData.getLeaveRequestView().get(0).getVACSTARTDATE().split(" ")[0]);
                        b.endDateText.setText(vacationData.getLeaveRequestView().get(0).getVACENDDATE().split(" ")[0]);
                        vacationSerial = vacationData.getLeaveRequestView().get(0).getVACSERIAL();
                    }else{
                        showDialogAndGoBack(R.string.M_Unable_To_Fetch_Data);
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    showDialogAndGoBack(R.string.M_Unable_To_Fetch_Data);
                }

            } catch (JSONException e) {
                e.printStackTrace();
                showDialogAndGoBack(R.string.M_Unable_To_Fetch_Data);
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            showDialogAndGoBack(R.string.M_Unable_To_Fetch_Data);
        }

        @Override
        public void onStart() {
            DisplayProgress();
            super.onStart();
        }

    };

    public void showDialogAndGoBack(int msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Dialog can not cancel by back button
        builder.setMessage(msg);
        commonShowAlertDialog(builder, null);
    }

    private void commonShowAlertDialog(AlertDialog.Builder builder,
                                       DialogInterface.OnClickListener clickListner) {
        // Dialog can not cancel by back button
        builder.setCancelable(false).setPositiveButton(
                getResources().getString(R.string.Ok),
                (clickListner != null) ? clickListner
                        : new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        // TODO Auto-generated method stub
                        dialog.dismiss();
                        onBackPressed();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.submitButton) {
            clickedSubmit();
        }

    }


}
