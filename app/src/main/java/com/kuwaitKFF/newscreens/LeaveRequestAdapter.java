package com.kuwaitKFF.newscreens;

import com.kuwaitKFF.R;
import com.kuwaitKFF.common.MyCommon;
import com.kuwaitKFF.more.bean.LeaveReqType;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class LeaveRequestAdapter extends ArrayAdapter<LeaveReqType> {

	public LeaveRequestAdapter(Context context, int resource,
							  LeaveReqType[] objects, String lang) {
		super(context, resource, objects);
		this.context = context;
		this.myObjs = objects;
		this.lang = lang;
	}

	private Context context;
	private LeaveReqType[] myObjs;
	private String lang;

	public LeaveReqType getItem(int position) {
		return myObjs[position];
	}

	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		 TextView label = new TextView(context);
		 if(lang!= null && lang.equalsIgnoreCase(MyCommon.LANGUAGE_ENG))
		 {
	         label.setText(myObjs[position].getLeaveTypeDescEn());
		 }else
		 {

	         label.setText(myObjs[position].getLeaveTypeDescAr());
		 }
		 label.setTextColor(context.getResources().getColor(R.color.txt_clr));
			label.setGravity(Gravity.CENTER);
		label.setTextSize(17f);
         return label;
	}
	
	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		 TextView label = new TextView(context);
		 if(lang!= null && lang.equalsIgnoreCase(MyCommon.LANGUAGE_ENG))
		 {
	         label.setText(myObjs[position].getLeaveTypeDescEn());
		 }else
		 {
	         label.setText(myObjs[position].getLeaveTypeDescAr());
		 }
		 label.setTextColor(Color.BLACK);
         return label;
	}

}
