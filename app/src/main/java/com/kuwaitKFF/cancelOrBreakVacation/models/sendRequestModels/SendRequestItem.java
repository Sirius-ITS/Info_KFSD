package com.kuwaitKFF.cancelOrBreakVacation.models.sendRequestModels;

import com.google.gson.annotations.SerializedName;

public class SendRequestItem{

	@SerializedName("RESPONSE_FLAG")
	private int rESPONSEFLAG;

	@SerializedName("RESPONSE_MESSAGE")
	private String rESPONSEMESSAGE;

	public void setRESPONSEFLAG(int rESPONSEFLAG){
		this.rESPONSEFLAG = rESPONSEFLAG;
	}

	public int getRESPONSEFLAG(){
		return rESPONSEFLAG;
	}

	public void setRESPONSEMESSAGE(String rESPONSEMESSAGE){
		this.rESPONSEMESSAGE = rESPONSEMESSAGE;
	}

	public String getRESPONSEMESSAGE(){
		return rESPONSEMESSAGE;
	}
}