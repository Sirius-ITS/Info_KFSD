package com.kuwaitKFF.correspondence;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kuwaitKFF.R;
import com.kuwaitKFF.common.MyCommon;

public class CorrespondenceListAdapter extends ArrayAdapter<CorrespondanceResponseBean>{
	public CorrespondenceListAdapter(Context context, int resource, ArrayList<CorrespondanceResponseBean> corresList, String lang) {
		super(context, resource);
		this.mActivity = context;
		this.mList = corresList;
		this.lang = lang;
		this.array = new ArrayList<CorrespondanceResponseBean>(mList);
	}

	ArrayList<CorrespondanceResponseBean> mList, array;
	Context mActivity;
	String lang;
	ViewHolder holder;

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		if (convertView == null) 
		{
			convertView = LayoutInflater.from(mActivity).inflate(
					R.layout.rows_corres_item, null);
			holder = new ViewHolder();
			holder.title = (TextView) convertView.findViewById(R.id.titleText);
			holder.desc = (TextView) convertView.findViewById(R.id.descText);
			holder.img = (ImageView) convertView.findViewById(R.id.imageNews);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		if(!getItem(position).getEmpEntityNewCmsCount().equalsIgnoreCase("null") && !getItem(position).getEmpEntityNewCmsCount().equalsIgnoreCase("0"))
		{
			holder.title.setText(getItem(position).getEmpEntityNewCmsCount());
		}
		else
		{
			holder.title.setVisibility(View.GONE);
		}
		if(lang.equalsIgnoreCase(MyCommon.LANGUAGE_ENG))
		{
			if(!getItem(position).getEmpEntityNameEn().equalsIgnoreCase("null"))
				holder.desc.setText(getItem(position).getEmpEntityNameEn());
		}
		else
		{
			if(!getItem(position).getEmpEntityNameAr().equalsIgnoreCase("null"))
				holder.desc.setText(getItem(position).getEmpEntityNameAr());
		}
		
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
	public CorrespondanceResponseBean getItem(int position) {

		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {

		return position;
	}
	
//	@Override
//	public Filter getFilter() {
//		return new Filter() {
//
//			@Override
//			protected FilterResults performFiltering(CharSequence constraint) {
//				constraint = constraint.toString().toLowerCase();
//				FilterResults result = new FilterResults();
//
//				if (constraint != null && constraint.toString().length() > 0) {
//					List<CorrespondanceResponseBean> founded = new ArrayList<CorrespondanceResponseBean>();
//					for (CorrespondanceResponseBean item : array) {
//						if (lang.equalsIgnoreCase(MyCommon.LANGUAGE_ENG) && item.getEmpEntityNameEn().toLowerCase().contains(constraint)) {
//							founded.add(item);
//						}
//						else if (lang.equalsIgnoreCase(MyCommon.LANGUAGE_AR) && item.getEmpEntityNameEn().toLowerCase().contains(constraint)) {
//							founded.add(item);
//						}
//					}
//					result.values = founded;
//					result.count = founded.size();
//				} else {
//					result.values = array;
//					result.count = array.size();
//				}
//				return result;
//
//			}
//
//			@SuppressWarnings("unchecked")
//			@Override
//			protected void publishResults(CharSequence constraint,
//					FilterResults results) {
//				mList.clear();
//				for (CorrespondanceResponseBean item : (List<CorrespondanceResponseBean>) results.values) {
//					mList.add(item);
//				}
//				notifyDataSetChanged();
//			}
//		};
//	}
}
