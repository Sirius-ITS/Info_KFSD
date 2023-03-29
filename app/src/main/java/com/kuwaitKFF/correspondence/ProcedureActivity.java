package com.kuwaitKFF.correspondence;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
import android.widget.ListView;

import com.kuwaitKFF.R;
import com.kuwaitKFF.base.BaseActivity;
import com.kuwaitKFF.common.MyCommon;
import com.kuwaitKFF.common.MyHttpConnection;
import com.kuwaitKFF.common.MySharedPref;
import com.kuwaitKFF.main.Kfsd;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class ProcedureActivity extends BaseActivity {
	Kfsd kfsd;
	ArrayList<CorrespondenceSnapshotBean> snapList;
	ListView listView;
	
	
	public ProcedureActivity() {
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
		
		if (kfsd.checkNetworkRechability())
			callWebService();
		else
			Show_No_Internet();
		
		setHeader(getResources().getString(R.string.procedures), true, true);
	}
	
	public void callWebService() {
		String serialNo = getIntent().getStringExtra("serialNo");
		System.out.println("Rupa serialNo "+serialNo);
		RequestParams params = new RequestParams(MyCommon.WS_PARA_REL_SERIAL," "+serialNo);
		MyHttpConnection.get(MyCommon.WS_METHOD_CMS_REL_CORRES, params,
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
				System.out.println("Rupa response " + responseBody);
				JSONObject json = null;
				try {
					json = new JSONObject(new String(responseBody, "UTF-8"));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}

				JSONArray jsonArray = json.getJSONArray("KfsdMobileSerialCorrespondence");
				Log.d("MzTag",jsonArray.toString());
				snapList = new ArrayList<CorrespondenceSnapshotBean>();
				for(int i=0; i<jsonArray.length(); i++)
				{
					JSONObject jsonObj = jsonArray.getJSONObject(i);
					CorrespondenceSnapshotBean bean = new CorrespondenceSnapshotBean(
							jsonObj.getString("CorrespondSerial"),
							jsonObj.getString("CorrespondTypeCode"),
							jsonObj.getString("CorrespondTypeDescAr"),
							jsonObj.getString("CorrespondTypeDescEn"),
							jsonObj.getString("CorrespondContCode"),
							jsonObj.getString("CorrespondContDescAr"),
							jsonObj.getString("CorrespondContDescEn"),
							jsonObj.getString("CorrespondSecurityCode"),
							jsonObj.getString("CorrespondSecurityDescAr"),
							jsonObj.getString("CorrespondSecurityDescEn"),
							jsonObj.getString("CorrespondPriorityCode"),
							jsonObj.getString("CorrespondPriorityDescAr"),
							jsonObj.getString("CorrespondPriorityDescEn"),
							jsonObj.getString("CorrespondFromEntityId"),
							jsonObj.getString("CorrespondFromEntityNameAr"),
							jsonObj.getString("CorrespondFromEntityNameEn"),
							jsonObj.getString("CorrespondToEntityId"),
							jsonObj.getString("CorrespondToEntityNameAr"),
							jsonObj.getString("CorrespondToEntityNameEn"),
							jsonObj.getString("CorrespondNo"),
							jsonObj.getString("CorrespondDate"),
							jsonObj.getString("CorrespondToEntitiesId"),
							jsonObj.getString("CorrespondToEntitiesNameAr"),
							jsonObj.getString("CorrespondToEntitiesNameEn"),
							jsonObj.getString("CorrespondCcEntitiesId"),
							jsonObj.getString("CorrespondCcEntitiesNameAr"),
							jsonObj.getString("CorrespondCcEntitiesNameEn"),
							jsonObj.getString("CorrespondReceiveDate"),
							jsonObj.getString("CorrespondEntryDate"),
							jsonObj.getString("CorrespondEntityNo"),
							jsonObj.getString("CorrespondSubject"),
							jsonObj.getString("CorrespondBody"),
							jsonObj.getString("CorrespondFlag"),
							jsonObj.getString("SnapshotSerial"),
							jsonObj.getString("SnapshotEntityId"),
							jsonObj.getString("SnapshotEmpCivilId"),
							jsonObj.getString("SnapshotToNameAr"),
							jsonObj.getString("SnapshotToNameEn"),
							jsonObj.getString("SnapshotDate"),
							jsonObj.getString("SnapshotRelatedSerial"),
							jsonObj.getString("SnapshotFromEntityId"),
							jsonObj.getString("SnapshotFromEntityNameAr"),
							jsonObj.getString("SnapshotFromEntityNameEn"),
							jsonObj.getString("SnapshotActionEmpCivilId"),
							jsonObj.getString("SnapshotActionEmpNameAr"),
							jsonObj.getString("SnapshotActionEmpNameEn"),
							jsonObj.getString("SnapshotActionCode"),
							jsonObj.getString("SnapshotActionDescAr"),
							jsonObj.getString("SnapshotActionDescEn"),
							jsonObj.getString("SnapshotActionEndDate"),
							jsonObj.getString("SnapshotActionNote"),
							jsonObj.getString("SnapshotReplyEmpCivilId"),
							jsonObj.getString("SnapshotReplyEmpNameAr"),
							jsonObj.getString("SnapshotReplyEmpNameEn"),
							jsonObj.getString("SnapshotReplyDate"),
							jsonObj.getString("SnapshotReplyNote"),
							jsonObj.getString("SnapshotFollowupFlag"),
							jsonObj.getString("SnapshotStatusCode"),
							jsonObj.getString("SnapshotStatusDescAr"),
							jsonObj.getString("SnapshotStatusDescEn"));
					snapList.add(bean);
				}


				listView.setAdapter(new ProcedureAdapter(kfsd, R.layout.rows_corres_item, snapList, MySharedPref.getLanguage(ProcedureActivity.this)));

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
