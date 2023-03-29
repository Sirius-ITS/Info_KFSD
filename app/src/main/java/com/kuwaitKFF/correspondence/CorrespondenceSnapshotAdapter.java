package com.kuwaitKFF.correspondence;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.kuwaitKFF.R;

public class CorrespondenceSnapshotAdapter extends ArrayAdapter<CorrespondenceSnapshotBean>{

	public CorrespondenceSnapshotAdapter(Context context, int resource, ArrayList<CorrespondenceSnapshotBean> corresList, String lang) {
		super(context, resource);
		this.mActivity = context;
		this.mList = corresList;
		this.lang = lang;
}

ArrayList<CorrespondenceSnapshotBean> mList, array;
Context mActivity;
String lang;
ViewHolder holder;

@Override
public View getView(final int position, View convertView, ViewGroup parent) {
	if (convertView == null) 
	{
		convertView = LayoutInflater.from(mActivity).inflate(
				R.layout.row_snapshot_item, null);
		holder = new ViewHolder();
		holder.title = (TextView) convertView.findViewById(R.id.titleText);
		holder.desc = (TextView) convertView.findViewById(R.id.date_Text);
		holder.corresNo = (TextView) convertView.findViewById(R.id.corresNo);
		convertView.setTag(holder);
	} else {
		holder = (ViewHolder) convertView.getTag();
	}
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

		;
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
public CorrespondenceSnapshotBean getItem(int position) {

	return mList.get(position);
}

@Override
public long getItemId(int position) {

	return position;
}

}
