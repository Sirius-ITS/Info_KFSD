package com.kuwaitKFF.vacationsbalance.models.vacationTypes;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class VacationTypes{

	@SerializedName("ListOfvacationTypes")
	private List<ListOfvacationTypesItem> listOfvacationTypes;

	public void setListOfvacationTypes(List<ListOfvacationTypesItem> listOfvacationTypes){
		this.listOfvacationTypes = listOfvacationTypes;
	}

	public List<ListOfvacationTypesItem> getListOfvacationTypes(){
		return listOfvacationTypes;
	}
}