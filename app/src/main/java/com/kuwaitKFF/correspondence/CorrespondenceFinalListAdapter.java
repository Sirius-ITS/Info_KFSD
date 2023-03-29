package com.kuwaitKFF.correspondence;

import java.util.ArrayList;

import com.kuwaitKFF.R;


import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

public class CorrespondenceFinalListAdapter extends BaseAdapter {
	ArrayList<String> mList;
	Context mActivity;

	public CorrespondenceFinalListAdapter(FragmentActivity mActivity,
										  ArrayList<String> myResMutableList) {
		// TODO Auto-generated constructor stub
		super();
		this.mActivity = mActivity;
		this.mList =  myResMutableList;

	}

	

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewHolder holder1;

		convertView = LayoutInflater.from(mActivity).inflate(
				R.layout.list_item_correspondence_details, null);
		holder1 = new ViewHolder();
		holder1.title = (TextView) convertView.findViewById(R.id.labelText);
		holder1.desc = (TextView) convertView.findViewById(R.id.valueText);
		
		
		convertView.setTag(holder1);

		holder1.title.setText(getItem(position));
		holder1.desc.setText("desc");
		
		return convertView;

	}

	static class ViewHolder {
		TextView title;
		TextView desc;
		ImageView img;
		

	}

	@Override
	public int getCount() {

		return mList.size();
	}

	@Override
	public String getItem(int position) {

		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {

		return position;
	}
}
