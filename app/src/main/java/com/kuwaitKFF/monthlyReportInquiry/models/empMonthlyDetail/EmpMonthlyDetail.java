package com.kuwaitKFF.monthlyReportInquiry.models.empMonthlyDetail;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class EmpMonthlyDetail{

	@SerializedName("empMonthlyDetail")
	private List<EmpMonthlyDetailItem> empMonthlyDetail;

	public void setEmpMonthlyDetail(List<EmpMonthlyDetailItem> empMonthlyDetail){
		this.empMonthlyDetail = empMonthlyDetail;
	}

	public List<EmpMonthlyDetailItem> getEmpMonthlyDetail(){
		return empMonthlyDetail;
	}
}