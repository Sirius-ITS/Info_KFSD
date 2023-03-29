package com.kuwaitKFF.menu;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentTransaction;

import com.kuwaitKFF.R;
import com.kuwaitKFF.approveRejectPermissions.FragmentAcceptRejectPermissions;
import com.kuwaitKFF.approveRejectVacations.FragmentAcceptRejectVacations;
import com.kuwaitKFF.base.BaseFragment;
import com.kuwaitKFF.cancelOrBreakVacation.FragmentCancelOrBreakVacation;
import com.kuwaitKFF.common.MyCommon;
import com.kuwaitKFF.common.MySharedPref;
import com.kuwaitKFF.databinding.FragmentSubmenuBinding;
import com.kuwaitKFF.home.HomeFragment;
import com.kuwaitKFF.login.FragmentAttendance;
import com.kuwaitKFF.login.FragmentPermission;
import com.kuwaitKFF.login.ProfileFragment;
import com.kuwaitKFF.main.Kfsd;
import com.kuwaitKFF.monthlyReportInquiry.FragmentMonthlyReportInquiry;
import com.kuwaitKFF.newscreens.FragmentLeaveRequest;
import com.kuwaitKFF.newscreens.FragmentPermissionRequest;
import com.kuwaitKFF.sickVacationBalance.FragmentSickVacationBalance;
import com.kuwaitKFF.vacation.FragmentVacation;
import com.kuwaitKFF.vacationsbalance.FragmentVacationBalance;

public class SubMenuFragment extends BaseFragment implements OnClickListener {

	Kfsd kfsd;
	View view;

	Intent i;
	int pageType; // 1 vacation, 2 permission
	FragmentSubmenuBinding b;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		kfsd = (Kfsd) getActivity().getApplication();

		view = LayoutInflater.from(getActivity()).inflate(
				R.layout.fragment_submenu, null);
		b = DataBindingUtil.setContentView(getActivity(),R.layout.fragment_submenu);


        Bundle bundle = this.getArguments();
        if (bundle != null)
            pageType = bundle.getInt("menuType", -1);


		b.textView1sub.setOnClickListener(this);
		b.textView2sub.setOnClickListener(this);
		b.textView3sub.setOnClickListener(this);
		b.textView4sub.setOnClickListener(this);
		b.textView5sub.setOnClickListener(this);
		b.textView6sub.setOnClickListener(this);
		b.textView7sub.setOnClickListener(this);
		b.textView8sub.setOnClickListener(this);
		b.textView9sub.setOnClickListener(this);

		if(pageType == 1) {
			b.textView1sub.setText(R.string.leave_request);
			b.textView2sub.setText(R.string.vacations);
			b.relView3sub.setVisibility(View.VISIBLE);
			b.relView4sub.setVisibility(View.VISIBLE);
			b.relView5sub.setVisibility(View.VISIBLE);
			b.relView6sub.setVisibility(View.VISIBLE);
			b.relView7sub.setVisibility(View.VISIBLE);
			b.relView8sub.setVisibility(View.VISIBLE);
			b.relView9sub.setVisibility(View.VISIBLE);
			b.textView3sub.setText(R.string.vacations_balance);
			b.textView4sub.setText(R.string.sick_leave_balance);
			b.textView5sub.setText(R.string.cancel_vacation_request);
			b.textView6sub.setText(R.string.cut_vacation_request);
			b.textView7sub.setText(R.string.vacations_requests_approval);
			b.textView8sub.setText(R.string.cancel_vacations_requests_approval);
			b.textView9sub.setText(R.string.break_vacations_requests_approval);
		}
		else if(pageType == 2) {
			b.textView1sub.setText(R.string.permission_request);
			b.textView2sub.setText(R.string.permissions);
			b.relView3sub.setVisibility(View.VISIBLE);
			b.textView3sub.setText(R.string.permissions_approval);
		}

		else if(pageType == 3) {
			b.textView1sub.setText(R.string.month_balance_enquiry);
			b.textView2sub.setText(R.string.month_report_enquiry);
		}

//		Log.d("MzTag22", "English Name: " + MyCommon.sLoginResponseBean.getNameEn());
//		Log.d("MzTag22", "Arabic Name: " + MyCommon.sLoginResponseBean.getNameAr());

		if (MyCommon.sLoginResponseBean != null) {
			String name;
			if (MySharedPref.getLanguage(getActivity()).equals(MyCommon.LANGUAGE_ENG)) {
				name = MyCommon.sLoginResponseBean.getNameEn();
				if (name.equals("null")) {
					name = "";
					MyCommon.sLoginResponseBean.setNameEn("");
				}
			} else {
				name = MyCommon.sLoginResponseBean.getNameAr();
				if (name.equals("null")) {
					name = "";
					MyCommon.sLoginResponseBean.setNameAr("");
				}
			}

			setHeader(view, getResources().getString(R.string.welcome) + " " + name, true, true);
		}



		return view;
	}
	
	
	public void setHeader(View view, String heading, boolean backVisible,
			boolean headerLogoVisible) {
		ViewStub stub = (ViewStub) view.findViewById(R.id.vsHeader);

		try {
			View inflated = stub.inflate();

			TextView txtTitle = (TextView) inflated
					.findViewById(R.id.txtVenueHeading);
		
			txtTitle.setText(heading);

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

							FragmentTransaction ft = getFragmentManager().beginTransaction();
							HomeFragment bf = new HomeFragment();

							SharedPreferences prefs = getActivity().getSharedPreferences("com.kfsd", Context.MODE_PRIVATE);
							prefs.edit().clear().apply();

							System.out.println("RUpa inside on create");
							ft.replace(R.id.groupContent, bf,
									getResources().getString(R.string.home));
							ft.disallowAddToBackStack();
							ft.commit();
							
							
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




	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			// preventing default implementation previous to
			// android.os.Build.VERSION_CODES.ECLAIR
			return true;
		}
		return false;

	}

	/**
	 * Overrides the default implementation for KeyEvent.KEYCODE_BACK so that
	 * all systems call onBackPressed().
	 */

	public boolean onKeyUp(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			try {

				getFragmentManager().popBackStack();

			} catch (Exception e) {
				e.printStackTrace();
			}

			return true;

		}

		return false;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.textView1sub:
			if(pageType == 1) {

				i = new Intent();
				i.setClass(getActivity(), FragmentLeaveRequest.class);
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
						| Intent.FLAG_ACTIVITY_CLEAR_TOP);

				startActivity(i);
			}
			else if(pageType == 2) {
				i = new Intent();
				i.setClass(getActivity(), FragmentPermissionRequest.class);
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
						| Intent.FLAG_ACTIVITY_CLEAR_TOP);

				startActivity(i);
			}
			else if (pageType == 3){
				i = new Intent();
				i.setClass(getActivity(), FragmentAttendance.class);
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
						| Intent.FLAG_ACTIVITY_CLEAR_TOP);

				startActivity(i);
			}
			break;

		case R.id.textView2sub:

			if(pageType == 1) {
				i = new Intent();
				i.setClass(getActivity(), FragmentVacation.class);
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
						| Intent.FLAG_ACTIVITY_CLEAR_TOP);

				startActivity(i);
			}
			else if(pageType == 2) {
				i = new Intent();
				i.setClass(getActivity(), FragmentPermission.class);
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
						| Intent.FLAG_ACTIVITY_CLEAR_TOP);

				startActivity(i);
			}
			else if (pageType == 3){
				i = new Intent();
				i.setClass(getActivity(), FragmentMonthlyReportInquiry.class);
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
						| Intent.FLAG_ACTIVITY_CLEAR_TOP);

				startActivity(i);
			}

			break;

			case R.id.textView3sub:
				if(pageType == 1) {
					i = new Intent();
					i.setClass(getActivity(), FragmentVacationBalance.class);
					i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
							| Intent.FLAG_ACTIVITY_CLEAR_TOP);

					startActivity(i);
				}else if(pageType == 2) {
					i = new Intent();
					i.setClass(getActivity(), FragmentAcceptRejectPermissions.class);
					i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
							| Intent.FLAG_ACTIVITY_CLEAR_TOP);

					startActivity(i);
				}
				break;

			case R.id.textView4sub:
				i = new Intent();
				i.setClass(getActivity(), FragmentSickVacationBalance.class);
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
						| Intent.FLAG_ACTIVITY_CLEAR_TOP);

				startActivity(i);
				break;

			case R.id.textView5sub:
				i = new Intent();
				i.setClass(getActivity(), FragmentCancelOrBreakVacation.class);
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
						| Intent.FLAG_ACTIVITY_CLEAR_TOP);
				i.putExtra("cancelOrBreak", 0);

				startActivity(i);
				break;

			case R.id.textView6sub:
				i = new Intent();
				i.setClass(getActivity(), FragmentCancelOrBreakVacation.class);
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
						| Intent.FLAG_ACTIVITY_CLEAR_TOP);
				i.putExtra("cancelOrBreak", 1);
				startActivity(i);
				break;

			case R.id.textView7sub:
				i = new Intent();
				i.setClass(getActivity(), FragmentAcceptRejectVacations.class);
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
						| Intent.FLAG_ACTIVITY_CLEAR_TOP);
				i.putExtra("whichApproval", 0);
				startActivity(i);
				break;

			case R.id.textView8sub:
				i = new Intent();
				i.setClass(getActivity(), FragmentAcceptRejectVacations.class);
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
						| Intent.FLAG_ACTIVITY_CLEAR_TOP);
				i.putExtra("whichApproval", 1);
				startActivity(i);
				break;

			case R.id.textView9sub:
				i = new Intent();
				i.setClass(getActivity(), FragmentAcceptRejectVacations.class);
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
						| Intent.FLAG_ACTIVITY_CLEAR_TOP);
				i.putExtra("whichApproval", 2);
				startActivity(i);
				break;


		default:
			break;
		}

	}

}
