package com.kuwaitKFF.home;

import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.kuwaitKFF.R;
import com.kuwaitKFF.base.BaseActivity;
import com.kuwaitKFF.common.MyCommon;
import com.kuwaitKFF.common.MyHttpConnection;
import com.kuwaitKFF.common.MySharedPref;
import com.kuwaitKFF.main.Kfsd;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class PAIAdoptionFragment extends BaseActivity implements View.OnClickListener {

    Kfsd kfsd;
    EditText trxNoEditTxt,rprsntCivilNoEditTxt, rprsntNameEditTxt, mobileNoEditTxt, emailEditTxt, captchaCheck;
    TextView captcha;
    Button submitButton;


    public PAIAdoptionFragment() {
        super(R.layout.fragment_pai_adoption);
    }

    @Override
    public void initialization() {
        ButterKnife.bind(this);

    }

    @Override
    public void implementation() {
        kfsd = (Kfsd) getApplication();

        trxNoEditTxt = (EditText)findViewById(R.id.trxNoTxtView);
        rprsntCivilNoEditTxt= (EditText)findViewById(R.id.rprsntCivilIdTxtView);
        rprsntNameEditTxt= (EditText)findViewById(R.id.rprsntNameTxtView);
        mobileNoEditTxt= (EditText)findViewById(R.id.mobileNoTxtView);
        emailEditTxt= (EditText)findViewById(R.id.emailTxtView);
        captchaCheck = (EditText) findViewById(R.id.captchaEnterTxt);

        submitButton = (Button)findViewById(R.id.submitButtonPai);
        submitButton.setOnClickListener(this);

        captcha = (TextView) findViewById(R.id.captcha);
        int randomPIN = (int)(Math.random()*9000)+1000;
        captcha.setText(String.valueOf(randomPIN));

        setHeader(getResources().getString(R.string.adoption_PAI), true, true);

    }



    public void callWebService() {

        RequestParams params = new RequestParams(MyCommon.WS_PARA_TRX_NO, "" +  trxNoEditTxt.getText().toString());
        params.put(MyCommon.WS_PARA_REP_CIVIL_NO, "" + rprsntCivilNoEditTxt.getText().toString());
        params.put(MyCommon.WS_PARA_REP_NAME, "" + rprsntNameEditTxt.getText().toString());
        params.put(MyCommon.WS_PARA_CELL_NO, "" + mobileNoEditTxt.getText().toString());
        params.put(MyCommon.WS_PARA_EMAIL, "" + rprsntNameEditTxt.getText().toString());

        if (MySharedPref.getLanguage(PAIAdoptionFragment.this).equalsIgnoreCase(MyCommon.LANGUAGE_ENG))
            params.put(MyCommon.WS_PARA_ULANG, "EN" );
        else
            params.put(MyCommon.WS_PARA_ULANG, "AR");

        params.put("verf", "1");

//        Log.d("MzTag",params.toString());

        MyHttpConnection.get(MyCommon.WS_METHOD_PAI, params,
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
            try {
                JSONObject json = null;
				try {
					json = new JSONObject(new String(responseBody, "UTF-8"));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
                JSONObject jsonObject = json.getJSONObject("KfsdPaiResponse");

                String validateFlag = jsonObject.getString("PValidateFlag");
                String validateMessage = jsonObject.getString("PValidateMessage");

                Show_Alert_Dialog(validateMessage);

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


    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.submitButtonPai) {

            hideSoftKeyboard();

            if(!emailEditTxt.getText().toString().contains("@"))
            {
                Show_Alert_Dialog(R.string.M_Please_Enter_correct_email);
                return;
            }

            if (captchaCheck.getText().toString().equals("")) {
                Show_Alert_Dialog(R.string.M_captcha_empty);
                return;
            }

            if (captchaCheck.getText() != null && !captchaCheck.getText().toString().equalsIgnoreCase(captcha.getText().toString())) {
                Show_Alert_Dialog(R.string.M_Please_Enter_correct);
                return;
            }



            if (kfsd.checkNetworkRechability())
                callWebService();
            else
                Show_No_Internet();

        }
    }


    public void hideSoftKeyboard() {
        if (getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus()
                    .getWindowToken(), 0);
        }
    }
}
