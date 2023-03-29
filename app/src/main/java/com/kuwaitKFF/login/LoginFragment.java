package com.kuwaitKFF.login;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import org.json.JSONException;
import org.json.JSONObject;

import com.kuwaitKFF.base.BaseFragment;
import com.kuwaitKFF.common.MyCommon;
import com.kuwaitKFF.common.MyHttpConnection;
import com.kuwaitKFF.common.MySharedPref;
import com.kuwaitKFF.main.Kfsd;
import com.kuwaitKFF.menu.MenuFragment;

import com.kuwaitKFF.R;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.FragmentTransaction;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.ClientProtocolException;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.protocol.HTTP;
import cz.msebera.android.httpclient.util.EntityUtils;

public class LoginFragment extends BaseFragment implements OnClickListener {

	Kfsd kfsd;
	View view;
	ImageView imageView1;
	ImageView imageView2;
	ImageView imageView3;
	LinearLayout relative1;
	RelativeLayout.LayoutParams params;
	boolean openCloseFlag = false;
	Button loginBtn;
	
	EditText userName,password;
	String sUserId,sPassword;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		kfsd = (Kfsd) getActivity().getApplication();

		view = LayoutInflater.from(getActivity()).inflate(
				R.layout.fragment_login, null);

		loginBtn = (Button) view.findViewById(R.id.loginBtn);
		loginBtn.setOnClickListener(this);
		
		userName = (EditText) view.findViewById(R.id.userNameText) ;
		password = (EditText) view.findViewById(R.id.passwordText) ;

		if(MySharedPref.getLanguage(getActivity()).equals(MyCommon.LANGUAGE_ENG))
		{
			userName.setGravity(Gravity.LEFT);
			password.setGravity(Gravity.LEFT);
		}else
		{
			userName.setGravity(Gravity.RIGHT);
			password.setGravity(Gravity.RIGHT);
			
		}
		setHeader(view, getResources().getString(R.string.login), false, false);

		return view;
	}
	
	
	public void setHeader(View view, String heading, boolean backVisible,
			boolean headerLogoVisible) {
		ViewStub stub = (ViewStub) view.findViewById(R.id.vsHeader);

		try {
			View inflated = stub.inflate();

			TextView txtTitle = (TextView) inflated
					.findViewById(R.id.txtVenueHeading);
		
			txtTitle.setText(heading);


			if (backVisible) {

				ImageButton backButton = (ImageButton) inflated
						.findViewById(R.id.Header_Back_Button);
				backButton.setVisibility(View.VISIBLE);
				backButton.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						
						if(getFragmentManager().getBackStackEntryCount()>0)
						{
							getFragmentManager().popBackStack();
						}

					}
				});

			}
			
			
			if (headerLogoVisible) {

				ImageButton logoButton = (ImageButton) inflated
						.findViewById(R.id.Header_Logo_Button);
				logoButton.setVisibility(View.VISIBLE);
				logoButton.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {

					}
				});

			}
		} catch (NullPointerException e) {
		}
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

				getFragmentManager().popBackStack();

			} catch (Exception e) {
				e.printStackTrace();
			}

			return true;

		}

		return false;
	}

	
	public void clickedLoginSubmit() {
		

		sUserId = MyCommon.getStringFromView(userName);
		sPassword = MyCommon.getStringFromView(password);

		if (sUserId.equalsIgnoreCase(MyCommon.DEFAULT_STRING)
				|| sPassword.equalsIgnoreCase(MyCommon.DEFAULT_STRING)) {
			Show_Alert_Dialog(R.string.M_Enter_Details);
		} else {
			if (kfsd.checkNetworkRechability())
				callLoginWebService();
			//callWebService();
			else
				Show_No_Internet();
		}

	}
	
	
	
	
	public void callWebService() {
		
		
			String webUrl = MyCommon.WS_BASE_URL + MyCommon.WS_METHOD_LOGIN;
		

		AsyncHttpPostTask asyn = new AsyncHttpPostTask(getActivity());
		asyn.execute(webUrl);

	}

	public class AsyncHttpPostTask extends AsyncTask<String, String, String> {
		private JSONObject jObj;
		ProgressDialog dialog;
		String strResponse = null;
		Context context;
		String xml;

		public AsyncHttpPostTask(Context context) {

			this.context = context;

		}

		@Override
		protected void onPreExecute() {
			dialog = new ProgressDialog(context);
			dialog.setMessage("Please wait...");
			dialog.setIndeterminate(true);
			dialog.show();
			// super.onPreExecute();
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			
			try {

	            JSONObject clientList = new JSONObject ();
	         
	                
	                clientList.put("pwd", "266041100022");
	                clientList.put("userCd", "17921");
	             
	                
	                StringEntity se = new StringEntity(clientList.toString(),"UTF-8");
	               
	                
	                se.setContentEncoding( new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
	               

	                DefaultHttpClient httpClient = new DefaultHttpClient();
	                HttpPost httpPost = new HttpPost(params[0]);

	                httpPost.setEntity(se);

	                HttpResponse httpResponse = httpClient.execute(httpPost);
	                HttpEntity httpEntity = httpResponse.getEntity();
	                xml = EntityUtils.toString(httpEntity);

	                // xml = xml.substring(39, xml.length() - 1);
	                strResponse = xml;



			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// return XML
			return xml;
		}

		@SuppressWarnings("deprecation")
		@Override
		protected void onPostExecute(String result) {
			String successString = null;
			if (dialog.isShowing()) {
				dialog.dismiss();
			}

			try {
				JSONObject json = new JSONObject(strResponse);
				int resultId = json.getInt("success");
				
				
			} catch (JSONException e) {
				e.printStackTrace();
			}

		}

	}
	
	public void callLoginWebService()  {
		
		RequestParams params = new RequestParams(MyCommon.WS_PARA_Pass, ""
				+ sPassword);
		params.put(MyCommon.WS_PARA_UserId,
				"" + sUserId);
		
		MyHttpConnection.get(MyCommon.WS_METHOD_LOGIN, params,
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
			try{

				JSONObject json = null;
				try {
					json = new JSONObject(new String(responseBody, "UTF-8"));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				int result = json.getInt("success");
				if(result == 1)
				{
					JSONObject jsonObj = json.getJSONObject("KfsdLoginResponse");
					LoginResponseBean loginResponseBean = new LoginResponseBean();
					loginResponseBean.setCivilId(jsonObj.getString("UserCivilId"));
					loginResponseBean.setFileNo(jsonObj.getString("UserFileNo"));
					loginResponseBean.setNameAr(jsonObj.getString("UserNameAr"));
					loginResponseBean.setNameEn(jsonObj.getString("UserNameEn"));
					loginResponseBean.setLoginId(jsonObj.getString("UserLogin"));

					MyCommon.sLoginResponseBean = loginResponseBean;

					SharedPreferences prefs = getActivity().getSharedPreferences("com.kfsd", Context.MODE_PRIVATE);
					prefs.edit().putString("com.kfsd.userName", sUserId ).apply();
					prefs.edit().putString("com.kfsd.Password", sPassword ).apply();

					FragmentTransaction ft = getFragmentManager().beginTransaction();
					MenuFragment mnFrg = new MenuFragment();
					ft.replace(R.id.groupContent, mnFrg);
					ft.addToBackStack(null);
					ft.commit();

				}else
				{
					SharedPreferences prefs = getActivity().getSharedPreferences("com.kfsd", Context.MODE_PRIVATE);
					prefs.edit().clear().apply();
					Show_Alert_Dialog(R.string.M_Invalid_User);
				}

			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
			Show_Alert_Dialog(R.string.M_Unable_To_Fetch_Data);
//			LoginResponseBean loginResponseBean = new LoginResponseBean();
//			loginResponseBean.setCivilId("268032800923");
//			loginResponseBean.setFileNo("");
//			loginResponseBean.setNameAr("");
//			loginResponseBean.setNameEn("");
//			loginResponseBean.setLoginId("");
//
//			MyCommon.sLoginResponseBean = loginResponseBean;
//
//			SharedPreferences prefs = getActivity().getSharedPreferences("com.kfsd", Context.MODE_PRIVATE);
//			prefs.edit().putString("com.kfsd.userName", sUserId ).apply();
//			prefs.edit().putString("com.kfsd.Password", sPassword ).apply();
//
//			FragmentTransaction ft = getFragmentManager().beginTransaction();
//			MenuFragment mnFrg = new MenuFragment();
//			ft.replace(R.id.groupContent, mnFrg);
//			ft.addToBackStack(null);
//			ft.commit();
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

		case R.id.loginBtn:
			clickedLoginSubmit();
			
			break;
		default:
			break;
		}

	}

}
