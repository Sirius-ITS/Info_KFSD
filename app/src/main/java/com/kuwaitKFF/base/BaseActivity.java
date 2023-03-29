package com.kuwaitKFF.base;

import com.kuwaitKFF.R;
import com.kuwaitKFF.common.MyCommon;
import com.kuwaitKFF.common.MySharedPref;
import com.kuwaitKFF.login.ProfileFragment;
import com.kuwaitKFF.main.Kfsd;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;


import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.ListFragment;


public abstract class BaseActivity extends FragmentActivity implements BaseInterface {
	
	

	/**
	 * Holding Title string as a String Resource Id
	 */
	private final int mLayoutResId;
	private int mtitleResId = 0;
	private final String mTitle = "";
	boolean isSlidingMenuAvailable = true;
	public Kfsd kfsd;
	TextView txtDays, txtHours, txtMin, txtSec;
	protected ListFragment mFrag;
	
	ProgressDialog progDailog;
	Dialog dialog;
	
	

	


	public BaseActivity(int layoutResId, int titleResId) {
		mLayoutResId = layoutResId;
		mtitleResId = titleResId;
	}


	public BaseActivity(int layoutResId) {
		mLayoutResId = layoutResId;
	}

	/**
	 * This method set Orientation to Portrait through code so we can not need
	 * to write in AndroidManifest.xml
	 */
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// set activity to portrait only mode
		MyCommon.setPortraitOrientation(this);
		kfsd = (Kfsd) getApplication();
		// Set content view to provided layout id
		setContentView(mLayoutResId);
    	if (mtitleResId == 0) {
			setTitle(mTitle);
		} else {
			setTitle(getString(mtitleResId));
		}


		

		initialization();
		implementation();
		
		

	}
	
	public void setHeader( String heading, boolean backVisible,
			boolean headerLogoVisible) {
		ViewStub stub = (ViewStub) findViewById(R.id.vsHeader);

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
						
						finish();
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

	public void setHeader( String heading, boolean backVisible,
			boolean headerLogoVisible,boolean isHeaderClickable) {
		ViewStub stub = (ViewStub) findViewById(R.id.vsHeader);

		try {
			View inflated = stub.inflate();

			TextView txtTitle = (TextView) inflated
					.findViewById(R.id.txtVenueHeading);
		
			txtTitle.setText(heading);
			
			
			if (isHeaderClickable) {

				if (MySharedPref.getLanguage(this).equals(
						MyCommon.LANGUAGE_ENG))
					txtTitle.setCompoundDrawablesWithIntrinsicBounds( 0, 0, R.drawable.ic_perm_identity_white_18dp, 0);
				else
					txtTitle.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_perm_identity_white_18dp, 0, 0, 0);

				txtTitle.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						
						Intent i = new Intent();
						i.setClass(BaseActivity.this, ProfileFragment.class);
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
						
						finish();
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

	
	@Override
	public void onResume() {
		super.onResume();
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
				progDailog = new ProgressDialog(this);
			if (progDailog.isShowing())
				progDailog.dismiss();
		}

		private void DisplayProgressCommon() {
			//progDailog.setTitle(getString(R.string.T_D_Title));
			progDailog.setCancelable(false);
			progDailog.show();
		}

		// Initiate and display progress dialog
		public void DisplayProgress() {
			initDisplayProgress();
			progDailog.setMessage(getString(R.string.M_Loading));
			DisplayProgressCommon();
		}

		public void Show_Alert_Dialog(String msg) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			// Dialog can not cancel by back button
			builder.setMessage(msg);
			commonShowAlertDialog(builder, null);
		}


		public void Show_Alert_Dialog(int msg) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
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
		
		
		public void Show_No_Internet() {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			// Dialog can not cancel by back button
			builder.setMessage(getResources().getString(R.string.M_No_Internet))
					.setCancelable(false)
					.setPositiveButton(getResources().getString(R.string.Cancel),
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									dialog.dismiss();
									BaseActivity.this.finish();
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
									BaseActivity.this.finish();
								}
							});

			AlertDialog alert = builder.create();
			alert.show();
		}
		
		
		public void Show_Alert_Dialog(int msg,
				DialogInterface.OnClickListener clickListner) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			// Dialog can not cancel by back button
			builder.setMessage(msg);
			commonShowAlertDialog(builder, clickListner);
		}
		
		public void Show_Alert_Dialog(String msg,
				DialogInterface.OnClickListener clickListner) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			// Dialog can not cancel by back button
			builder.setMessage(msg);
			commonShowAlertDialog(builder, clickListner);
		}
		
		public void appNotPresent() {
			
			
			Show_Alert_Dialog(R.string.M_App_Not_Present);
		}
}
