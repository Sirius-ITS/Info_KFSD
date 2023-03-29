package com.kuwaitKFF.menu;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.kuwaitKFF.base.BaseFragment;
import com.kuwaitKFF.changepassword.ChangePasswordFragment;
import com.kuwaitKFF.circular.CircularFragment;
import com.kuwaitKFF.common.MyCommon;
import com.kuwaitKFF.common.MyHttpConnection;
import com.kuwaitKFF.common.MySharedPref;
import com.kuwaitKFF.correspondence.CorrespondenceFragment;
import com.kuwaitKFF.decisions.DecisionsFragment;
import com.kuwaitKFF.home.HomeFragment;
import com.kuwaitKFF.newscreens.FragmentLeaveInquiry;
import com.kuwaitKFF.newscreens.FragmentLeaveRequest;
import com.kuwaitKFF.newscreens.FragmentPermissionInquiry;
import com.kuwaitKFF.newscreens.FragmentPermissionRequest;
import com.kuwaitKFF.login.FragmentAttendance;
import com.kuwaitKFF.login.ProfileFragment;
import com.kuwaitKFF.main.Kfsd;
import com.kuwaitKFF.task.FragmentTask;

import com.kuwaitKFF.R;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;

public class MenuFragment extends BaseFragment implements OnClickListener {

	Kfsd kfsd;
	View view;
	TextView textView1;
	TextView textView2;
	TextView textView3;
	TextView textView4;
	TextView textView5;
	TextView textView7;
	TextView textView8;
	TextView textView9;
	TextView textView6;

	TextView permReqTxt;
	TextView leaveReqTxt;
	TextView leaveInquiryTxt;
	TextView permInquiryTxt;

	RelativeLayout permRequestRel;
	RelativeLayout leaveRequestRel;
	RelativeLayout leaveInquiryRel;
	RelativeLayout permInquiryRel;
	TextView counterText;
	FragmentTransaction ft;
	Fragment mnFrg;
	Intent i;
	int counter;
	int responseFlag;
	String responseMsg;

	boolean showNewScreensFlag = false;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		kfsd = (Kfsd) getActivity().getApplication();

		view = LayoutInflater.from(getActivity()).inflate(
				R.layout.fragment_menu, null);

		permRequestRel = (RelativeLayout) view.findViewById(R.id.relative1m);
		leaveRequestRel = (RelativeLayout) view.findViewById(R.id.relative2m);
		permInquiryRel = (RelativeLayout) view.findViewById(R.id.relative3m);
		leaveInquiryRel = (RelativeLayout) view.findViewById(R.id.relative4m);

		if(!showNewScreensFlag)
		{
			permRequestRel.setVisibility(View.GONE);
			leaveRequestRel.setVisibility(View.GONE);
			permInquiryRel.setVisibility(View.GONE);
			leaveInquiryRel.setVisibility(View.GONE);
		}

		permReqTxt = (TextView) view.findViewById(R.id.textView3m);
		permReqTxt.setOnClickListener(this);

		leaveReqTxt = (TextView) view.findViewById(R.id.textView4m);
		leaveReqTxt.setOnClickListener(this);

		permInquiryTxt = (TextView) view.findViewById(R.id.textView5m);
		permInquiryTxt.setOnClickListener(this);

		leaveInquiryTxt = (TextView) view.findViewById(R.id.textView6m);
		leaveInquiryTxt.setOnClickListener(this);

		textView1 = (TextView) view.findViewById(R.id.textView1);
		textView1.setOnClickListener(this);
		textView2 = (TextView) view.findViewById(R.id.textView2);
		textView2.setOnClickListener(this);
		textView3 = (TextView) view.findViewById(R.id.textView3);
		textView3.setOnClickListener(this);
		textView4 = (TextView) view.findViewById(R.id.textView4);
		textView4.setOnClickListener(this);
		textView5 = (TextView) view.findViewById(R.id.textView5);
		textView5.setOnClickListener(this);

		textView6 = (TextView) view.findViewById(R.id.textView6);
		textView6.setOnClickListener(this);
		textView7 = (TextView) view.findViewById(R.id.textView7);
		textView7.setOnClickListener(this);
		textView8 = (TextView) view.findViewById(R.id.textView8);
		textView8.setOnClickListener(this);
		textView9 = (TextView) view.findViewById(R.id.textView9);
		textView9.setOnClickListener(this);
		
		counterText = (TextView) view.findViewById(R.id.textViewCounter);

//		Log.d("MzTag22", "English Name: " + MyCommon.sLoginResponseBean.getNameEn());
//		Log.d("MzTag22", "Arabic Name: " + MyCommon.sLoginResponseBean.getNameAr());


		if (MyCommon.sLoginResponseBean != null) {
			String name;
			if (MySharedPref.getLanguage(getActivity()).equals(MyCommon.LANGUAGE_ENG)) {
				name = MyCommon.sLoginResponseBean.getNameEn();
				if (name.equals("null")) {
					name = "";
					MyCommon.sLoginResponseBean.setNameEn("");
				}
			} else {
				name = MyCommon.sLoginResponseBean.getNameAr();
				if (name.equals("null")) {
					name = "";
					MyCommon.sLoginResponseBean.setNameAr("");
				}
			}

			setHeader(view, getResources().getString(R.string.welcome) + " " + name, true, true);
		}


		if (kfsd.checkNetworkRechability())
		{
			callWebService();
		    callMailWebService();
		}
		else
			Show_No_Internet();


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

			if (MySharedPref.getLanguage(getActivity()).equals(
					MyCommon.LANGUAGE_ENG))
				txtTitle.setCompoundDrawablesWithIntrinsicBounds( 0, 0, R.drawable.ic_perm_identity_white_18dp, 0);
			else
				txtTitle.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_perm_identity_white_18dp, 0, 0, 0);

			txtTitle.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					
					Intent i = new Intent();
					i.setClass(getActivity(), ProfileFragment.class);
					i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
							| Intent.FLAG_ACTIVITY_CLEAR_TOP);
				
					startActivity(i);


				}
			});

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

							FragmentTransaction ft = getFragmentManager().beginTransaction();
							HomeFragment bf = new HomeFragment();

							SharedPreferences prefs = getActivity().getSharedPreferences("com.kfsd", Context.MODE_PRIVATE);
							prefs.edit().clear().apply();

							System.out.println("RUpa inside on create");
							ft.replace(R.id.groupContent, bf,
									getResources().getString(R.string.home));
							ft.disallowAddToBackStack();
							ft.commit();
							
							
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

	public void callWebService() {

		RequestParams	params = new RequestParams(MyCommon.WS_PARA_LANG, ""
					+ MyCommon.sLoginResponseBean.getCivilId());

		MyHttpConnection.get(MyCommon.WS_METHOD_CORRESPONDENCE_COUNTER, params,
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
			try {

				JSONObject json = null;
				try {
					json = new JSONObject(new String(responseBody, "UTF-8"));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				JSONObject jsonObj = json.getJSONObject("KfsdMobileCorresCount");
				counter = jsonObj.getInt("corresCount");

				if(counter>0)
				{
					counterText.setVisibility(View.VISIBLE);
					counterText.setText(String.valueOf(counter));

				}else
				{
					counterText.setVisibility(View.GONE);
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


	
	public void callMailWebService() {

		RequestParams	params = new RequestParams(MyCommon.WS_PARA_EMPID, ""
					+ MyCommon.sLoginResponseBean.getCivilId());

		MyHttpConnection.get(MyCommon.WS_METHOD_MAIL, params,
				asyncMailResponseHandler);

	}

	AsyncHttpResponseHandler asyncMailResponseHandler = new AsyncHttpResponseHandler() {


		@Override
		public void onFinish() {
			DismissProgress();
			super.onFinish();
		}

		@Override
		public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
			// response = MyCommon.getJsonFromWebServiceResponse(response);
			try {

				JSONObject json = null;
				try {
					json = new JSONObject(new String(responseBody, "UTF-8"));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				JSONArray jsonArray = json.getJSONArray("KfsdMobileContactUs");
				JSONObject jsonObj = jsonArray.getJSONObject(0);
				responseFlag = jsonObj.getInt("responseFlag");
				responseMsg = jsonObj.getString("responseMsg");

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

		FragmentTransaction ft = getFragmentManager().beginTransaction();
		SubMenuFragment subMenuFrg = new SubMenuFragment();
		Bundle bundle = new Bundle();

		switch (v.getId()) {
		case R.id.textView1:
			bundle.putInt("menuType", 3);
			subMenuFrg.setArguments(bundle);
			ft.replace(R.id.groupContent, subMenuFrg);
			ft.addToBackStack(null);
			ft.commit();

			break;

		case R.id.textView2:
			bundle.putInt("menuType", 1);
			subMenuFrg.setArguments(bundle);
			ft.replace(R.id.groupContent, subMenuFrg);
			ft.addToBackStack(null);
			ft.commit();

			break;

		case R.id.textView3:

            bundle.putInt("menuType", 2);
			subMenuFrg.setArguments(bundle);
			ft.replace(R.id.groupContent, subMenuFrg);
			ft.addToBackStack(null);
			ft.commit();

			break;


		case R.id.textView3m:
				i = new Intent();
				i.setClass(getActivity(), FragmentPermissionRequest.class);
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
						| Intent.FLAG_ACTIVITY_CLEAR_TOP);

				startActivity(i);
				break;

		case R.id.textView4m:
				i = new Intent();
				i.setClass(getActivity(), FragmentLeaveRequest.class);
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
						| Intent.FLAG_ACTIVITY_CLEAR_TOP);

				startActivity(i);
				break;

		case R.id.textView5m:
				i = new Intent();
				i.setClass(getActivity(), FragmentPermissionInquiry.class);
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
						| Intent.FLAG_ACTIVITY_CLEAR_TOP);

				startActivity(i);
				break;

		case R.id.textView6m:
				i = new Intent();
				i.setClass(getActivity(), FragmentLeaveInquiry.class);
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
						| Intent.FLAG_ACTIVITY_CLEAR_TOP);

				startActivity(i);
				break;


		case R.id.textView4:
			i = new Intent();
			i.setClass(getActivity(), FragmentTask.class);
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
					| Intent.FLAG_ACTIVITY_CLEAR_TOP);

			startActivity(i);

			break;

		case R.id.textView5:
			//ft = getFragmentManager().beginTransaction();
			i = new Intent(getActivity(), CorrespondenceFragment.class);
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
			| Intent.FLAG_ACTIVITY_CLEAR_TOP);

			startActivity(i);
			
//			mnFrg = new CorrespondenceFragment();
//
//			ft.replace(R.id.groupContent, mnFrg);
//			ft.addToBackStack(null);
//			ft.commit();

			break;
			
		case R.id.textView6:
			if(responseMsg == null || responseMsg.equalsIgnoreCase("null"))
			{
				Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));
				startActivity(browserIntent);
			}
			else
			{
				Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(responseMsg));
				startActivity(browserIntent);
			}

			break;

		case R.id.textView7:
			ft = getFragmentManager().beginTransaction();
			mnFrg = new CircularFragment();

			ft.replace(R.id.groupContent, mnFrg);
			ft.addToBackStack(null);
			ft.commit();

			break;

		case R.id.textView8:
			ft = getFragmentManager().beginTransaction();
			mnFrg = new DecisionsFragment();

			ft.replace(R.id.groupContent, mnFrg);
			ft.addToBackStack(null);
			ft.commit();

			break;

		case R.id.textView9:

//			i = new Intent();
//			i.setClass(getActivity(), ChangePasswordFragment.class);
//			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
//					| Intent.FLAG_ACTIVITY_CLEAR_TOP);

			//startActivity(i);
			 ft = getFragmentManager().beginTransaction();
			 mnFrg = new ChangePasswordFragment();
			
			 ft.replace(R.id.groupContent, mnFrg);
			 ft.addToBackStack(null);
			 ft.commit();

			break;

		default:
			break;
		}

	}

}
