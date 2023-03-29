package com.kuwaitKFF.home;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.kuwaitKFF.R;

public class SectorPagerFragment  extends Fragment {
	
	TextView tablFileNo, tableTranscNo, tableTranscType,
	tableProcType, tableProcDate, tableOrganisational,
	tableResponsible;
	Date date;
	String formattedDate = "";
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view=inflater.inflate(R.layout.fragment_sector_pager, null);
		tablFileNo = (TextView) view.findViewById(R.id.fileNoText);
		tableTranscNo = (TextView) view.findViewById(R.id.transactionNo);
		tableTranscType = (TextView) view.findViewById(R.id.transactionType);
		tableProcType = (TextView) view.findViewById(R.id.procedureType);
		tableProcDate = (TextView) view.findViewById(R.id.procedureDate);
		tableOrganisational = (TextView) view.findViewById(R.id.organizational);
		tableResponsible = (TextView) view.findViewById(R.id.responsible);
		
		Bundle bundle=getArguments();
		
		if(bundle!=null){
		
			tablFileNo.setText(bundle.getString("ReqFileNo"));
			tableTranscNo.setText(bundle.getString("ReqSerial"));
			tableProcDate.setText(dateFormatForDate(bundle
					.getString("ReqActionDate")));
			tableTranscType.setText(bundle
					.getString("ReqTypeDesc"));
			tableProcType.setText(bundle.getString("ReqAction"));
			tableResponsible.setText(bundle
					.getString("ReqEmpName"));
			tableOrganisational.setText(bundle
					.getString("ReqOrgName"));
		}
		
		return view;
	}
	
	public String dateFormatForDate(String dateVal) {

		SimpleDateFormat formatter = new SimpleDateFormat(
				"yyyy-MM-dd'T'HH:mm:ss");
		SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy/MM/dd");

		if(dateVal!=null&&!dateVal.isEmpty()&&!dateVal.equals("null")){
		
			try {
				date = formatter.parse(dateVal);
				formattedDate = formatter1.format(date);
			} catch (ParseException e) {				
				e.printStackTrace();
			}
		}
		

		return formattedDate;

	}

}
