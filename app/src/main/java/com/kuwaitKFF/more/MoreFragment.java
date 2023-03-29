package com.kuwaitKFF.more;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import com.kuwaitKFF.base.BaseFragment;
import com.kuwaitKFF.common.MyCommon;
import com.kuwaitKFF.common.MyHttpConnection;
import com.kuwaitKFF.common.MySharedPref;
import com.kuwaitKFF.main.Kfsd;
import com.kuwaitKFF.R;
import com.loopj.android.http.AsyncHttpResponseHandler;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import cz.msebera.android.httpclient.Header;

public class MoreFragment extends BaseFragment  {

	Kfsd kfsd;
	View view;
	ArrayList<String> listDataHeader;

	
	MoreExpandableListAdapter listAdapter;
	ExpandableListView expListView;

	HashMap<String, ArrayList<String>> listDataChild;
	FragmentTransaction ft;
	FragmentManager mFragmentManager;
	Intent i;
	String responseMsg;
	int responseFlag;
	TextView textTitle;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		kfsd = (Kfsd) getActivity().getApplication();


		if(MySharedPref.getLanguage(getActivity()).equals(MyCommon.LANGUAGE_ENG))
			MyCommon.saveSelectedLanguage(kfsd, MyCommon.LANGUAGE_ENG);
		else
			MyCommon.saveSelectedLanguage(kfsd, MyCommon.LANGUAGE_AR);

		view = LayoutInflater.from(getActivity()).inflate(
				R.layout.fragment_advertisements, null);
		textTitle = (TextView) view.findViewById(R.id.textTitle);
		textTitle.setVisibility(View.GONE);
		
		if (kfsd.checkNetworkRechability())
		{
			
		    //callWebService();
		}
		else
			Show_No_Internet();
	
		expListView = (ExpandableListView) view.findViewById(R.id.listDisplay);
		expListView.setOnGroupClickListener(new OnGroupClickListener() {

			@Override
			public boolean onGroupClick(ExpandableListView parent, View v,
					int groupPosition, long id) {
				
				if(groupPosition ==1)
				{
					
					i = new Intent();
					i.setClass(getActivity(), MapActivityFragment.class);
					i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
							| Intent.FLAG_ACTIVITY_CLEAR_TOP);

					startActivity(i);

								
				}else if(groupPosition ==2)	
				{
					
					i = new Intent();
					i.setClass(getActivity(), WhoWeAreFragment.class);
					i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
							| Intent.FLAG_ACTIVITY_CLEAR_TOP);

					startActivity(i);
					
				}else if(groupPosition ==3)	
				{
				    ft = getFragmentManager().beginTransaction();
					SettingsFragment mnFrg2 = new SettingsFragment();
	
					ft.replace(R.id.groupContent, mnFrg2);
					ft.addToBackStack(null);
					ft.commit();
	
			}
	
			  return false;

			}
		});

		// Listview Group expanded listener
		expListView.setOnGroupExpandListener(new OnGroupExpandListener() {
			 int previousGroup = 0;

			@Override
			public void onGroupExpand(int groupPosition) {
				
				
				if(groupPosition != previousGroup)
					expListView.collapseGroup(previousGroup);
	            previousGroup = groupPosition;
				
			
//				Toast.makeText(getApplicationContext(),
//						listDataHeader.get(groupPosition) + " Expanded",
//						Toast.LENGTH_SHORT).show();
			}
		});

		// Listview Group collasped listener
		expListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {

			@Override
			public void onGroupCollapse(int groupPosition) {
				
			
//				Toast.makeText(getApplicationContext(),
//						listDataHeader.get(groupPosition) + " Collapsed",
//						Toast.LENGTH_SHORT).show();

			}
		});

		// Listview on child click listener
		expListView.setOnChildClickListener(new OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				if(childPosition == 0){
					System.out.println("RUPA in child position");
					i = new Intent();
					i.putExtra("lang", MySharedPref.getLanguage(getActivity()));
					i.setClass(getActivity(), PhoneEmailFragment.class);
					i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
							| Intent.FLAG_ACTIVITY_CLEAR_TOP);

					startActivity(i);
				}
				if(childPosition ==1){
					i = new Intent();
					i.putExtra("lang", MySharedPref.getLanguage(getActivity()));
					i.setClass(getActivity(), ContactUsFragment.class);
					i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
							| Intent.FLAG_ACTIVITY_CLEAR_TOP);

					startActivity(i);


	
			}	
				if(childPosition ==2){
					if (kfsd.checkNetworkRechability())
					{
						callWebService();
					}
					else
						Show_No_Internet();
	
			}	

			if(childPosition ==3){
				i = new Intent();
				i.setClass(getActivity(), SocialMediaFragment.class);
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
						| Intent.FLAG_ACTIVITY_CLEAR_TOP);

				startActivity(i);

			}	

				
			  return true;
			}
		});
       
		

		listDataHeader = new ArrayList<String>();
		listDataChild = new HashMap<String, ArrayList<String>>();
		
		populatedata();
		
		listAdapter = new MoreExpandableListAdapter(getActivity(), listDataHeader, listDataChild);

		// setting list adapter
		expListView.setAdapter(listAdapter);

		setHeader(view, getResources().getString(R.string.more), false, false);

		return view;
	}
	
	
	
	public void callWebService() {


		MyHttpConnection.get(MyCommon.WS_METHOD_WEBSITE,
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
				JSONObject jsonObj = json.getJSONObject("KfsdMobileWebsite");
				responseFlag = jsonObj.getInt("responseFlag");
				responseMsg = jsonObj.getString("responseMsg");
				if(responseMsg != null && !responseMsg.equalsIgnoreCase("null"))
				{

					Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(responseMsg));
					startActivity(browserIntent);
				}else
				{

					Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));
					startActivity(browserIntent);
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



	private void populatedata() {
		listDataHeader.add(getResources().getString(R.string.contact_us));
		listDataHeader.add(getResources().getString(R.string.map));
		listDataHeader.add(getResources().getString(R.string.who_we_are));
		listDataHeader.add(getResources().getString(R.string.setting));
		
		
		
		ArrayList<String> top250 = new ArrayList<String>();
		top250.add(getResources().getString(R.string.phone_mails));
		top250.add(getResources().getString(R.string.suggestion));
		top250.add(getResources().getString(R.string.website));
		top250.add(getResources().getString(R.string.social_media));
		
		ArrayList<String> nowShowing = new ArrayList<String>();
//		nowShowing.add("Text1");
//		nowShowing.add("Text2");
//		nowShowing.add("Text3");
//		nowShowing.add("Text4");

		ArrayList<String> comingSoon = new ArrayList<String>();
//		comingSoon.add("Text1");
//		comingSoon.add("Text2");
//		comingSoon.add("Text3");
		
		ArrayList<String> settings = new ArrayList<String>();

		listDataChild.put(listDataHeader.get(0), top250); // Header, Child data
		 listDataChild.put(listDataHeader.get(1), nowShowing);
		 listDataChild.put(listDataHeader.get(2), comingSoon);
		 listDataChild.put(listDataHeader.get(3), settings);

			
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

		

}
