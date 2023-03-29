package com.kuwaitKFF.more;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.kuwaitKFF.R;
import com.kuwaitKFF.more.bean.PhoneEmailBean;

public class PhoneEmailAdapter extends ArrayAdapter<PhoneEmailBean> {
	Context context;
	public PhoneEmailAdapter(Context context, int resource) {
		super(context, resource);
		this.context = context;
	}

	class ViewHolder
	{
		TextView deptName;
		TextView name;
		TextView  webView;
	}
	ViewHolder holder;
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		View view = convertView;
		if (view == null) {
			view = LayoutInflater.from(context).inflate(
					R.layout.row_phone_email, null);
			holder = new ViewHolder();
			holder.name = (TextView) view.findViewById(R.id.TV_leader_name);
			holder.deptName = (TextView) view.findViewById(R.id.TV_leader_dept_name);
			holder.webView = (TextView ) view.findViewById(R.id.WV_leader_contact);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		PhoneEmailBean bean = getItem(position);
		System.out.println("dname"+bean.getLeaderName());
		if(bean.getLeaderName() != null && !"null".equalsIgnoreCase(bean.getLeaderName()))
		{
			holder.name.setText(bean.getLeaderName());
			holder.name.setVisibility(View.VISIBLE);
		}
		else
		{
			holder.name.setVisibility(View.GONE);
		}
		if(bean.getLeaderDeptName() != null && !"null".equalsIgnoreCase(bean.getLeaderDeptName()))
			holder.deptName.setText(bean.getLeaderDeptName());
		else
		{
			holder.deptName.setVisibility(View.GONE);
		}
        String html = bean.getLeaderContact();
        holder.webView.setText(html);
		return view;
	}

}
