package com.kuwaitKFF.approveRejectVacations.models.vacationsDataModels;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class VacationsData {

	@SerializedName("empLeaveRequestView")
	private List<EmpLeaveRequestViewItem> empLeaveRequestView;

	public void setEmpLeaveRequestView(List<EmpLeaveRequestViewItem> empLeaveRequestView){
		this.empLeaveRequestView = empLeaveRequestView;
	}

	public List<EmpLeaveRequestViewItem> getEmpLeaveRequestView(){
		return empLeaveRequestView;
	}
}