package com.kuwaitKFF.approveRejectVacations.models.vacationsDataModels;

import com.google.gson.annotations.SerializedName;

public class EmpLeaveRequestViewItem{

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

	@SerializedName("APP_SERIAL")
	private String vAPPSERIAL;

	@SerializedName("APP_ORG_ID")
	private String appOrgId;

	public String getvAPPSERIAL() {
		return vAPPSERIAL;
	}

	public void setvAPPSERIAL(String vAPPSERIAL) {
		this.vAPPSERIAL = vAPPSERIAL;
	}

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

	public String getAppOrgId() {
		return appOrgId;
	}

	public void setAppOrgId(String appOrgId) {
		this.appOrgId = appOrgId;
	}
}