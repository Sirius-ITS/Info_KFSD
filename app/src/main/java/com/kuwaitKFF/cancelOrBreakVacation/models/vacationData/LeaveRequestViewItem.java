package com.kuwaitKFF.cancelOrBreakVacation.models.vacationData;

import com.google.gson.annotations.SerializedName;

public class LeaveRequestViewItem{

	@SerializedName("EMP_NAME")
	private String eMPNAME;

	@SerializedName("VAC_END_DATE")
	private String vACENDDATE;

	@SerializedName("VAC_SERIAL")
	private String vACSERIAL;

	@SerializedName("VAC_START_DATE")
	private String vACSTARTDATE;

	@SerializedName("VACATION_DESC")
	private String vACATIONDESC;

	public void setEMPNAME(String eMPNAME){
		this.eMPNAME = eMPNAME;
	}

	public String getEMPNAME(){
		return eMPNAME;
	}

	public void setVACENDDATE(String vACENDDATE){
		this.vACENDDATE = vACENDDATE;
	}

	public String getVACENDDATE(){
		return vACENDDATE;
	}

	public void setVACSERIAL(String vACSERIAL){
		this.vACSERIAL = vACSERIAL;
	}

	public String getVACSERIAL(){
		return vACSERIAL;
	}

	public void setVACSTARTDATE(String vACSTARTDATE){
		this.vACSTARTDATE = vACSTARTDATE;
	}

	public String getVACSTARTDATE(){
		return vACSTARTDATE;
	}

	public void setVACATIONDESC(String vACATIONDESC){
		this.vACATIONDESC = vACATIONDESC;
	}

	public String getVACATIONDESC(){
		return vACATIONDESC;
	}
}