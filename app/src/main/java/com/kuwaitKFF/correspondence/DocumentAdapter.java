package com.kuwaitKFF.correspondence;

import java.util.ArrayList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kuwaitKFF.R;
import com.kuwaitKFF.common.MyCommon;
import com.kuwaitKFF.common.MySharedPref;
import com.kuwaitKFF.main.Kfsd;

public class DocumentAdapter extends ArrayAdapter<DocumentBean>{
	public DocumentAdapter(Kfsd context, int resource, ArrayList<DocumentBean> list) {
		super(context, resource);
		this.mActivity = context;
		this.mList = list;
	}

	ArrayList<DocumentBean> mList;
	Kfsd mActivity;
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
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		if (MySharedPref.getLanguage(mActivity).equals(MyCommon.LANGUAGE_ENG)) {
			holder.desc.setText(getItem(position).getCorrespondDescEn());
		}
		else
			holder.desc.setText(getItem(position).getCorrespondDescAr());

		holder.title.setVisibility(View.GONE);
				
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
	public DocumentBean getItem(int position) {

		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {

		return position;
	}

}
