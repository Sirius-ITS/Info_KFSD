package com.kuwaitKFF.more;

import java.util.ArrayList;
import java.util.HashMap;

import com.kuwaitKFF.R;


import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;



public class MoreExpandableListAdapter extends BaseExpandableListAdapter {

	private final Context _context;
	private final ArrayList<String> _listDataHeader; // header titles
	// child data in format of header title, child title
	private final HashMap<String, ArrayList<String>> _listDataChild;
	float productCost;
	String productCostFinalValue;

	public MoreExpandableListAdapter(Context context, ArrayList<String> listDataHeader,
			HashMap<String, ArrayList<String>> listChildData) {
		this._context = context;
		this._listDataHeader = listDataHeader;
		this._listDataChild = listChildData;
	}
	
		@Override
	public String getChild(int groupPosition, int childPosititon) {
		return this._listDataChild.get(this._listDataHeader.get(groupPosition))
				.get(childPosititon);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, final int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {

		// String childText =  getChild(groupPosition, childPosition).deliveryAmt;

		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) this._context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			
			convertView = infalInflater.inflate(R.layout.list_item_1, null);
		}
		
		TextView title = (TextView) convertView.findViewById(R.id.titleText);
		
//		if(!(getChild(groupPosition, childPosition).getProductImage().isEmpty()))
//		{
//		  
//		  String DownloadUrl = getChild(groupPosition, childPosition).getProductImage();
//		  
//		  imgLoader.DisplayImage(DownloadUrl,  imageView1);
//		}
//		 else {
//			 imageView1.setImageResource(R.drawable.pinkberry);
//		}
//		
		
		title.setText(getChild(groupPosition, childPosition));
				return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return this._listDataChild.get(this._listDataHeader.get(groupPosition))
				.size();
	}

	@Override
	public String getGroup(int groupPosition) {
		return this._listDataHeader.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return this._listDataHeader.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		String headerTitle = (String) getGroup(groupPosition);
		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) this._context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(R.layout.list_group, null);
		}

		TextView lblListHeader = (TextView) convertView
				.findViewById(R.id.lblListHeader);
		lblListHeader.setTypeface(null, Typeface.BOLD);
		lblListHeader.setText(headerTitle);
		lblListHeader.setTextSize(17f);

		

		ImageView arrowBtn = (ImageView) convertView
				.findViewById(R.id.arrowBtn1);
		arrowBtn.setImageResource(isExpanded?R.drawable.downarrow:R.drawable.downarrow);
		

		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

}
