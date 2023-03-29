package com.kuwaitKFF.vacationsbalance.models.leaveBalanceData;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class LeaveBalance{

	@SerializedName("leaveBalanceData")
	private List<LeaveBalanceDataItem> leaveBalanceData;

	public void setLeaveBalanceData(List<LeaveBalanceDataItem> leaveBalanceData){
		this.leaveBalanceData = leaveBalanceData;
	}

	public List<LeaveBalanceDataItem> getLeaveBalanceData(){
		return leaveBalanceData;
	}
}