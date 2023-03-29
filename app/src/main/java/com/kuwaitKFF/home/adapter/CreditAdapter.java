package com.kuwaitKFF.home.adapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.kuwaitKFF.R;
import com.kuwaitKFF.common.MyCommon;
import com.kuwaitKFF.home.CreditSectorResponseBean;

public class CreditAdapter extends ArrayAdapter<CreditSectorResponseBean> {

	public CreditAdapter(Context context, int resource, String lang) {
		super(context, resource);
		this.context = context;
		this.lang = lang;
	}

	class ViewHolder {
		TextView firstRowLabel;
		TextView firstRowText;
		TextView secondRowLabel;
		TextView secondRowtext;
	}
	Context context;
	String lang;
	ViewHolder holder;

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		System.out.println("Rupa inside getView");
		View view = convertView;
		if (view == null) 
		{
			view = LayoutInflater.from(context).inflate(
					R.layout.row_credit_sector, null);
			holder = new ViewHolder();

			holder.firstRowLabel = (TextView) view.findViewById(R.id.firstRowLabel);
			holder.firstRowText = (TextView) view.findViewById(R.id.firstRowtxt);
			holder.secondRowLabel = (TextView) view.findViewById(R.id.secondRowLabel);
			holder.secondRowtext = (TextView) view.findViewById(R.id.secondRowtxt);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		
		CreditSectorResponseBean bean = getItem(position);
		//holder.serialNo.setText(bean.getReqSerial());

		holder.firstRowLabel.setText(R.string.date);
		holder.firstRowText.setText(dateFormatForDate(bean.getReqOrgDate()));


		if (lang != null && lang.equalsIgnoreCase(MyCommon.LANGUAGE_ENG)) {
			holder.secondRowLabel.setText(R.string.organizational_level);
			holder.secondRowtext.setText(bean.getReqOrgNameEn());
		} else {

			holder.secondRowLabel.setText(R.string.organizational_level);
			holder.secondRowtext.setText(bean.getReqOrgNameAr());
		}

		
//		holder.credtirow.setVisibility(View.GONE);
		return view;
	}

	public String dateFormatForDate(String dateVal) {

		Date date;
		String formattedDate = null;

		SimpleDateFormat formatter = new SimpleDateFormat(
				"yyyy-MM-dd'T'HH:mm:ss");
		SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy");

		try {
			date = formatter.parse(dateVal);
			formattedDate = formatter1.format(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return formattedDate;

	}

}
