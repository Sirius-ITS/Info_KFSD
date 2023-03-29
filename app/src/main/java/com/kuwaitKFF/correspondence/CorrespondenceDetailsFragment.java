package com.kuwaitKFF.correspondence;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.kuwaitKFF.R;
import com.kuwaitKFF.base.BaseActivity;
import com.kuwaitKFF.common.MyCommon;
import com.kuwaitKFF.common.MySharedPref;
import com.kuwaitKFF.main.Kfsd;

import butterknife.ButterKnife;

public class CorrespondenceDetailsFragment extends BaseActivity implements
				OnClickListener {

	Kfsd kfsd;
	View view;
	ArrayList<String> circularList;
	ListView listView;
	TextView linkButton;
	TextView fromName, toName, corresNo, corresSub, corresDate, corresBody;
	TextView sfrom, sDesc, sNote, sDate;
	TextView procedures, documents, relMsg;
	CorrespondenceSnapshotBean bean;
	
	public CorrespondenceDetailsFragment() {
		super(R.layout.fragment_correspondence_details);
	}

	@Override
	public void initialization() {
		ButterKnife.bind(this);

	}

	@Override
	public void implementation() {
		kfsd = (Kfsd) getApplication();
		SimpleDateFormat formatterForDisplay = new SimpleDateFormat("yyyy-MM-dd");
		if (MySharedPref.getLanguage(this).equals(MyCommon.LANGUAGE_ENG)) {
			formatterForDisplay = new SimpleDateFormat("dd-MM-yyyy");
		}
		String lang = MySharedPref.getLanguage(this);
		bean = (CorrespondenceSnapshotBean) getIntent()
				.getSerializableExtra("corres_detail");
		
		procedures = (TextView)findViewById(R.id.procedures);
		procedures.setOnClickListener(this);
		documents = (TextView) findViewById(R.id.documents);
		documents.setOnClickListener(this);
		relMsg = (TextView) findViewById(R.id.rel_msges);
		relMsg.setOnClickListener(this);
		
		fromName = (TextView) findViewById(R.id.TV_from);
		if (lang.equalsIgnoreCase(MyCommon.LANGUAGE_AR))
		{
			if(!bean.getCorrespondFromEntityNameAr().equalsIgnoreCase("null")) 
				fromName.setText(bean.getCorrespondFromEntityNameAr());
		} else  {
			if (!bean.getCorrespondFromEntityNameAr().equalsIgnoreCase("null"))
				fromName.setText(bean.getCorrespondFromEntityNameEn());
		}
		
		toName = (TextView) findViewById(R.id.TV_to);
		if (lang.equalsIgnoreCase(MyCommon.LANGUAGE_AR))
		{
			if(!bean.getCorrespondToEntityNameAr().equalsIgnoreCase("null"))
				toName.setText(bean.getCorrespondToEntityNameAr());
		}
		 else
		{
			if (!bean.getCorrespondToEntityNameEn().equalsIgnoreCase("null"))
				toName.setText(bean.getCorrespondToEntityNameEn());
		}

		corresNo = (TextView) findViewById(R.id.TV_no);
		if (!bean.getCorrespondNo().equalsIgnoreCase("null")) {
			corresNo.setText(bean.getCorrespondNo());
		}
		
		corresSub = (TextView) findViewById(R.id.TV_sub);

		if (!bean.getCorrespondSubject().equalsIgnoreCase("null")) {
			corresSub.setText(bean.getCorrespondSubject());
		}

		corresDate = (TextView) findViewById(R.id.TV_date);
		if (bean.getCorrespondDate() != null
				&& !bean.getCorrespondDate().equalsIgnoreCase("null")) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			try {
				corresDate.setText((formatterForDisplay.format(formatter.parse(bean.getCorrespondDate()))));
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
//		corresBody = (TextView) findViewById(R.id.TV_body);
//		if (bean.getCorrespondBody() != null
//				&& !bean.getCorrespondBody().equalsIgnoreCase("null")) {
//			corresBody.setText(bean.getCorrespondBody());
//		}
		

		sfrom = (TextView) findViewById(R.id.TV_snap_from);
		if (lang != null && lang.equalsIgnoreCase(MyCommon.LANGUAGE_AR)
				&& !bean.getSnapshotFromEntityNameAr().equalsIgnoreCase("null")) {
			sfrom.setText(bean.getSnapshotFromEntityNameAr());
		} else if (!bean.getSnapshotFromEntityNameEn().equalsIgnoreCase("null")) {
			sfrom.setText(bean.getSnapshotFromEntityNameEn());
		}
		sDesc = (TextView) findViewById(R.id.snapAction);

		if (lang != null && lang.equalsIgnoreCase(MyCommon.LANGUAGE_AR)
				&& !bean.getSnapshotActionDescAr().equalsIgnoreCase("null")) {
			sDesc.setText(bean.getSnapshotActionDescAr());
		} else if (!bean.getSnapshotActionDescEn().equalsIgnoreCase("null")) {
			sDesc.setText(bean.getSnapshotActionDescEn());
		}
//
//		sDate = (TextView) findViewById(R.id.snapDate);
//		if (!bean.getSnapshotDate().equalsIgnoreCase("null")) {
//			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//			try {
//				sDate.setText(formatterForDisplay.format(formatter.parse(bean.getSnapshotDate())));
//			} catch (ParseException e) {
//				e.printStackTrace();
//			}
//		}
		
		sNote = (TextView) findViewById(R.id.snapNote);
		if (!bean.getSnapshotActionNote().equalsIgnoreCase("null")) {
			sNote.setText(bean.getSnapshotActionNote());
		}

		// linkButton = (TextView)findViewById(R.id.linkButton);
		// linkButton.setOnClickListener(this);
		
		
		
		setHeader(getResources().getString(R.string.correspondence), true, true);

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

				finish();

			} catch (Exception e) {
				e.printStackTrace();
			}

			return true;

		}

		return false;
	}

	@Override
	public void onClick(View v) {
		Intent intent;
		switch(v.getId())
		{
		case R.id.procedures:
			intent = new Intent(this, ProcedureActivity.class);
			intent.putExtra("serialNo", bean.getSnapshotSerial());
			startActivity(intent);
			break;
		case R.id.documents:
			intent = new Intent(this, DocumentActivity.class);
			intent.putExtra("serialNo", bean.getCorrespondSerial());
			startActivity(intent);
			break;
		case R.id.rel_msges:
			intent = new Intent(this, RelatedMsgActivity.class);
			intent.putExtra("serialNo", bean.getCorrespondSerial());
			startActivity(intent);
			break;
		}
	}

}
