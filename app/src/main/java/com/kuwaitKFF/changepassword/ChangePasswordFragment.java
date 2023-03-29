package com.kuwaitKFF.changepassword;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.kuwaitKFF.R;
import com.kuwaitKFF.base.BaseFragment;
import com.kuwaitKFF.common.MyCommon;
import com.kuwaitKFF.common.MyHttpConnection;
import com.kuwaitKFF.common.MySharedPref;
import com.kuwaitKFF.main.Kfsd;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;

public class ChangePasswordFragment extends BaseFragment implements OnClickListener {

	Kfsd kfsd;
	View view;
	ImageView imageView1;
	ImageView imageView2;
	ImageView imageView3;
	LinearLayout relative1;

	boolean openCloseFlag = false;
	ImageView loginImage;
	
	RequestParams params;
	
	EditText newwPassword,oldPassword,confirmPassword;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		kfsd = (Kfsd) getActivity().getApplication();

		view = LayoutInflater.from(getActivity()).inflate(
				R.layout.fragment_change_password, null);
		loginImage = (ImageView)view.findViewById(R.id.loginImage);
		loginImage.setOnClickListener(this);
		
		newwPassword = (EditText) view.findViewById(R.id.newPassText);
		oldPassword = (EditText) view.findViewById(R.id.oldPassText);
		confirmPassword = (EditText) view.findViewById(R.id.confirmPassText);
		
		
		if (MySharedPref.getLanguage(getActivity()).equals(
				MyCommon.LANGUAGE_ENG)) {
			setHeader(view,getResources().getString(R.string.welcome)+" "+MyCommon.sLoginResponseBean.getNameEn(), true,true,true);
		}else
		{
			setHeader(view,getResources().getString(R.string.welcome)+" "+MyCommon.sLoginResponseBean.getNameAr(), true,true,true);
		}
		


		return view;
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

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.loginImage:
        	 clickedSubmit();
		break;
		
		default:
			break;
		}

	}

	private void clickedSubmit() 
	{
		hideSoftKeyboard();
		System.out.println("confirmPassword.getText().toString() "+confirmPassword.getText().toString());
		System.out.println("newwPassword.getText().toString()) "+newwPassword.getText().toString());
		if (newwPassword.getText().toString()
				.equalsIgnoreCase(MyCommon.DEFAULT_STRING)) {
			Show_Alert_Dialog(R.string.M_Enter_New_Passord);
		} else if (oldPassword.getText().toString()
				.equalsIgnoreCase(MyCommon.DEFAULT_STRING)) {

			Show_Alert_Dialog(R.string.M_Enter_Old_Password);
		} else if (confirmPassword.getText().toString()
				.equalsIgnoreCase(MyCommon.DEFAULT_STRING)) {

			Show_Alert_Dialog(R.string.M_Enter_Confirm_Password);
		} else if (confirmPassword.getText().toString()
				.equalsIgnoreCase(newwPassword.getText().toString())) {
			if (kfsd.checkNetworkRechability())
				callWebService();
			else
				Show_No_Internet();

		}
		else 
		{
			Show_Alert_Dialog(R.string.Password_Does_Not_Match);	
		}

		
	}
	
	
	public void hideSoftKeyboard() {
	    if(getActivity().getCurrentFocus()!=null) {
	        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
	        inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
	    }
	}
	public void callWebService() {
		if (MySharedPref.getLanguage(getActivity()).equals(
				MyCommon.LANGUAGE_ENG)) {
			params = new RequestParams(MyCommon.WS_PARA_LANG, ""
					+ MyCommon.VALUE_ENG);

		} else {
			params = new RequestParams(MyCommon.WS_PARA_LANG, ""
					+ MyCommon.VALUE_AR);

		}

		params.put(MyCommon.WS_PARA_New_Pass, "" + newwPassword.getText()); // endDate.getText().toString()
		params.put(MyCommon.WS_PARA_Old_Pass, "" + oldPassword.getText());
		
		params.put(MyCommon.WS_PARA_CIVILID, "" + MyCommon.sLoginResponseBean.getCivilId());

		MyHttpConnection.get(MyCommon.WS_METHOD_CHANGE_PASSWORD, params,
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
			// response = MyCommon.getJsonFromWebServiceResponse(response);
			try
			{

				JSONObject json = null;
				try {
					json = new JSONObject(new String(responseBody, "UTF-8"));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				JSONObject jsonObj = json.getJSONObject("KfsdMobileChangePwd");
				final String responseFlag = jsonObj.getString("responseFlag");
				String responseMsg = jsonObj.getString("responseMsg");
				//Show_Alert_Dialog(responseMsg);

				Show_Alert_Dialog(responseMsg, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						if(getFragmentManager().getBackStackEntryCount()>0)
						{
							if(responseFlag.equalsIgnoreCase("1"))
								getActivity().onBackPressed();
//							getFragmentManager().popBackStack();
//
//							FragmentTransaction ft = getFragmentManager().beginTransaction();
//							HomeFragment bf = new HomeFragment();
//
//							System.out.println("RUpa inside on create");
//							ft.replace(R.id.groupContent, bf,
//									getResources().getString(R.string.home));
//							ft.disallowAddToBackStack();
//							ft.commit();


						}

					}
				});;



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
