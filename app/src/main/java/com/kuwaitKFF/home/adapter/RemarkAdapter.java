package com.kuwaitKFF.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.kuwaitKFF.R;
import com.kuwaitKFF.common.MyCommon;
import com.kuwaitKFF.home.RemarkSectorResponseBean;

public class RemarkAdapter extends ArrayAdapter<RemarkSectorResponseBean> {

	public RemarkAdapter(Context context, int resource, String lang) {
		super(context, resource);
		this.context = context;
		this.lang = lang;
	}

	class ViewHolder {
		TextView firstRowLabel;
		TextView firstRowText;
		TextView secondRowLabel;
		TextView secondRowtext;
//		TableRow remarkDtText;
//		TableRow serionrow;
//		TextView TV_remark;
//		TableRow remarktext;
//		TableRow LL_organisation;
//		TextView ET_organisation;
	}
	Context context;
	String lang;
	ViewHolder holder;
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
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
//			holder.remarkDttxt = (TextView) view.findViewById(R.id.ET_remark);

//			holder.remarkDtText = (TableRow) view.findViewById(R.id.LL_remark_date);
//			holder.serionrow = (TableRow) view.findViewById(R.id.LL_serial_num);
//			holder.remarktext = (TableRow) view.findViewById(R.id.LL_remark);
//			holder.LL_organisation = (TableRow) view.findViewById(R.id.LL_organisation);
//			holder.ET_organisation = (TextView) view.findViewById(R.id.ET_organisation);

			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		RemarkSectorResponseBean bean = getItem(position);
//		holder.serialNo.setText(bean.getReqSerial());
//		holder.serialNo.setVisibility(View.GONE);
//		holder.LL_organisation.setVisibility(View.VISIBLE);
		if(lang != null && lang.equalsIgnoreCase(MyCommon.LANGUAGE_ENG))
		{
			holder.firstRowLabel.setText(R.string.organizational_level);
			holder.firstRowText.setText(bean.getNoteOrgNameEn());
		}
		else
		{
			holder.firstRowLabel.setText(R.string.organizational_level);
			holder.firstRowText.setText(bean.getNoteOrgNameAr());
		}
		if (lang != null && lang.equalsIgnoreCase(MyCommon.LANGUAGE_ENG)) 
		{
			holder.secondRowLabel.setText("Remark");
			holder.secondRowtext.setText(bean.getNoteDescEn());
		} else 
		{
			holder.secondRowLabel.setText(R.string.remark);
			holder.secondRowtext.setText(bean.getNoteDescAr());
		}
		//holder.remarktext.setV)
//		if(position%2==0)
//		{
//			holder.remarktext.setBackgroundColor(Color.parseColor("#5B83B6"));
//		}
//		else
//		{
//			holder.remarktext.setBackgroundColor(Color.parseColor("#D7DFEA"));
//		}
		
//		holder.remarkDt.setText(bean.getNoteExecDate());
//		holder.remarkDt.setVisibility(View.GONE);
//		holder.remarkDtText.setVisibility(View.GONE);
//		holder.serionrow.setVisibility(View.GONE);

		return view;
	}
	
	

}
