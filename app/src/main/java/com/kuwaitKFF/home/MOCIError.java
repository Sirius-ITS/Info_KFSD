package com.kuwaitKFF.home;

import android.widget.TextView;


import com.kuwaitKFF.R;
import com.kuwaitKFF.base.BaseActivity;
import com.kuwaitKFF.common.MyCommon;
import com.kuwaitKFF.common.MyHttpConnection;
import com.kuwaitKFF.main.Kfsd;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class MOCIError extends BaseActivity  {

    Kfsd kfsd;

    TextView errorTxtView;



    public MOCIError() {
        super(R.layout.fragment_moci_error);
    }

    @Override
    public void initialization() {
        ButterKnife.bind(this);

    }

    @Override
    public void implementation() {
        kfsd = (Kfsd) getApplication();

        errorTxtView = (TextView) findViewById(R.id.errorTxtView);

        setHeader(getResources().getString(R.string.adoption_MOCI), true, true);

        callWebService();

    }



    public void callWebService() {

        String s = getIntent().getStringExtra("appNo");

        RequestParams params = new RequestParams(MyCommon.WS_PARA_TRX_NO, s);

        MyHttpConnection.get(MyCommon.WS_METHOD_MOCI_ERROR, params,
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
            try
            {
                JSONObject json = null;
				try {
					json = new JSONObject(new String(responseBody, "UTF-8"));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
                JSONArray jsonArray = json.getJSONArray("KfsdMobileAppError");
//				Log.d("MzTag",jsonArray.toString());

                JSONObject jsonObject = jsonArray.getJSONObject(0);

                String errorDesc = jsonObject.getString("MociErrorDataDesc");

                errorTxtView.setText(errorDesc);

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




}
