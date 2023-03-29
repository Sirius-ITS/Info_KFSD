package com.kuwaitKFF.advertisements;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.TextView;

import com.kuwaitKFF.R;
import com.kuwaitKFF.base.BaseFragment;
import com.kuwaitKFF.common.MyCommon;
import com.kuwaitKFF.common.MyHttpConnection;
import com.kuwaitKFF.common.MySharedPref;
import com.kuwaitKFF.main.Kfsd;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.Header;

public class AdvertisementsFragment extends BaseFragment implements OnItemClickListener  {

	Kfsd kfsd;
	View view;
	ArrayList<AdvertisementListBean> circularResponseBeansList;
	ArrayList<AdvertisementResponseBean> circularResponseInnerList;
	ExpandableListView listView;
	private RequestParams params;
	TextView title;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		kfsd = (Kfsd) getActivity().getApplication();


		view = LayoutInflater.from(getActivity()).inflate(
				R.layout.fragment_advertisements, null);
		
		listView = (ExpandableListView) view.findViewById(R.id.listDisplay);


//		title = (TextView)view.findViewById(R.id.textTitle);
//		title.setText(getResources().getString(R.string.advertisement));
//		setHeader(view, getResources().getString(R.string.advertisement), false, false);

		if (kfsd.checkNetworkRechability())
			callWebService();
		else
			Show_No_Internet();


		listView.setOnItemClickListener(this);
		listView.setOnChildClickListener(new OnChildClickListener() {
			
			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				if(!circularResponseBeansList.get(groupPosition).getAdvrtList().get(childPosition).getAdvertisementSerial().equals("0")) // (NOT) NO "advertisement" Found
				{
					AdvertisementResponseBean bean = (circularResponseBeansList.get(groupPosition).getAdvrtList().get(childPosition));
//					Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(bean.getAdvertisementFileFullPath()));
//					startActivity(browserIntent);
					Intent  i = new Intent();
					i.setClass(getActivity(), AdvertisementsDetailsFragment.class);
					i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
							| Intent.FLAG_ACTIVITY_CLEAR_TOP);
					Bundle b = new Bundle();
					b.putString("pdfUrl", bean.getAdvertisementFileFullPath());

					i.putExtras(b);
					startActivity(i);
				}

				return false;
			}
		});
		
		setHeader(view, getResources().getString(R.string.advertisement), true, true);
		listView.setOnGroupExpandListener(new OnGroupExpandListener() {
			int previousGroup = 0;

			@Override
			public void onGroupExpand(int groupPosition) {


				if (groupPosition != previousGroup)
					listView.collapseGroup(previousGroup);
				previousGroup = groupPosition;


//				Toast.makeText(getApplicationContext(),
//						listDataHeader.get(groupPosition) + " Expanded",
//						Toast.LENGTH_SHORT).show();
			}
		});



		return view;
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

		MyHttpConnection.get(MyCommon.WS_METHOD_ADVERTISEMENT, params,
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
				JSONArray jsonArray = json.getJSONArray("KfsdMobileAdvrtList");
				Log.d("MzTag",jsonArray.toString());
				circularResponseBeansList = new ArrayList<AdvertisementListBean>();

				for (int i = 0; i < jsonArray.length(); i++) {
					circularResponseInnerList = new ArrayList<AdvertisementResponseBean>();
					JSONObject jsonObject = jsonArray.getJSONObject(i);
					AdvertisementListBean circularResponseBean = new AdvertisementListBean();
					circularResponseBean.setAdvertiseTypeCode(jsonObject.getString("AdvertiseTypeCode"));
					circularResponseBean.setAdvertiseTypeDescEn(jsonObject.getString("AdvertiseTypeDescEn"));
					circularResponseBean.setAdvertiseTypeDescAr(jsonObject.getString("AdvertiseTypeDescAr"));
					JSONArray jsonA = jsonObject.getJSONArray("advrtList");
					for(int j=0; j<jsonA.length(); j++)
					{
						JSONObject json1 = jsonA.getJSONObject(j);
						AdvertisementResponseBean advrtInner = new AdvertisementResponseBean();
						advrtInner.setAdvertisementSerial(json1.getString("AdvertisementSerial"));
						advrtInner.setAdvertisementTypeCd(json1.getString("AdvertisementTypeCd"));
						advrtInner.setAdvertisementSubject(json1.getString("AdvertisementSubject"));
						advrtInner.setAdvertisementFileFullPath(json1.getString("AdvertisementFileFullPath"));
						advrtInner.setAdvertisementFileExtension(json1.getString("AdvertisementFileExtension"));
						advrtInner.setAdvertisementLangFlag(json1.getString("AdvertisementLangFlag"));
						advrtInner.setAdvertisementDate(json1.getString("AdvertiseDate"));

						circularResponseInnerList.add(advrtInner);
					}


					//				//sort Ascending
					Collections.sort(circularResponseInnerList, new Comparator() {

						public int compare(Object o1, Object o2) {
							AdvertisementResponseBean p1 = (AdvertisementResponseBean) o1;
							AdvertisementResponseBean p2 = (AdvertisementResponseBean) o2;
							return p1.getAdvertisementDate().compareToIgnoreCase(p2.getAdvertisementDate());
						}
					});

					//Reverse to get descending
					Collections.reverse(circularResponseInnerList);


					circularResponseBean.setAdvrtList(circularResponseInnerList);
					circularResponseBeansList.add(circularResponseBean);



				}




				listView.setAdapter(new AdvertisementListAdapter(getActivity(),
						circularResponseBeansList));

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
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
//		AdvertisementListAdapter circularListAdapter = (AdvertisementListAdapter) listView.getAdapter();
//	    Intent  i = new Intent();
//				i.setClass(getActivity(), AdvertisementsDetailsFragment.class);
//				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
//						| Intent.FLAG_ACTIVITY_CLEAR_TOP);
//				Bundle b = new Bundle();
//				//b.putString("pdfUrl", circularListAdapter.getItem(position).getAdvertiseTypeDescAr());
//		        
//			    i.putExtras(b);
//				startActivity(i);
		System.out.println("Rupa inside on click of item");
		
		
				
//		FragmentTransaction ft = getFragmentManager().beginTransaction();
//		CircularDetailsFragment mnFrg = new CircularDetailsFragment();
//
//		ft.replace(R.id.groupContent, mnFrg);
//		ft.addToBackStack(null);
//		ft.commit();
		
	}

	

}
