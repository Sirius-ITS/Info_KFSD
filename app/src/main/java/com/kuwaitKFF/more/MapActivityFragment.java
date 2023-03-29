package com.kuwaitKFF.more;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.Spinner;
import android.widget.TextView;


import androidx.core.app.ActivityCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.kuwaitKFF.R;
import com.kuwaitKFF.base.BaseActivity;
import com.kuwaitKFF.common.MyCommon;
import com.kuwaitKFF.common.MyHttpConnection;
import com.kuwaitKFF.common.MySharedPref;
import com.kuwaitKFF.main.Kfsd;
import com.kuwaitKFF.more.bean.LocationListResponse;
import com.kuwaitKFF.more.bean.LocationPointResponse;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class MapActivityFragment extends BaseActivity implements OnMapReadyCallback {

	Kfsd kfsd;
	View view;
	ArrayList<LocationListResponse> list;
	ExpandableListView expListView;
	MapFragment mapFragment;
	Spinner centreText, districtText;
	HashMap<String, ArrayList<String>> listDataChild;
	ArrayList<LocationPointResponse> pointList;
	Button submitButton;
	int centrePosition = -1;

	public MapActivityFragment() {
		super(R.layout.fragment_map);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initialization() {
		ButterKnife.bind(this);

	}

	@Override
	public void implementation() {
		kfsd = (Kfsd) getApplication();

		centreText = (Spinner) findViewById(R.id.centreText);
		submitButton = (Button) findViewById(R.id.mapSubmitButton);

		MyHttpConnection.get(MyCommon.WS_METHOD_LOCATION_LIST,
				asyncMapResponseHandler);
		centreText.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
									   int position, long id) {

				((TextView) parent.getChildAt(0)).setTextColor(Color.rgb(52, 90, 152));
				((TextView) parent.getChildAt(0)).setTextSize(17f);
				((TextView) parent.getChildAt(0)).setGravity(Gravity.CENTER_HORIZONTAL);

				centrePosition = -1;

				if (list != null && list.get(position) != null) {
					RequestParams params = new RequestParams();
					System.out.println("Item list.get(position).getGovCode() " + list.get(position).getGovCode());
					params.put("GovCd", list.get(position).getGovCode());
					MyHttpConnection.get(MyCommon.WS_METHOD_LOCATION_POINT, params,
							asyncGovResponseHandler);
				}

			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});
		submitButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				if (centrePosition == -1)
					return;

				mapFragment.getMapAsync(MapActivityFragment.this);

//				GoogleMap map = mapFragment.getMap();
//				map.clear();
//				int i = 0;
//				for (LocationPointResponse resp : pointList)
//				{
//					if(resp.getLocationLatitudes() != null && resp.getLocationLongitude() != null)
//					{
//						Double lat =Double.parseDouble(resp.getLocationLatitudes());
//						Double longi =Double.parseDouble(resp.getLocationLongitude());
//						LatLng ll = new LatLng(lat, longi);
//						if(i==0)
//						{
//							map.moveCamera(CameraUpdateFactory.newLatLngZoom(ll, 7));
//						}
//						MarkerOptions options = new MarkerOptions();
//						options.position(ll);
//
//						if (MySharedPref.getLanguage(kfsd).equals(MyCommon.LANGUAGE_ENG)) {
//							options.title(resp.getLocationNameEn());
//							options.snippet(resp.getLocationContactEn());
//						} else {
//							options.title(resp.getLocationNameAr());
//							options.snippet(resp.getLocationContactAr());
//						}
//
//						map.addMarker(options);
//
//						i++;
//					}
//				}


			}
		});
		districtText = (Spinner) findViewById(R.id.districtText);
		districtText.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
									   int position, long id) {


				((TextView) parent.getChildAt(0)).setTextColor(Color.rgb(52, 90, 152));
				((TextView) parent.getChildAt(0)).setTextSize(17f);
				((TextView) parent.getChildAt(0)).setGravity(Gravity.CENTER_HORIZONTAL);

				centrePosition = position;
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});

		setHeader(getResources().getString(R.string.map), true, true);
		mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
		mapFragment.getMapAsync(this);

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

				finish();

			} catch (Exception e) {
				e.printStackTrace();
			}

			return true;

		}

		return false;
	}

	@Override
	public void onMapReady(GoogleMap map) {

		if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
			// TODO: Consider calling
			//    ActivityCompat#requestPermissions
			// here to request the missing permissions, and then overriding
			//   public void onRequestPermissionsResult(int requestCode, String[] permissions,
			//                                          int[] grantResults)
			// to handle the case where the user grants the permission. See the documentation
			// for ActivityCompat#requestPermissions for more details.
			return;
		}
		map.setMyLocationEnabled(true);
		if (pointList.get(centrePosition).getLocationLatitudes() != null && pointList.get(centrePosition).getLocationLongitude() != null) {
			Double lat = Double.parseDouble(pointList.get(centrePosition).getLocationLatitudes());
			Double longi = Double.parseDouble(pointList.get(centrePosition).getLocationLongitude());
			MarkerOptions options = new MarkerOptions();
			options.position(new LatLng(lat, longi));
//					options.title(pointList.get(position).getLocationNameEn());

			if (MySharedPref.getLanguage(kfsd).equals(MyCommon.LANGUAGE_ENG)) {
				options.title(pointList.get(centrePosition).getLocationNameEn());
				options.snippet(pointList.get(centrePosition).getLocationContactEn());
			} else {
				options.title(pointList.get(centrePosition).getLocationNameAr());
				options.snippet(pointList.get(centrePosition).getLocationContactAr());
			}

			map.addMarker(options).showInfoWindow();
			map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, longi), 16));
		}else{
			map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(29.3667, 47.9667), 9)); //kuwait
		}
       
	}

	AsyncHttpResponseHandler asyncMapResponseHandler = new AsyncHttpResponseHandler() {

		@Override
		public void onFinish() {
			DismissProgress();
			super.onFinish();
		}

		@Override
		public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
			Log.d("MzTags", String.valueOf(responseBody));
			// response = MyCommon.getJsonFromWebServiceResponse(response);
			try {
				JSONObject json = null;
				try {
					json = new JSONObject(new String(responseBody, "UTF-8"));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				JSONArray jsonArray = json.getJSONArray("KfsdMobileGovernment");
				list = new ArrayList<LocationListResponse>();

				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonObject = jsonArray.getJSONObject(i);
					LocationListResponse locObj = new LocationListResponse();
					locObj.setGovCode(jsonObject
							.getString("GovCode"));
					locObj.setGovNameAr(jsonObject
							.getString("GovNameAr"));
					locObj.setGovNameEn(jsonObject
							.getString("GovNameEn"));

					list.add(locObj);
				}
				String lang = MySharedPref.getLanguage(MapActivityFragment.this);

				LocationListResponse[] array = new LocationListResponse[list.size()];
				LocationListAdapter adapter = new LocationListAdapter(getApplicationContext(), android.R.layout.simple_spinner_item,
						list.toArray(array), lang);

				centreText.setAdapter(adapter);


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
	
	AsyncHttpResponseHandler asyncGovResponseHandler = new AsyncHttpResponseHandler() {

		@Override
		public void onFinish() {
			DismissProgress();
			super.onFinish();
		}

		@Override
		public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
			Log.d("MzTag", String.valueOf(responseBody));
			// response = MyCommon.getJsonFromWebServiceResponse(response);
			try {
				JSONObject json = null;
				try {
					json = new JSONObject(new String(responseBody, "UTF-8"));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				pointList = new ArrayList<LocationPointResponse>();
				String success = json.getString("success");

				if(success.equals("1"))
				{
					JSONArray jsonArray = json.getJSONArray("KfsdMobileLoc");
					pointList.add(new LocationPointResponse());
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject jsonObject = jsonArray.getJSONObject(i);
						LocationPointResponse point = new LocationPointResponse();
						point.setLocationCode(jsonObject
								.getString("LocationCode"));
						point.setLocationNameAr(jsonObject
								.getString("LocationNameAr"));
						point.setLocationNameEn(jsonObject
								.getString("LocationNameEn"));
						point.setLocationGovCode(jsonObject
								.getString("LocationGovCode"));
						point.setLocationLongitude(jsonObject
								.getString("LocationLongitude"));
						point.setLocationLatitudes(jsonObject
								.getString("LocationLatitudes"));

						String temp = jsonObject.getString("LocationContactAr");

						if(!temp.equals("null"))
							point.setLocationContactAr(temp);

						temp = jsonObject.getString("LocationContactEn");

						if(!temp.equals("null"))
							point.setLocationContactEn(temp);


						point.setLatLong(jsonObject
								.getString("latLong"));

						pointList.add(point);
					}
				}
				String lang = MySharedPref.getLanguage(MapActivityFragment.this);
				LocationPointResponse[] array = new LocationPointResponse[pointList.size()];
				PointListAdapter adapter = new PointListAdapter(getApplicationContext(), android.R.layout.simple_spinner_item,
						pointList.toArray(array), lang);
				districtText.setAdapter(adapter);

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
