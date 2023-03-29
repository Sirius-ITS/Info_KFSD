package com.kuwaitKFF.sickVacationBalance.models.sickLeaveData;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class SickLeaveData{

	@SerializedName("sickLeaveData")
	private List<SickLeaveDataItem> sickLeaveData;

	public void setSickLeaveData(List<SickLeaveDataItem> sickLeaveData){
		this.sickLeaveData = sickLeaveData;
	}

	public List<SickLeaveDataItem>getSickLeaveData(){
		return sickLeaveData;
	}
}