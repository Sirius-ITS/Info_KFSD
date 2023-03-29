package com.kuwaitKFF.sickVacationBalance.models.sickLeaveData;

import com.google.gson.annotations.SerializedName;

public class SickLeaveDataItem{

	@SerializedName("SICK_HEALTH_COUNT")
	private int sICKHEALTHCOUNT;

	@SerializedName("SICK_COUNT")
	private int sICKCOUNT;

	@SerializedName("SICK_HOSPITAL_COUNT")
	private int sICKHOSPITALCOUNT;

	public void setSICKHEALTHCOUNT(int sICKHEALTHCOUNT){
		this.sICKHEALTHCOUNT = sICKHEALTHCOUNT;
	}

	public int getSICKHEALTHCOUNT(){
		return sICKHEALTHCOUNT;
	}

	public void setSICKCOUNT(int sICKCOUNT){
		this.sICKCOUNT = sICKCOUNT;
	}

	public int getSICKCOUNT(){
		return sICKCOUNT;
	}

	public void setSICKHOSPITALCOUNT(int sICKHOSPITALCOUNT){
		this.sICKHOSPITALCOUNT = sICKHOSPITALCOUNT;
	}

	public int getSICKHOSPITALCOUNT(){
		return sICKHOSPITALCOUNT;
	}
}