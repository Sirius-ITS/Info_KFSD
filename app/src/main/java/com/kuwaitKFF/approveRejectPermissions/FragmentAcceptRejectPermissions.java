package com.kuwaitKFF.approveRejectPermissions;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.databinding.DataBindingUtil;

import com.google.gson.Gson;
import com.kuwaitKFF.R;
import com.kuwaitKFF.approveRejectPermissions.adapters.AcceptRejectPermissionsListAdapter;
import com.kuwaitKFF.approveRejectPermissions.interfaces.AcceptRejectPermissionItemInterface;
import com.kuwaitKFF.approveRejectPermissions.models.permissionsDataModels.EmpPermissionRequestViewItem;
import com.kuwaitKFF.approveRejectPermissions.models.permissionsDataModels.PermissionsData;
import com.kuwaitKFF.approveRejectVacations.adapters.AcceptRejectVacationsListAdapter;
import com.kuwaitKFF.approveRejectVacations.interfaces.AcceptRejectItemInterface;
import com.kuwaitKFF.approveRejectVacations.models.vacationsDataModels.EmpLeaveRequestViewItem;
import com.kuwaitKFF.approveRejectVacations.models.vacationsDataModels.VacationsData;
import com.kuwaitKFF.base.BaseActivity;
import com.kuwaitKFF.cancelOrBreakVacation.models.sendRequestModels.SendRequestItem;
import com.kuwaitKFF.common.MyCommon;
import com.kuwaitKFF.common.MyHttpConnection;
import com.kuwaitKFF.common.MySharedPref;
import com.kuwaitKFF.databinding.FragmentApproveDeclinePermissionsBinding;
import com.kuwaitKFF.databinding.FragmentApproveDeclineVacationsBinding;
import com.kuwaitKFF.main.Kfsd;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class FragmentAcceptRejectPermissions extends BaseActivity implements OnClickListener, AcceptRejectPermissionItemInterface {

    Kfsd kfsd;
    FragmentApproveDeclinePermissionsBinding b;
    private boolean flag = false;


    public FragmentAcceptRejectPermissions() {
        super(R.layout.fragment_approve_decline_permissions);

    }

    @Override
    public void initialization() {
        ButterKnife.bind(this);
        b = DataBindingUtil.setContentView(this, R.layout.fragment_approve_decline_permissions);
    }

    @Override
    public void implementation() {
        kfsd = (Kfsd) getApplication();

        b.titleText.setText(getResources().getString(R.string.permissions_approval));


        if (MySharedPref.getLanguage(FragmentAcceptRejectPermissions.this).equals(
                MyCommon.LANGUAGE_ENG)) {
            setHeader(getResources().getString(R.string.welcome) + " " + MyCommon.sLoginResponseBean.getNameEn(), true, true, true);
        } else {
            setHeader(getResources().getString(R.string.welcome) + " " + MyCommon.sLoginResponseBean.getNameAr(), true, true, true);
        }

        populatePermissionTypeSpinner();

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

    private void populatePermissionTypeSpinner(){
        ArrayList<String> types = new ArrayList<>();
        types.add(getResources().getString(R.string.choose));
        types.add(getResources().getString(R.string.direct_affiliate_units));
        types.add(getResources().getString(R.string.all_affiliate_units));
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, types);

        b.searchLevelSpinner.setAdapter(adapter);
        b.spinnerParentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b.searchLevelSpinner.performClick();
            }
        });
        b.searchLevelSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if (flag) {
                    if (position > 0) {
                        getPermissionsData(position);
                    }
                }
                flag = true;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void getPermissionsData(int levelList) {
        RequestParams params = new RequestParams();
        params.put("levelList", levelList);
        params.put("empCivilId", "" + MyCommon.sLoginResponseBean.getCivilId());
        MyHttpConnection.getPermissionRequest("getPermissionRequestView", params, asyncGetVacationDataResponseHandler);
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

    public void acceptOrRejectRequest(EmpPermissionRequestViewItem item, int acceptOrReject) {
        RequestParams params = new RequestParams();

        SimpleDateFormat format1, format2;
        format1 = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        format2 = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        try {
            params.put("permDate", format2.format(Objects.requireNonNull(format1.parse(item.getPERMDATE().split(" ")[0]))));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if(MySharedPref.getLanguage(this).equals(MyCommon.LANGUAGE_ENG)) {
            params.put("langFlag", "EN");
        }else{
            params.put("langFlag", "AR");
        }
        params.put("permEmpCivilId", item.getPERMEMPCIVILID());
        params.put("permSerial", item.getPERMSERIAL());
        params.put("empCivilId", "" + MyCommon.sLoginResponseBean.getCivilId());
        params.put("permTypeCode", b.searchLevelSpinner.getSelectedItemPosition());
        if (acceptOrReject == 2){
            MyHttpConnection.getPermissionRequest("permissionReject", params, asyncSendRequestResponseHandler);
        }else{
            MyHttpConnection.getPermissionRequest("permissionApproval", params, asyncSendRequestResponseHandler);
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
                            getPermissionsData(b.searchLevelSpinner.getSelectedItemPosition());
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
                    PermissionsData permissionsData = new Gson().fromJson(json.toString(), PermissionsData.class);
                    if (permissionsData != null){
                        if (permissionsData.getEmpPermissionRequestView() != null){
                            if (permissionsData.getEmpPermissionRequestView().size() > 0){
                                b.permissionsList.setAdapter(new AcceptRejectPermissionsListAdapter(FragmentAcceptRejectPermissions.this, FragmentAcceptRejectPermissions.this, 0, permissionsData.getEmpPermissionRequestView()));
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
    public void onItemClick(EmpPermissionRequestViewItem item, int position, int acceptOrReject) {
        acceptOrRejectRequest(item, acceptOrReject);
    }
}
