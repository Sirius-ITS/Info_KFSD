package com.kuwaitKFF.advertisements;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import com.kuwaitKFF.R;
import com.kuwaitKFF.common.MyCommon;
import com.kuwaitKFF.common.MySharedPref;

public class AdvertisementListAdapter extends BaseExpandableListAdapter {

	ArrayList<AdvertisementListBean> mList;
	Context mActivity;

	public AdvertisementListAdapter(FragmentActivity mActivity,
									ArrayList<AdvertisementListBean> myResMutableList) {
		// TODO Auto-generated constructor stub
		super();
		this.mActivity = mActivity;
		this.mList = myResMutableList;
	}

	static class ViewHolder {
		TextView title;
	}

	class ChildViewHolder {
		TextView subject;
		TextView desc;
	}


	@Override
	public int getGroupCount() {
		return mList.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return mList.get(groupPosition).getAdvrtList().size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return mList.get(groupPosition);
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return (mList.get(groupPosition).getAdvrtList().get(childPosition));
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		final ViewHolder holder1;

		convertView = LayoutInflater.from(mActivity).inflate(
				R.layout.row_item_list, null);
		holder1 = new ViewHolder();
		holder1.title = (TextView) convertView.findViewById(R.id.titleText);

		convertView.setTag(holder1);

		if (MySharedPref.getLanguage((Activity)mActivity).equals(
				MyCommon.LANGUAGE_ENG))
			holder1.title.setText(((AdvertisementListBean) getGroup(groupPosition)).getAdvertiseTypeDescEn());
		else
			holder1.title.setText(((AdvertisementListBean) getGroup(groupPosition)).getAdvertiseTypeDescAr());

		holder1.title.setTypeface(null, Typeface.BOLD);
		holder1.title.setTextSize(17f);


		return convertView;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		final ChildViewHolder holder1;

		convertView = LayoutInflater.from(mActivity).inflate(
				R.layout.row_advrt_expandable_item, null);
		holder1 = new ChildViewHolder();
		holder1.subject = (TextView) convertView.findViewById(R.id.TV_1);
		holder1.desc = (TextView) convertView.findViewById(R.id.TV_2);
		convertView.setTag(holder1);

		holder1.subject.setText(((AdvertisementResponseBean)getChild(groupPosition, childPosition)).getAdvertisementSubject());
		//holder1.desc.setText(((AdvertisementResponseBean)getChild(groupPosition, childPosition)).getAdvertisementSerial());
		holder1.desc.setVisibility(View.GONE);
		return convertView;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

}
