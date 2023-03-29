package com.kuwaitKFF.advertisements;

import java.util.ArrayList;

public class AdvertisementListBean {
	
	String AdvertiseTypeCode;
	String AdvertiseTypeDescAr;
	String AdvertiseTypeDescEn;
	ArrayList<AdvertisementResponseBean> advrtList;
	
	public ArrayList<AdvertisementResponseBean> getAdvrtList() {
		return advrtList;
	}
	public void setAdvrtList(ArrayList<AdvertisementResponseBean> advrtList) {
		this.advrtList = advrtList;
	}
	public String getAdvertiseTypeCode() {
		return AdvertiseTypeCode;
	}
	public void setAdvertiseTypeCode(String advertiseTypeCode) {
		AdvertiseTypeCode = advertiseTypeCode;
	}
	public String getAdvertiseTypeDescAr() {
		return AdvertiseTypeDescAr;
	}
	public void setAdvertiseTypeDescAr(String advertiseTypeDescAr) {
		AdvertiseTypeDescAr = advertiseTypeDescAr;
	}
	public String getAdvertiseTypeDescEn() {
		return AdvertiseTypeDescEn;
	}
	public void setAdvertiseTypeDescEn(String advertiseTypeDescEn) {
		AdvertiseTypeDescEn = advertiseTypeDescEn;
	}
	
	
}
