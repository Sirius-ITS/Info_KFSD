package com.kuwaitKFF.vacationsbalance.models.vacationTypes;

import com.google.gson.annotations.SerializedName;

public class ListOfvacationTypesItem{

	@SerializedName("vacationDesc")
	private String vacationDesc;

	@SerializedName("vacationCode")
	private String vacationCode;

	public void setVacationDesc(String vacationDesc){
		this.vacationDesc = vacationDesc;
	}

	public String getVacationDesc(){
		return vacationDesc;
	}

	public void setVacationCode(String vacationCode){
		this.vacationCode = vacationCode;
	}

	public String getVacationCode(){
		return vacationCode;
	}
}