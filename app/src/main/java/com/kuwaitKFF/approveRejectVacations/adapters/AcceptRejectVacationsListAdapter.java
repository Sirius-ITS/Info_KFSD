package com.kuwaitKFF.approveRejectVacations.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.kuwaitKFF.R;
import com.kuwaitKFF.approveRejectVacations.interfaces.AcceptRejectItemInterface;
import com.kuwaitKFF.approveRejectVacations.models.vacationsDataModels.EmpLeaveRequestViewItem;
import java.util.List;

public class AcceptRejectVacationsListAdapter extends ArrayAdapter<EmpLeaveRequestViewItem> {
	List<EmpLeaveRequestViewItem> mList;
	Context mActivity;
	private AcceptRejectItemInterface itemInterface;

	public AcceptRejectVacationsListAdapter(@NonNull Context context, AcceptRejectItemInterface itemInterface
			, int resource, @NonNull List<EmpLeaveRequestViewItem> objects) {
		super(context, resource, objects);
		this.mActivity = context;
		this.mList =  objects;
		this.itemInterface = itemInterface;
	}


	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewHolder holder1;

		convertView = LayoutInflater.from(mActivity).inflate(
				R.layout.list_item_approve_decline, null);
		holder1 = new ViewHolder();
		holder1.empNameText = convertView.findViewById(R.id.empNameText);
		holder1.vacationTypeText = convertView.findViewById(R.id.vacationTypeText);
		holder1.startDateText = convertView.findViewById(R.id.startDateText);
		holder1.toDateText = convertView.findViewById(R.id.toDateText);
		holder1.accept = convertView.findViewById(R.id.approveButton);
		holder1.reject = convertView.findViewById(R.id.declineButton);
		
		
		convertView.setTag(holder1);

		holder1.empNameText.setText(getItem(position).getEMPNAME());
		holder1.vacationTypeText.setText(getItem(position).getVACATIONDESC());
		holder1.startDateText.setText(getItem(position).getVACSTARTDATE().split(" ")[0]);
		holder1.toDateText.setText(getItem(position).getVACENDDATE().split(" ")[0]);

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
		TextView vacationTypeText;
		TextView startDateText;
		TextView toDateText;
		Button accept, reject;
		

	}

	@Override
	public int getCount() {

		return mList.size();
	}

	@Override
	public EmpLeaveRequestViewItem getItem(int position) {

		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {

		return position;
	}
}
