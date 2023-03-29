package com.kuwaitKFF.cancelOrBreakVacation.models.sendRequestModels;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class SendRequest{

	@SerializedName("SendRequest")
	private List<SendRequestItem> sendRequest;

	public void setSendRequest(List<SendRequestItem> sendRequest){
		this.sendRequest = sendRequest;
	}

	public List<SendRequestItem> getSendRequest(){
		return sendRequest;
	}
}