package com.kuwaitKFF.approveRejectVacations;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;

import androidx.databinding.DataBindingUtil;

import com.google.gson.Gson;
import com.kuwaitKFF.R;
import com.kuwaitKFF.approveRejectVacations.adapters.AcceptRejectVacationsListAdapter;
import com.kuwaitKFF.approveRejectVacations.interfaces.AcceptRejectItemInterface;
import com.kuwaitKFF.approveRejectVacations.models.vacationsDataModels.EmpLeaveRequestViewItem;
import com.kuwaitKFF.approveRejectVacations.models.vacationsDataModels.VacationsData;
import com.kuwaitKFF.base.BaseActivity;
import com.kuwaitKFF.cancelOrBreakVacation.models.sendRequestModels.SendRequestItem;
import com.kuwaitKFF.common.MyCommon;
import com.kuwaitKFF.common.MyHttpConnection;
import com.kuwaitKFF.common.MySharedPref;
import com.kuwaitKFF.databinding.FragmentApproveDeclineVacationsBinding;
import com.kuwaitKFF.main.Kfsd;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class FragmentAcceptRejectVacations extends BaseActivity implements OnClickListener, AcceptRejectItemInterface {

    Kfsd kfsd;
    FragmentApproveDeclineVacationsBinding b;


    public FragmentAcceptRejectVacations() {
        super(R.layout.fragment_approve_decline_vacations);

    }

    @Override
    public void initialization() {
        ButterKnife.bind(this);
        b = DataBindingUtil.setContentView(this, R.layout.fragment_approve_decline_vacations);
    }

    @Override
    public void implementation() {
        kfsd = (Kfsd) getApplication();

        if (getIntent().getIntExtra("whichApproval", 0) == 0){
            b.titleText.setText(getResources().getString(R.string.vacations_requests_approval));
        }else if (getIntent().getIntExtra("whichApproval", 0) == 1){
            b.titleText.setText(getResources().getString(R.string.cancel_vacations_requests_approval));
        }else{
            b.titleText.setText(getResources().getString(R.string.break_vacations_requests_approval));
        }

//        b.submitButton.setOnClickListener(this);

        if (MyCommon.sLoginResponseBean != null) {
            if (MySharedPref.getLanguage(FragmentAcceptRejectVacations.this).equals(
                    MyCommon.LANGUAGE_ENG)) {
                setHeader(getResources().getString(R.string.welcome) + " " + MyCommon.sLoginResponseBean.getNameEn(), true, true, true);
            } else {
                setHeader(getResources().getString(R.string.welcome) + " " + MyCommon.sLoginResponseBean.getNameAr(), true, true, true);
            }
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
        if (getIntent().getIntExtra("whichApproval", 0) == 0){
            params.put(MyCommon.WS_METHOD_REQUEST_TYPE, 1);
        }else if (getIntent().getIntExtra("whichApproval", 0) == 1){
            params.put(MyCommon.WS_METHOD_REQUEST_TYPE, 2);
        }else{
            params.put(MyCommon.WS_METHOD_REQUEST_TYPE, 3);
        }
        params.put(MyCommon.WS_METHOD_EMP_CIVIL_ID,  "" + MyCommon.sLoginResponseBean.getCivilId());
        MyHttpConnection.getLeaveRequest("getEmpLeaveRequestView", params, asyncGetVacationDataResponseHandler);
    }


    public void clickedSubmit() {
//        if (b.phoneText.getText().toString().equals("")){
//            Show_Alert_Dialog(R.string.phone_empty_validation);
//        }else if (b.phoneText.getText().length() < 8){
//            Show_Alert_Dialog(R.string.wrong_phone_validation);
//        }else {
//            sendCancelOrBreakVacationRequest();
//        }
    }

    public void acceptOrRejectRequest(EmpLeaveRequestViewItem item, int acceptOrReject) {
        RequestParams params = new RequestParams();
        if(MySharedPref.getLanguage(this).equals(MyCommon.LANGUAGE_ENG)) {
            params.put("langFlag", "EN");
        }else{
            params.put("langFlag", "AR");
        }
        params.put("civilId", "" + MyCommon.sLoginResponseBean.getCivilId());
        params.put("appSerial", item.getvAPPSERIAL());
        params.put("appLeaveSerial", item.getVACSERIAL());
        if (acceptOrReject == 2){
            params.put("appOrgId", item.getAppOrgId());
            MyHttpConnection.getLeaveRequest("leaveReject", params, asyncSendRequestResponseHandler);
        }else{
            MyHttpConnection.getLeaveRequest("leaveApproval", params, asyncSendRequestResponseHandler);
        }

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
                            getVacationData();
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
                    VacationsData vacationsData = new Gson().fromJson(json.toString(), VacationsData.class);
                    if (vacationsData != null){
                        if (vacationsData.getEmpLeaveRequestView() != null){
                            if (vacationsData.getEmpLeaveRequestView().size() > 0){
                                if (getIntent().getIntExtra("whichApproval", 0) == 0){
                                    b.vacationsList.setAdapter(new AcceptRejectVacationsListAdapter(FragmentAcceptRejectVacations.this, FragmentAcceptRejectVacations.this, 0, vacationsData.getEmpLeaveRequestView()));
                                }else if (getIntent().getIntExtra("whichApproval", 0) == 1){
                                    b.vacationsList.setAdapter(new AcceptRejectVacationsListAdapter(FragmentAcceptRejectVacations.this, FragmentAcceptRejectVacations.this, 1, vacationsData.getEmpLeaveRequestView()));
                                }else{
                                    b.vacationsList.setAdapter(new AcceptRejectVacationsListAdapter(FragmentAcceptRejectVacations.this, FragmentAcceptRejectVacations.this, 2, vacationsData.getEmpLeaveRequestView()));
                                }
                            }else{
                                showDialogAndGoBack(R.string.M_Unable_To_Fetch_Data);
                            }
                        }else{
                            showDialogAndGoBack(R.string.M_Unable_To_Fetch_Data);
                        }
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


    @Override
    public void onItemClick(EmpLeaveRequestViewItem item, int position, int acceptOrReject) {
        acceptOrRejectRequest(item, acceptOrReject);
    }
}
