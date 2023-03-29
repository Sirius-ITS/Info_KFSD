package com.kuwaitKFF.correspondence;

public class DocumentBean {
	
	String CorrespondDocSerial;
	String CorrespondSerial;
	String CorrespondDocDate;
	String CorrespondDocFilePath;
	String CorrespondDocFileName;
	String CorrespondDocFileExtension;
	String CorrespondDescAr;
	String CorrespondDescEn;
	public DocumentBean(String correspondDocSerial, String correspondSerial,
			String correspondDocDate, String correspondDocFilePath,
			String correspondDocFileName, String correspondDocFileExtension,
			String correspondDescAr, String correspondDescEn) {
		super();
		CorrespondDocSerial = correspondDocSerial;
		CorrespondSerial = correspondSerial;
		CorrespondDocDate = correspondDocDate;
		CorrespondDocFilePath = correspondDocFilePath;
		CorrespondDocFileName = correspondDocFileName;
		CorrespondDocFileExtension = correspondDocFileExtension;
		CorrespondDescAr = correspondDescAr;
		CorrespondDescEn = correspondDescEn;
	}
	public String getCorrespondDocSerial() {
		return CorrespondDocSerial;
	}
	public void setCorrespondDocSerial(String correspondDocSerial) {
		CorrespondDocSerial = correspondDocSerial;
	}
	public String getCorrespondSerial() {
		return CorrespondSerial;
	}
	public void setCorrespondSerial(String correspondSerial) {
		CorrespondSerial = correspondSerial;
	}
	public String getCorrespondDocDate() {
		return CorrespondDocDate;
	}
	public void setCorrespondDocDate(String correspondDocDate) {
		CorrespondDocDate = correspondDocDate;
	}
	public String getCorrespondDocFilePath() {
		return CorrespondDocFilePath;
	}
	public void setCorrespondDocFilePath(String correspondDocFilePath) {
		CorrespondDocFilePath = correspondDocFilePath;
	}
	public String getCorrespondDocFileName() {
		return CorrespondDocFileName;
	}
	public void setCorrespondDocFileName(String correspondDocFileName) {
		CorrespondDocFileName = correspondDocFileName;
	}
	public String getCorrespondDocFileExtension() {
		return CorrespondDocFileExtension;
	}
	public void setCorrespondDocFileExtension(String correspondDocFileExtension) {
		CorrespondDocFileExtension = correspondDocFileExtension;
	}
	public String getCorrespondDescAr() {
		return CorrespondDescAr;
	}
	public void setCorrespondDescAr(String correspondDescAr) {
		CorrespondDescAr = correspondDescAr;
	}
	public String getCorrespondDescEn() {
		return CorrespondDescEn;
	}
	public void setCorrespondDescEn(String correspondDescEn) {
		CorrespondDescEn = correspondDescEn;
	}	
	
	
}
