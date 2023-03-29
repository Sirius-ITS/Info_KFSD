package com.kuwaitKFF.more;

import com.kuwaitKFF.common.MyCommon;
import com.kuwaitKFF.more.bean.ContactMsgType;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ContactSpinnerAdapter extends ArrayAdapter<ContactMsgType> {

	public ContactSpinnerAdapter(Context context, int resource,
			ContactMsgType[] objects, String lang) {
		super(context, resource, objects);
		this.context = context;
		this.myObjs = objects;
		this.lang = lang;
	}

	private Context context;
	private ContactMsgType[] myObjs;
	private String lang;

	public ContactMsgType getItem(int position) {
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
	         label.setText(myObjs[position].getContactUsTypeDescEn()); 
		 }else
		 {

	         label.setText(myObjs[position].getContactUsTypeDescAr());
		 }
		 label.setTextColor(Color.BLACK);
         return label;
	}
	
	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		 TextView label = new TextView(context);
		 if(lang!= null && lang.equalsIgnoreCase(MyCommon.LANGUAGE_ENG))
		 {
	         label.setText(myObjs[position].getContactUsTypeDescEn()); 
		 }else
		 {
	         label.setText(myObjs[position].getContactUsTypeDescAr());
		 }
		 label.setTextColor(Color.BLACK);
         return label;
	}

}
