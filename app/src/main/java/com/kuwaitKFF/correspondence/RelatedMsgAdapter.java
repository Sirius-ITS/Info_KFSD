package com.kuwaitKFF.correspondence;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.kuwaitKFF.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class RelatedMsgAdapter extends ArrayAdapter<RelatedMsgBean> {
	public RelatedMsgAdapter(Context context, int resource,
			ArrayList<RelatedMsgBean> list) {
		super(context, resource);
		this.mActivity = context;
		this.mList = list;
		System.out.println("Rupa mList "+mList.size());
	}

	ArrayList<RelatedMsgBean> mList;
	Context mActivity;
	ViewHolder holder;

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(mActivity).inflate(
					R.layout.row_related_item, null);
			holder = new ViewHolder();
			holder.title = (TextView) convertView.findViewById(R.id.titleTextRel);
			holder.desc = (TextView) convertView.findViewById(R.id.descTextRel);
			holder.corresNo = (TextView) convertView.findViewById(R.id.corresNoRel);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		if(getItem(position)!=null)
		{
			if(!getItem(position).getCorrespondNo().equals("null"))
			{
				holder.corresNo.setText(getItem(position).getCorrespondNo());
			}
			holder.title.setText(getItem(position).getCorrespondSubject());
			if(!getItem(position).getCorrespondDate().equals("null"))
			{

				SimpleDateFormat formatterForDisplay = new SimpleDateFormat("yyyy-MM-dd");
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				try {
					holder.desc.setText(formatterForDisplay.format(formatter.parse(getItem(position).getCorrespondDate())));
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}										
		return convertView;

	}

	static class ViewHolder {
		TextView title;
		TextView desc;
		TextView corresNo;
	}

	@Override
	public int getCount() {

		return mList.size();
	}

	@Override
	public RelatedMsgBean getItem(int position) {

		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {

		return position;
	}

}
