package com.kuwaitKFF.more;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.kuwaitKFF.common.MyCommon;
import com.kuwaitKFF.more.bean.LocationListResponse;

public class LocationListAdapter extends ArrayAdapter<LocationListResponse> {

	public LocationListAdapter(Context context, int resource, LocationListResponse[] objects, String lang) {
		super(context, resource);
		this.context = context;
		this.myObjs = objects;
		this.lang = lang;
	}

	private Context context;
	private LocationListResponse[] myObjs;
	private String lang;

	public LocationListResponse getItem(int position) {
		return myObjs[position];
	}

	public long getItemId(int position) {
		return position;
	}
	@Override
	public int getCount() {
		return myObjs.length;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		 TextView label = new TextView(context);
		 if(lang!= null && lang.equalsIgnoreCase(MyCommon.LANGUAGE_ENG))
		 {
	         label.setText(myObjs[position].getGovNameEn()); 
		 }else
		 {
	         label.setText(myObjs[position].getGovNameAr());
		 }
		 label.setTextColor(Color.BLACK);
		 
         return label;
	}
	
	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		 TextView label = new TextView(context);
		 if(lang!= null && lang.equalsIgnoreCase(MyCommon.LANGUAGE_ENG))
		 {
	         label.setText(myObjs[position].getGovNameEn()); 
		 }else
		 {

	         label.setText(myObjs[position].getGovNameAr());
		 }
		 label.setTextColor(Color.BLACK);
         return label;
	}


}
