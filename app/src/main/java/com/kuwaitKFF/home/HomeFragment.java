package com.kuwaitKFF.home;

import com.kuwaitKFF.base.BaseFragment;
import com.kuwaitKFF.base.TabbarActivity;
import com.kuwaitKFF.common.MyCommon;
import com.kuwaitKFF.common.MyHttpConnection;
import com.kuwaitKFF.common.MySharedPref;
import com.kuwaitKFF.login.LoginFragment;
import com.kuwaitKFF.login.LoginResponseBean;
import com.kuwaitKFF.main.Kfsd;

import com.kuwaitKFF.R;
import com.kuwaitKFF.menu.MenuFragment;
import com.kuwaitKFF.splash.SplashActivity;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.FragmentTransaction;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;

public class HomeFragment extends BaseFragment implements OnClickListener {

	Kfsd kfsd;
	View view;
	ImageView imageView1;
	ImageView imageView2;
	ImageView imageView3;
	ImageView imageLine2,imageLine3;

	LinearLayout relative1;
	RelativeLayout.LayoutParams params;
	FragmentTransaction ft;
	
	TextView text1,text2, text3, text4;
	float img3LeftMargin, img3TopMargin;
	boolean openCloseFlag = false;
	Animation animate = new AlphaAnimation(0, 1);
	Intent  i;

	boolean showNewScreensFlag = false;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		kfsd = (Kfsd) getActivity().getApplication();



		if(MySharedPref.getLanguage(getActivity()).equals(MyCommon.LANGUAGE_ENG))
		{
			saveSelectedLanguage(MyCommon.LANGUAGE_ENG);
			Log.d("MzTag333", "English");
		}else
		{
			saveSelectedLanguage(MyCommon.LANGUAGE_AR);
			Log.d("MzTag333", "Arabic");
		}


		view = LayoutInflater.from(getActivity()).inflate(
				R.layout.fragment_home, null);


		imageView1 = (ImageView) view.findViewById(R.id.imageView1);
		imageView1.setOnClickListener(this);
		imageView2 = (ImageView) view.findViewById(R.id.imageView2);
		imageView2.setOnClickListener(this);

		imageLine2 = (ImageView) view.findViewById(R.id.imageLine2);
		imageLine3 = (ImageView) view.findViewById(R.id.imageLine3);


		imageView3 = (ImageView) view.findViewById(R.id.imageView3);
		relative1 = (LinearLayout) view.findViewById(R.id.relative1);
		
		text1 = (TextView) view.findViewById(R.id.textView1);
		text1.setOnClickListener(this);

		text2 = (TextView) view.findViewById(R.id.textView2);
		text2.setOnClickListener(this);

		text3 = (TextView) view.findViewById(R.id.textView3);
		text3.setOnClickListener(this);

		text4 = (TextView) view.findViewById(R.id.textView4);
		text4.setOnClickListener(this);
		
		animate.setInterpolator(new AccelerateDecelerateInterpolator());
		animate.setDuration(1400);

		if(MySharedPref.getLanguage(getActivity()).equals(MyCommon.LANGUAGE_ENG))
		{
			Log.d("MzTag33", "English");
			text1.setGravity(Gravity.LEFT);
			text2.setGravity(Gravity.LEFT);
			text3.setGravity(Gravity.LEFT);
			text4.setGravity(Gravity.LEFT);

		}else
		{
			Log.d("MzTag33", "Arabic");
			text1.setGravity(Gravity.RIGHT);
			text2.setGravity(Gravity.RIGHT);
			text3.setGravity(Gravity.RIGHT);
			text4.setGravity(Gravity.RIGHT);
		}

		 setHeader(view, getResources().getString(R.string.kuwait_fire_service_directorate), false,false);


		Resources r = getResources();
		 img3LeftMargin = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 110, r.getDisplayMetrics());

		 img3TopMargin = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, r.getDisplayMetrics());

		 if (MyCommon.refresh) {
			 Resources res = view.getResources();

			 // Change locale settings in the application.
			 if (MySharedPref.getLanguage(getActivity()).equals(
					 MyCommon.LANGUAGE_AR)) {
				 DisplayMetrics dm = res.getDisplayMetrics();
				 android.content.res.Configuration conf = res.getConfiguration();
				 conf.locale = new Locale("ar");
				 res.updateConfiguration(conf, dm);
				 view.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
				 Intent i = new Intent();
				 i.setClass(view.getContext(), TabbarActivity.class);
				 i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
						 | Intent.FLAG_ACTIVITY_CLEAR_TOP);
				 i.putExtra(TabbarActivity.TAB_ID, new Integer(3));
				 startActivity(i);
				 getActivity().finish();
			 } else {
				 DisplayMetrics dm = res.getDisplayMetrics();
				 android.content.res.Configuration conf = res.getConfiguration();
				 conf.locale = new Locale("en");
				 res.updateConfiguration(conf, dm);
				 view.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
				 Intent i = new Intent();
				 i.setClass(view.getContext(), TabbarActivity.class);
				 i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
						 | Intent.FLAG_ACTIVITY_CLEAR_TOP);
				 i.putExtra(TabbarActivity.TAB_ID, new Integer(0));
				 startActivity(i);
				 getActivity().finish();
			 }
			 MyCommon.refresh = false;
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
		case R.id.imageView1:
			if (openCloseFlag) {
//				imageView1.setAnimation(AnimationUtils.loadAnimation(getActivity(),
//                        R.anim.move_down));
//				imageView2.setAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.move));
//				imageView3.setAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.move));

				relative1.setVisibility(View.GONE);




				RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)imageView3.getLayoutParams();
				params.setMargins((int)img3LeftMargin, (int)img3TopMargin, 0, 0); //substitute parameters for left, top, right, bottom
				params.removeRule(RelativeLayout.BELOW);
				imageView3.setLayoutParams(params);



				openCloseFlag = false;
			} else {

				RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)imageView3.getLayoutParams();
				params.setMargins((int)img3LeftMargin, 0, 0, 0); //substitute parameters for left, top, right, bottom
				params.addRule(RelativeLayout.BELOW, R.id.relative1);
				imageView3.setLayoutParams(params);

				relative1.setVisibility(View.VISIBLE);



				//relative1.startAnimation(animate);
//				imageView1.setAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.move));
//				imageView2.setAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.move_down));
//				imageView3.setAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.move_down));

				openCloseFlag = true;
			}

			if(!showNewScreensFlag)
			{
				imageLine2.setVisibility(View.GONE);
				imageLine3.setVisibility(View.GONE);
				text3.setVisibility(View.GONE);
				text4.setVisibility(View.GONE);
			}

			break;

		case R.id.imageView2:

			FragmentTransaction ft = getFragmentManager().beginTransaction();

			SharedPreferences prefs = getActivity().getSharedPreferences("com.kfsd", Context.MODE_PRIVATE);

			String userName = prefs.getString("com.kfsd.userName", null);
			String password = prefs.getString("com.kfsd.Password", null);

			LoginFragment mnFrg = new LoginFragment();

			if(userName == null || password == null) {
				ft.replace(R.id.groupContent, mnFrg);
				ft.addToBackStack(null);
				ft.commit();
			}
			else
			{
				RequestParams params = new RequestParams(MyCommon.WS_PARA_Pass, ""
						+ password);
				params.put(MyCommon.WS_PARA_UserId,
						"" + userName);


				MyHttpConnection.get(MyCommon.WS_METHOD_LOGIN, params,
						asyncLoginResponseHandler);


			}
			
			break;
			
		case R.id.textView1:

		  //   i = new Intent();
				//	i.setClass(getActivity(), ClearanceFragment.class);
				//	i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
					//		| Intent.FLAG_ACTIVITY_CLEAR_TOP);
				
					//startActivity(i);



			Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://services.kfsd.gov.kw"));
			startActivity(browserIntent);
			break;
			
		case R.id.textView2:
			Intent browserIntentt = new Intent(Intent.ACTION_VIEW, Uri.parse("https://pservices.kff.gov.kw/kfsdpub/faces/home"));
			startActivity(browserIntentt);
		    //  i = new Intent();
				//	i.setClass(getActivity(), SectorFragment.class);
					//i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
							//| Intent.FLAG_ACTIVITY_CLEAR_TOP);
				
					//startActivity(i);
			
			break;

			case R.id.textView3:

				i = new Intent();
				i.setClass(getActivity(), PAIAdoptionFragment.class);
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
						| Intent.FLAG_ACTIVITY_CLEAR_TOP);

				startActivity(i);

				break;

			case R.id.textView4:

				i = new Intent();
				i.setClass(getActivity(), MOCIAdoptionFragment.class);
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
						| Intent.FLAG_ACTIVITY_CLEAR_TOP);

				startActivity(i);

				break;
			

		default:
			break;
		}

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

					FragmentTransaction ft = getFragmentManager().beginTransaction();
					MenuFragment mnFrg = new MenuFragment();
					ft.replace(R.id.groupContent, mnFrg);
					ft.addToBackStack(null);
					ft.commit();

				}else
				{
					SharedPreferences prefs = getActivity().getSharedPreferences("com.kfsd", Context.MODE_PRIVATE);
					prefs.edit().clear().apply();

					FragmentTransaction ft = getFragmentManager().beginTransaction();
					LoginFragment mnFrg = new LoginFragment();
					ft.replace(R.id.groupContent, mnFrg);
					ft.addToBackStack(null);
					ft.commit();
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


	private void saveSelectedLanguage(String language) {
		Resources res = kfsd.getResources();

		// Change locale settings in the application.
		DisplayMetrics dm = res.getDisplayMetrics();
		android.content.res.Configuration conf = res.getConfiguration();
		conf.locale = new Locale(language);
		res.updateConfiguration(conf, dm);
	}
}
