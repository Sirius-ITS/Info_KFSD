package com.kuwaitKFF.correspondence;



import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.kuwaitKFF.R;
import com.kuwaitKFF.base.BaseActivity;
import com.kuwaitKFF.circular.CircularDetailsFragment;
import com.kuwaitKFF.common.MyCommon;
import com.kuwaitKFF.common.MyHttpConnection;
import com.kuwaitKFF.main.Kfsd;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class DocumentActivity extends BaseActivity implements OnItemClickListener{
	
	Kfsd kfsd;
	ListView listView;
	ArrayList<DocumentBean> list;
	
	public DocumentActivity(){
		super(R.layout.activity_document);
	}
	
	public DocumentActivity(int layoutResId) {
		super(R.layout.activity_document);
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
		
		setHeader(getResources().getString(R.string.Documents), true, true);
	}
//
//	@Override
//	public void onItemClick(AdapterView<?> parent, View view, int position,
//			long id) {
//
//
//	}

	public void callWebService() {
		String serialNo = getIntent().getStringExtra("serialNo");
		RequestParams params = new RequestParams(MyCommon.WS_PARA_SERIAL," "+serialNo);
		System.out.println("rupa serialNo "+serialNo);
		MyHttpConnection.get(MyCommon.WS_METHOD_CMS_DOC, params,
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
			Log.d("MzTagDoc", String.valueOf(responseBody));
			// response = MyCommon.getJsonFromWebServiceResponse(response);
			try {
				JSONObject json = null;
				try {
					json = new JSONObject(new String(responseBody, "UTF-8"));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}

				JSONArray jsonArray = json.getJSONArray("KfsdMobileCMSDocument");
				Log.d("MzTagDoc",jsonArray.toString());
				list = new ArrayList<DocumentBean>();
				for(int i=0; i<jsonArray.length(); i++)
				{
					JSONObject obj = jsonArray.getJSONObject(i);
					DocumentBean bean = new DocumentBean((String) obj.getString("CorrespondDocSerial"),
							(String) obj.getString("CorrespondSerial"), (String) obj.getString("CorrespondDocDate"),
							(String) obj.getString("CorrespondDocFilePath"), (String) obj.getString("CorrespondDocFileName"),
							(String) obj.getString("CorrespondDocFileExtension"), (String) obj.getString("CorrespondDescAr"),
							(String) obj.getString("CorrespondDescEn"));
					list.add(bean);

					Log.d("MzTagDoc", bean.getCorrespondDocFilePath());
					Log.d("MzTagDoc", bean.getCorrespondDocSerial());
				}

				listView.setAdapter(new DocumentAdapter(kfsd, R.layout.rows_corres_item, list));

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


	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

		DocumentAdapter documentAdapter = (DocumentAdapter) listView.getAdapter();


		if (!documentAdapter.getItem(position).getCorrespondDocSerial().equals("0")) {


			Intent i = new Intent();
			i.setClass(kfsd, CircularDetailsFragment.class);
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
					| Intent.FLAG_ACTIVITY_CLEAR_TOP);
			Bundle b = new Bundle();
			Log.d("MzTagDoc",documentAdapter.getItem(position).getCorrespondDocFilePath());

			b.putString("pdfUrl", documentAdapter.getItem(position).getCorrespondDocFilePath());
			b.putString("docFlag", "1");

			i.putExtras(b);
			startActivity(i);

		}
	}
}
