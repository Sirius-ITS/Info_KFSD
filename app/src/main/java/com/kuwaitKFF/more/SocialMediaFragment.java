package com.kuwaitKFF.more;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ExpandableListView;
import android.widget.ImageView;

import com.kuwaitKFF.R;
import com.kuwaitKFF.base.BaseActivity;
import com.kuwaitKFF.common.MyCommon;
import com.kuwaitKFF.common.MyHttpConnection;
import com.kuwaitKFF.main.Kfsd;
import com.loopj.android.http.AsyncHttpResponseHandler;

import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class SocialMediaFragment extends BaseActivity implements OnClickListener  {

	Kfsd kfsd;
	View view;
	ArrayList<String> listDataHeader;
	ImageView youTube,twitter,instagram;
	MoreExpandableListAdapter listAdapter;
	ExpandableListView expListView;

	HashMap<String, ArrayList<String>> listDataChild;
	
	ArrayList<SocialMediaResponseBean> socialMediaResponseBeansList;
	
	Intent browserIntent;
	
	
	
	public SocialMediaFragment() {
		super(R.layout.fragment_share);
	}

	@Override
	public void initialization() {
		ButterKnife.bind(this);

	}

	@Override
	public void implementation() {
		kfsd = (Kfsd) getApplication();
		
//		youTube = (ImageView) findViewById(R.id.youTubeButton);
//		youTube.setOnClickListener(this);
//
//
//		twitter = (ImageView) findViewById(R.id.twitterButton);
//		twitter.setOnClickListener(this);
//
//
//		instagram = (ImageView) findViewById(R.id.instagramButton);
//		instagram.setOnClickListener(this);

		findViewById(R.id.youTubeLayout).setOnClickListener(this);
		findViewById(R.id.twitterLayout).setOnClickListener(this);
		findViewById(R.id.instagramLayout).setOnClickListener(this);

		
		if (kfsd.checkNetworkRechability())
			callWebService();
		else
			Show_No_Internet();


		setHeader(getResources().getString(R.string.social_media), true, true);

	}

	public void callWebService() {

		MyHttpConnection.get(MyCommon.WS_METHOD_SOCIAL_MEDIA,
				asyncResponseHandler);

	}

	AsyncHttpResponseHandler asyncResponseHandler = new AsyncHttpResponseHandler() {

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
				int result = json.getInt("success");
				if(result==0)
				{
					Show_Alert_Dialog(getResources().getString(R.string.No_data_available));
				}else
				{


					JSONArray jsonArray = json.getJSONArray("KfsdMobileSocialMedia");
					socialMediaResponseBeansList = new ArrayList<SocialMediaResponseBean>();

					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject jsonObject = jsonArray.getJSONObject(i);
						SocialMediaResponseBean socialMediaResponseBean = new SocialMediaResponseBean();
						socialMediaResponseBean.setMediaCode(jsonObject
								.getString("SocialMediaTypeCode"));
						socialMediaResponseBean.setMediaDescAr(jsonObject
								.getString("SocialMediaTypeDescAr"));

						socialMediaResponseBean.setMediaDescEn(jsonObject
								.getString("SocialMediaTypeDescEn"));

						socialMediaResponseBean.setMediaLink(jsonObject
								.getString("SocialMediaLink"));


						socialMediaResponseBeansList.add(socialMediaResponseBean);

					}

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

				finish();

			} catch (Exception e) {
				e.printStackTrace();
			}

			return true;

		}

		return false;
	}

	@Override
	public void onClick(View v) {

		Log.d("MzTag","onClick(111)");

		switch (v.getId()) {
			case R.id.youTubeLayout:

          	 browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(socialMediaResponseBeansList.get(0).getMediaLink()));
			startActivity(browserIntent);
			break;
			
		case R.id.twitterLayout:

          	browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(socialMediaResponseBeansList.get(1).getMediaLink()));
			startActivity(browserIntent);
			break;
			
		case R.id.instagramLayout:

          	browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(socialMediaResponseBeansList.get(2).getMediaLink()));
			startActivity(browserIntent);
			break;

		default:
			break;
		}
		
	}

		

}
