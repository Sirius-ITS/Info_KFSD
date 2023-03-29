package com.kuwaitKFF.cancelOrBreakVacation.models.vacationData;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class VacationData{

	@SerializedName("leaveRequestView")
	private List<LeaveRequestViewItem> leaveRequestView;

	public void setLeaveRequestView(List<LeaveRequestViewItem> leaveRequestView){
		this.leaveRequestView = leaveRequestView;
	}

	public List<LeaveRequestViewItem> getLeaveRequestView(){
		return leaveRequestView;
	}
}