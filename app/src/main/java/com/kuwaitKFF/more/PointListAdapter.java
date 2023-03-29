package com.kuwaitKFF.more;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.kuwaitKFF.common.MyCommon;
import com.kuwaitKFF.more.bean.LocationPointResponse;

public class PointListAdapter extends ArrayAdapter<LocationPointResponse> {

	public PointListAdapter(Context context, int resource, LocationPointResponse[] objects, String lang) {
		super(context, resource);
		this.context = context;
		this.myObjs = objects;
		this.lang = lang;
	}

	private Context context;
	private LocationPointResponse[] myObjs;
	private String lang;

	public LocationPointResponse getItem(int position) {
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
	         label.setText(myObjs[position].getLocationNameEn()); 
		 }else
		 {

	         label.setText(myObjs[position].getLocationNameAr());
		 }
		 label.setTextColor(Color.BLACK);
         return label;
	}
	
	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		 TextView label = new TextView(context);
		 if(lang!= null && lang.equalsIgnoreCase(MyCommon.LANGUAGE_ENG))
		 {
	         label.setText(myObjs[position].getLocationNameEn()); 
		 }else
		 {

	         label.setText(myObjs[position].getLocationNameAr());
		 }
		 label.setTextColor(Color.BLACK);
         return label;
	}



}
