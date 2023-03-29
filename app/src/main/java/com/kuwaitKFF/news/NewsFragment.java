package com.kuwaitKFF.news;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.kuwaitKFF.base.BaseFragment;
import com.kuwaitKFF.common.MyCommon;
import com.kuwaitKFF.common.MyHttpConnection;
import com.kuwaitKFF.common.MySharedPref;
import com.kuwaitKFF.main.Kfsd;

import com.kuwaitKFF.R;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import cz.msebera.android.httpclient.Header;

public class NewsFragment extends BaseFragment implements OnItemClickListener {

	Kfsd kfsd;
	View view;
	ArrayList<String> circularList;
	ListView listView;
	RequestParams params;
	ArrayList<NewsResponseBean> newsResponseBeansList;
	Date date;
	String formattedDate;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		kfsd = (Kfsd)getActivity().getApplication();

		view = LayoutInflater.from(getActivity()).inflate(
				R.layout.fragment_news, null);

		listView = (ListView) view.findViewById(R.id.listDisplay);

		listView.setOnItemClickListener(this);
		

		if (kfsd.checkNetworkRechability())
			callWebService();
		else
			Show_No_Internet();

		//setHeader(view, getResources().getString(R.string.login), false, false);
	
		setHeader(view, getResources().getString(R.string.news), false, false);
		

		return view;
	}

	
	public void callWebService() {

		if (MySharedPref.getLanguage(getActivity()).equals(
				MyCommon.LANGUAGE_ENG)) {

			params = new RequestParams(MyCommon.WS_PARA_LANG, ""
					+ MyCommon.VALUE_ENG);

			Log.d("ffffftttffffff",params.toString());
		}


		else {
			params = new RequestParams(MyCommon.WS_PARA_LANG, ""
					+ MyCommon.VALUE_AR);
			Log.d("ffffftttffffff",params.toString());
		}

		MyHttpConnection.get(MyCommon.WS_METHOD_NEWS, params,
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
				JSONArray jsonArray = json.getJSONArray("KfsdMobileNews");
//			Log.d("MzTag",jsonArray.toString());
				newsResponseBeansList = new ArrayList<NewsResponseBean>();

				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonObject = jsonArray.getJSONObject(i);
					NewsResponseBean newsResponseBean = new NewsResponseBean();
					newsResponseBean.setNewsSerial(jsonObject
							.getString("NewsSerial"));
					newsResponseBean.setNewsDate(jsonObject
							.getString("NewsDate"));
					newsResponseBean.setNewsSource(jsonObject
							.getString("NewsSource"));
					newsResponseBean.setNewsSubject(jsonObject
							.getString("NewsSubject"));
					newsResponseBean.setNewsBody(jsonObject
							.getString("NewsBody"));
					newsResponseBean.setNewsFilefullPath(jsonObject
							.getString("NewsFileFullPath"));
					newsResponseBean.setNewsFileExtension(jsonObject
							.getString("NewsFileExtension"));
					//Log.d("sssssssssssssssss",jsonObject
					//.getString("NewsFileFullPath"));
					newsResponseBean.setNewsLangFlag(jsonObject
							.getString("NewsLangFlag"));

					newsResponseBeansList.add(newsResponseBean);
				}


				//sort Ascending
				Collections.sort(newsResponseBeansList, new Comparator() {

					public int compare(Object o1, Object o2) {
						NewsResponseBean p1 = (NewsResponseBean) o1;
						NewsResponseBean p2 = (NewsResponseBean) o2;
						return p1.getNewsDate().compareToIgnoreCase(p2.getNewsDate());
					}
				});

				//Reverse to get descending
				Collections.reverse(newsResponseBeansList);



				listView.setAdapter(new NewsListAdapter(getActivity(), newsResponseBeansList));

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

		Intent i = new Intent();
		i.setClass(getActivity(), NewsDetailsFragment.class);
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
				| Intent.FLAG_ACTIVITY_CLEAR_TOP);
		Bundle b = new Bundle();
		b.putString("content", newsResponseBeansList.get(position).getNewsBody());
		b.putString("dateText", dateFormatForDate(newsResponseBeansList.get(position).getNewsDate()));
		b.putString("title", newsResponseBeansList.get(position).getNewsSource());
		b.putString("mainTitle", newsResponseBeansList.get(position).getNewsSubject());
		b.putString("picPath", newsResponseBeansList.get(position).getNewsFilefullPath());
         i.putExtras(b);
		startActivity(i);

	}
	
	public String dateFormatForDate(String dateVal) {


		SimpleDateFormat formatter = new SimpleDateFormat(
				"yyyy-MM-dd'T'HH:mm:ss");
		SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy/MM/dd");

		try {
			if(dateVal != null || (!dateVal.equalsIgnoreCase("null")))
			{
				date = formatter.parse(dateVal);
				formattedDate = formatter1.format(date);
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return formattedDate;

	}


}
