package com.kuwaitKFF.decisions;

import java.util.ArrayList;

import com.kuwaitKFF.R;


import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

public class DecisionsListAdapter extends BaseAdapter {
	ArrayList<DecisionResponseBean> mList;
	Context mActivity;

	public DecisionsListAdapter(FragmentActivity mActivity,
								ArrayList<DecisionResponseBean> myResMutableList) {
		// TODO Auto-generated constructor stub
		super();
		this.mActivity = mActivity;
		this.mList =  myResMutableList;

	}

	

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewHolder holder1;

		convertView = LayoutInflater.from(mActivity).inflate(
				R.layout.row_item_list, null);
		holder1 = new ViewHolder();
		holder1.title = (TextView) convertView
				.findViewById(R.id.titleText);
		
		
		convertView.setTag(holder1);

		holder1.title.setText(getItem(position).getDecisionSubject());
		
		return convertView;

	}

	static class ViewHolder {
		TextView title;
		

	}

	@Override
	public int getCount() {

		return mList.size();
	}

	@Override
	public DecisionResponseBean getItem(int position) {

		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {

		return position;
	}
}
