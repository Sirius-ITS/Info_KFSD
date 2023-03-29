package com.kuwaitKFF.base;

import com.kuwaitKFF.R;
import com.kuwaitKFF.common.MyCommon;
import com.kuwaitKFF.common.MySharedPref;
import com.kuwaitKFF.login.ProfileFragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class BaseFragment extends Fragment {
	Context mContext;
	private boolean instanceStateSaved;
	protected View header;

	public BaseFragment() {
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

	// @Override
	// public void onSaveInstanceState(Bundle outState) {
	// instanceStateSaved = true;
	// }

	public void setHeader(View view, String heading, boolean backVisible,
			boolean headerLogoVisible) {
		ViewStub stub = (ViewStub) view.findViewById(R.id.vsHeader);

		try {
			View inflated = stub.inflate();

			TextView txtTitle = (TextView) inflated
					.findViewById(R.id.txtVenueHeading);
		
			txtTitle.setText(heading);

			if (backVisible) {

				ImageButton backButton = (ImageButton) inflated
						.findViewById(R.id.Header_Back_Button);
				backButton.setVisibility(View.VISIBLE);
				backButton.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						
						if(getFragmentManager().getBackStackEntryCount()>0)
						{
							getFragmentManager().popBackStack();
						}

					}
				});

			}
			
			
			if (headerLogoVisible) {

				ImageButton logoButton = (ImageButton) inflated
						.findViewById(R.id.Header_Logo_Button);
				logoButton.setVisibility(View.VISIBLE);
				logoButton.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {

					}
				});

			}
		} catch (NullPointerException e) {
		}
	}

	
	public void setHeader(View view, String heading, boolean backVisible,
			boolean headerLogoVisible, boolean isHeaderClickable) {
		ViewStub stub = (ViewStub) view.findViewById(R.id.vsHeader);

		try {
			View inflated = stub.inflate();

			TextView txtTitle = (TextView) inflated
					.findViewById(R.id.txtVenueHeading);
		
			txtTitle.setText(heading);
			
			if (isHeaderClickable) {

				if (MySharedPref.getLanguage(getActivity()).equals(
						MyCommon.LANGUAGE_ENG))
					txtTitle.setCompoundDrawablesWithIntrinsicBounds( 0, 0, R.drawable.ic_perm_identity_white_18dp, 0);
				else
					txtTitle.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_perm_identity_white_18dp, 0, 0, 0);

				txtTitle.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						
						Intent i = new Intent();
						i.setClass(getActivity(), ProfileFragment.class);
						i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
								| Intent.FLAG_ACTIVITY_CLEAR_TOP);
					
						startActivity(i);

					}
				});

			}


			if (backVisible) {

				ImageButton backButton = (ImageButton) inflated
						.findViewById(R.id.Header_Back_Button);
				backButton.setVisibility(View.VISIBLE);
				backButton.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						
						if(getFragmentManager().getBackStackEntryCount()>0)
						{
							getFragmentManager().popBackStack();
						}

					}
				});

			}
			
			
			if (headerLogoVisible) {

				ImageButton logoButton = (ImageButton) inflated
						.findViewById(R.id.Header_Logo_Button);
				logoButton.setVisibility(View.VISIBLE);
				logoButton.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {

					}
				});

			}
		} catch (NullPointerException e) {
		}
	}

	
	ProgressDialog progDailog = null;

	// Initiate and display progress dialog
	public void displayProgress() {
		if (progDailog == null)
			progDailog = new ProgressDialog(getActivity());
		if (progDailog.isShowing())
			progDailog.dismiss();
		progDailog.setCancelable(false);
		progDailog.show();
	}

	// Dismiss the progress dialog
	public void dismissProgress() {
		if (progDailog != null) {
			if (progDailog.isShowing())
				progDailog.dismiss();
		}
	}

	public static String getStringFromView(EditText editText) {
		return editText.getText().toString().trim();
	}

	public void Show_Alert_Dialog(int msg) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		// Dialog can not cancel by back button
		builder.setMessage(msg);
		commonShowAlertDialog(builder, null);
	}
	
	public void Show_Alert_Dialog(String msg,
			DialogInterface.OnClickListener clickListner) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		// Dialog can not cancel by back button
		builder.setMessage(msg);
		commonShowAlertDialog(builder, clickListner);
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

	public void onWindowFocusChanged(boolean hasFocus) {
		// TODO Auto-generated method stub

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
				.setNegativeButton(
						getResources().getString(R.string.action_settings),
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

	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		return false;
	}

}
