package com.kuwaitKFF.correspondence;


import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.kuwaitKFF.R;
import com.kuwaitKFF.base.BaseActivity;
import com.kuwaitKFF.common.MyCommon;
import com.kuwaitKFF.common.MyHttpConnection;
import com.kuwaitKFF.main.Kfsd;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class RelatedMsgActivity extends BaseActivity implements OnItemClickListener{
	
	Kfsd kfsd;
	ListView listView;
	ArrayList<RelatedMsgBean> list;
	
	public RelatedMsgActivity(){
		super(R.layout.activity_related_msg);
	}
	public RelatedMsgActivity(int layoutResId) {
		super(R.layout.activity_related_msg);
	}

	@Override
	public void initialization() {
		ButterKnife.bind(this);
	}

	@Override
	public void implementation() {
		kfsd = (Kfsd) getApplication();
		
		listView = (ListView) findViewById(R.id.listDisplay);
		listView.setOnItemClickListener(this);
		
		if (kfsd.checkNetworkRechability())
			callWebService();
		else
			Show_No_Internet();
		
		setHeader(getResources().getString(R.string.related_msges), true, true);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		
	}

	public void callWebService() {
		String serialNo = getIntent().getStringExtra("serialNo");
		RequestParams params = new RequestParams(MyCommon.WS_PARA_SERIAL, " "+serialNo);
		
		MyHttpConnection.get(MyCommon.WS_METHOD_CMS_REL, params,
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
				JSONArray jsonArray = json.getJSONArray("KfsdMobileCMSRelated");
				list = new ArrayList<RelatedMsgBean>();

				for(int i=0; i<jsonArray.length(); i++)
				{
					JSONObject obj = jsonArray.getJSONObject(i);
					RelatedMsgBean bean = new RelatedMsgBean((String) obj.getString("CorrespondSerial"),
							(String) obj.getString("CorrespondRelatedSerial"), (String) obj.getString("CorrespondTypeCode"),
							(String) obj.getString("CorrespondTypeDescAr"), (String) obj.getString("CorrespondTypeDescEn"),
							(String) obj.getString("CorrespondFromEntityId"), (String) obj.getString("CorrespondFromEntityNameAr"),
							(String) obj.getString("CorrespondFromEntityNameEn"), (String) obj.getString("CorrespondToEntityId"),
							(String) obj.getString("CorrespondToEntityNameAr"), (String) obj.getString("CorrespondToEntityNameEn"),
							(String) obj.getString("CorrespondNo"), (String) obj.getString("CorrespondDate"),
							(String) obj.getString("CorrespondReceiveDate"), (String) obj.getString("CorrespondEntryDate"),
							(String) obj.getString("CorrespondEntityNo"), (String) obj.getString("CorrespondSubject"));
					list.add(bean);
				}

				listView.setAdapter(new RelatedMsgAdapter(kfsd, R.layout.row_related_item, list));

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
