package com.kuwaitKFF.base;



import com.kuwaitKFF.R;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class BaseFragmentForSelection extends Fragment {

	ProgressDialog progDailog;
	Dialog dialog;
	
	Context mContext;
	private boolean instanceStateSaved;
	protected View header;
	
	
	
	
	public BaseFragmentForSelection() {
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
		
		mContext = getActivity().getApplicationContext();
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		instanceStateSaved = true;
	}
	
	
	

	public void Show_No_Internet() {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		// Dialog can not cancel by back button
		builder.setMessage(getResources().getString(R.string.M_No_Internet))
				.setCancelable(false)
				.setPositiveButton(getResources().getString(R.string.Cancel),
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();
								// BaseActivity.this.finish();
							}
						})
				.setNegativeButton(getResources().getString(R.string.action_settings),
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();
								startActivityForResult(
										new Intent(
												android.provider.Settings.ACTION_WIRELESS_SETTINGS),
										0);
								// BaseActivity.this.finish();
							}
						});

		AlertDialog alert = builder.create();
		alert.show();
	}

	// Dismiss the progress dialog
	public void DismissProgress() {

		if (progDailog != null) {
			if (progDailog.isShowing())
				progDailog.dismiss();
		}
	}

	private void initDisplayProgress() {
		if (progDailog == null)
			progDailog = new ProgressDialog(getActivity());
		if (progDailog.isShowing())
			progDailog.dismiss();
	}

	private void DisplayProgressCommon() {
		// progDailog.setTitle(getString(R.string.T_D_Title));
		progDailog.setCancelable(false);
		progDailog.show();
	}

	// Initiate and display progress dialog
	public void DisplayProgress() {
		initDisplayProgress();
		progDailog.setMessage(getString(R.string.M_Loading));
		DisplayProgressCommon();
	}

	public void Show_Alert_Dialog(int msg) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		// Dialog can not cancel by back button
		builder.setMessage(msg);
		commonShowAlertDialog(builder, null);
	}

	private void commonShowAlertDialog(AlertDialog.Builder builder,
			DialogInterface.OnClickListener clickListner) {
		// Dialog can not cancel by back button
		builder.setCancelable(false).setPositiveButton(
				getResources().getString(R.string.Ok),
				(clickListner != null) ? clickListner
						: new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								dialog.dismiss();
							}
						});
		AlertDialog alert = builder.create();
		alert.show();
	}

	

}
