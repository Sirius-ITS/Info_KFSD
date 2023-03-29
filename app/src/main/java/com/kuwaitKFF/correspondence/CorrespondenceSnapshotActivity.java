package com.kuwaitKFF.correspondence;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
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

public class CorrespondenceSnapshotActivity extends BaseActivity implements
		OnItemClickListener {

	ListView listView;
	ArrayList<CorrespondenceSnapshotBean> correspondanceList;

	public CorrespondenceSnapshotActivity() {
		super(R.layout.activity_circular_snapshot);
	}
	
	public CorrespondenceSnapshotActivity(int layoutResId) {
		super(R.layout.activity_circular_snapshot);
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
		listView.setFastScrollEnabled(true);

		callWebService();
		setHeader(getResources().getString(R.string.correspondence), true, true);
	}

	public void callWebService() {

		if (getIntent().getStringExtra("entityId") != null) {
			System.out.println("Rupa inside if "
					+ getIntent().getStringExtra("entityId"));
			RequestParams params = new RequestParams();
			String entity = getIntent().getStringExtra("entityId");
			System.out.println("Rupa entity "+entity);
			params.put(MyCommon.WS_PARA_ENTITYID, ""
					+ entity);

			MyHttpConnection.get(MyCommon.WS_METHOD_CORRES_ENTITY, params,
					asyncSnapshotResponseHandler);

		}

	}

	AsyncHttpResponseHandler asyncSnapshotResponseHandler = new AsyncHttpResponseHandler() {

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
				JSONArray jsonArray = json
						.getJSONArray("KfsdMobileCorrespondence");
				correspondanceList = new ArrayList<CorrespondenceSnapshotBean>();

				Log.d("MzTagCOrr", jsonArray.toString());

				System.out.println("Rupa inside "+jsonArray.length());
				for (int i = 0; i < jsonArray.length(); i++) {
					System.out.println("Rupa inside for");
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

					correspondanceList.add(bean);
				}

				//sort Ascending
				Collections.sort(correspondanceList, new Comparator() {

					public int compare(Object o1, Object o2) {
						CorrespondenceSnapshotBean p1 = (CorrespondenceSnapshotBean) o1;
						CorrespondenceSnapshotBean p2 = (CorrespondenceSnapshotBean) o2;
						return p1.getSnapshotDate().compareToIgnoreCase(p2.getSnapshotDate());
					}
				});

				//Reverse to get descending
				Collections.reverse(correspondanceList);

				String lang = MySharedPref
						.getLanguage(CorrespondenceSnapshotActivity.this);
				listView.setAdapter(new CorrespondenceSnapshotAdapter(
						getApplicationContext(), R.layout.row_snapshot_item,
						correspondanceList, lang));

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
			System.out.println("RUPA inside start");
			DisplayProgress();
			super.onStart();
		}

	};

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent intent = new Intent(this, CorrespondenceDetailsFragment.class);
		intent.putExtra("corres_detail", (CorrespondenceSnapshotBean)correspondanceList.get(position));
		startActivity(intent);
	}
}
