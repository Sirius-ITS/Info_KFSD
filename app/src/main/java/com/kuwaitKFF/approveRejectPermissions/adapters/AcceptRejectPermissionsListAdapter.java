package com.kuwaitKFF.approveRejectPermissions.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.kuwaitKFF.R;
import com.kuwaitKFF.approveRejectPermissions.interfaces.AcceptRejectPermissionItemInterface;
import com.kuwaitKFF.approveRejectPermissions.models.permissionsDataModels.EmpPermissionRequestViewItem;
import com.kuwaitKFF.approveRejectVacations.interfaces.AcceptRejectItemInterface;
import com.kuwaitKFF.approveRejectVacations.models.vacationsDataModels.EmpLeaveRequestViewItem;

import java.util.List;

public class AcceptRejectPermissionsListAdapter extends ArrayAdapter<EmpPermissionRequestViewItem> {
	List<EmpPermissionRequestViewItem> mList;
	Context mActivity;
	private AcceptRejectPermissionItemInterface itemInterface;

	public AcceptRejectPermissionsListAdapter(@NonNull Context context, AcceptRejectPermissionItemInterface itemInterface
			, int resource, @NonNull List<EmpPermissionRequestViewItem> objects) {
		super(context, resource, objects);
		this.mActivity = context;
		this.mList =  objects;
		this.itemInterface = itemInterface;
	}


	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewHolder holder1;

		convertView = LayoutInflater.from(mActivity).inflate(
				R.layout.list_item_approve_decline_permission, null);
		holder1 = new ViewHolder();
		holder1.empNameText = convertView.findViewById(R.id.empNameText);
		holder1.permissionTypeText = convertView.findViewById(R.id.permissionTypeText);
		holder1.permissionDateText = convertView.findViewById(R.id.startDateText);
		holder1.durationText = convertView.findViewById(R.id.durationText);
		holder1.accept = convertView.findViewById(R.id.approveButton);
		holder1.reject = convertView.findViewById(R.id.declineButton);
		holder1.permissionDuration = convertView.findViewById(R.id.permissionDurationView);
		
		
		convertView.setTag(holder1);

		holder1.empNameText.setText(getItem(position).getPERMEMPNAME());
		holder1.permissionTypeText.setText(getItem(position).getPERMVACATIONDESC());
		holder1.permissionDateText.setText(getItem(position).getPERMDATE().split(" ")[0]);
		holder1.durationText.setText(getItem(position).getPERMDURATION());

		holder1.accept.setOnClickListener(view -> {
			if (itemInterface != null) {
				itemInterface.onItemClick(getItem(position), position, 1);
			}
		});

		holder1.reject.setOnClickListener(view -> {
			if (itemInterface != null) {
				itemInterface.onItemClick(getItem(position), position, 2);
			}
		});
		
		return convertView;

	}

	static class ViewHolder {
		TextView empNameText;
		TextView permissionTypeText;
		TextView permissionDateText;
		TextView durationText;
		Button accept, reject;
		RelativeLayout permissionDuration;

		

	}

	@Override
	public int getCount() {

		return mList.size();
	}

	@Override
	public EmpPermissionRequestViewItem getItem(int position) {

		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {

		return position;
	}
}
