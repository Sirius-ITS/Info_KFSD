package com.kuwaitKFF.correspondence;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.kuwaitKFF.R;
import com.kuwaitKFF.common.MyCommon;

public class ProcedureAdapter extends ArrayAdapter<CorrespondenceSnapshotBean>{
	
	public ProcedureAdapter(Context context, int resource, ArrayList<CorrespondenceSnapshotBean> corresList, String lang) {
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
				R.layout.row_procedure_item, null);
		holder = new ViewHolder();
		holder.procTo = (TextView) convertView.findViewById(R.id.ET_proc_to);
		holder.type = (TextView) convertView.findViewById(R.id.ET_type);
		holder.date = (TextView) convertView.findViewById(R.id.ET_date);
		holder.status = (TextView) convertView.findViewById(R.id.ET_status);
		convertView.setTag(holder);
	} else {
		holder = (ViewHolder) convertView.getTag();
	}
	CorrespondenceSnapshotBean bean = getItem(position);

	if(lang.equalsIgnoreCase(MyCommon.LANGUAGE_ENG)) {
		if (!bean.getSnapshotToNameEn().equalsIgnoreCase("null"))
			holder.procTo.setText(getItem(position).getSnapshotToNameEn());
	}
	else {
		if (!bean.getSnapshotToNameAr().equalsIgnoreCase("null"))
			holder.procTo.setText(getItem(position).getSnapshotToNameAr());
	}
	if(lang.equalsIgnoreCase(MyCommon.LANGUAGE_ENG)) {
		if (!bean.getSnapshotActionDescEn().equalsIgnoreCase("null"))
			holder.type.setText(getItem(position).getSnapshotActionDescEn());

		if(!bean.getSnapshotStatusDescEn().equalsIgnoreCase("null"))
			holder.status.setText(bean.getSnapshotStatusDescEn());
	}
	else {
		if (!bean.getSnapshotActionDescAr().equalsIgnoreCase("null"))
			holder.type.setText(getItem(position).getSnapshotActionDescAr());

		if(!bean.getSnapshotStatusDescAr().equalsIgnoreCase("null"))
			holder.status.setText(bean.getSnapshotStatusDescAr());
	}

	Log.d("MzTagPROC", bean.getSnapshotStatusDescAr());
	Log.d("MzTagPROC", bean.getSnapshotStatusDescEn());

//	if(!bean.getSnapshotActionNote().equalsIgnoreCase("null"))
//	holder.status.setText(bean.getsnapshotst());
	
	if(!bean.getSnapshotDate().equalsIgnoreCase("null"))
	{
		SimpleDateFormat formatterForDisplay = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		try {
			holder.date.setText(formatterForDisplay.format(formatter.parse(bean.getSnapshotDate())));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}


	return convertView;

}

static class ViewHolder {
	TextView procTo;
	TextView type;		
	TextView date;
	TextView status;
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
