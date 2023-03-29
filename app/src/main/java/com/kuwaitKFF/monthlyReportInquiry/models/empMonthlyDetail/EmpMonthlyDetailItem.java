package com.kuwaitKFF.monthlyReportInquiry.models.empMonthlyDetail;

import com.google.gson.annotations.SerializedName;

public class EmpMonthlyDetailItem{

	@SerializedName("ABSENT_DAYS_COUNT")
	private int aBSENTDAYSCOUNT;

	@SerializedName("TOTAL_LATE_DISCOUNT")
	private int tOTALLATEDISCOUNT;

	@SerializedName("ABSENT_COUNT")
	private int aBSENTCOUNT;

	@SerializedName("ABSENT_DAYS")
	private String aBSENTDAYS;

	@SerializedName("VAC_COUNT")
	private int vACCOUNT;

	@SerializedName("VAC_DAYS")
	private String vACDAYS;

	@SerializedName("MESSION_COUNT")
	private int mESSIONCOUNT;

	@SerializedName("SICK_COUNT")
	private int sICKCOUNT;

	@SerializedName("NOT_OUT_COUNT")
	private int nOTOUTCOUNT;

	@SerializedName("LATE_AMOUNT_PER_MONTH")
	private String lATEAMOUNTPERMONTH;

	@SerializedName("NOT_IN_COUNT")
	private int nOTINCOUNT;

	@SerializedName("ABSENT_FULL_DAYS")
	private String aBSENTFULLDAYS;

	public void setABSENTDAYSCOUNT(int aBSENTDAYSCOUNT){
		this.aBSENTDAYSCOUNT = aBSENTDAYSCOUNT;
	}

	public int getABSENTDAYSCOUNT(){
		return aBSENTDAYSCOUNT;
	}

	public void setTOTALLATEDISCOUNT(int tOTALLATEDISCOUNT){
		this.tOTALLATEDISCOUNT = tOTALLATEDISCOUNT;
	}

	public int getTOTALLATEDISCOUNT(){
		return tOTALLATEDISCOUNT;
	}

	public void setABSENTCOUNT(int aBSENTCOUNT){
		this.aBSENTCOUNT = aBSENTCOUNT;
	}

	public int getABSENTCOUNT(){
		return aBSENTCOUNT;
	}

	public void setABSENTDAYS(String aBSENTDAYS){
		this.aBSENTDAYS = aBSENTDAYS;
	}

	public String getABSENTDAYS(){
		return aBSENTDAYS;
	}

	public void setVACCOUNT(int vACCOUNT){
		this.vACCOUNT = vACCOUNT;
	}

	public int getVACCOUNT(){
		return vACCOUNT;
	}

	public void setVACDAYS(String vACDAYS){
		this.vACDAYS = vACDAYS;
	}

	public String getVACDAYS(){
		return vACDAYS;
	}

	public void setMESSIONCOUNT(int mESSIONCOUNT){
		this.mESSIONCOUNT = mESSIONCOUNT;
	}

	public int getMESSIONCOUNT(){
		return mESSIONCOUNT;
	}

	public void setSICKCOUNT(int sICKCOUNT){
		this.sICKCOUNT = sICKCOUNT;
	}

	public int getSICKCOUNT(){
		return sICKCOUNT;
	}

	public void setNOTOUTCOUNT(int nOTOUTCOUNT){
		this.nOTOUTCOUNT = nOTOUTCOUNT;
	}

	public int getNOTOUTCOUNT(){
		return nOTOUTCOUNT;
	}

	public void setLATEAMOUNTPERMONTH(String lATEAMOUNTPERMONTH){
		this.lATEAMOUNTPERMONTH = lATEAMOUNTPERMONTH;
	}

	public String getLATEAMOUNTPERMONTH(){
		return lATEAMOUNTPERMONTH;
	}

	public void setNOTINCOUNT(int nOTINCOUNT){
		this.nOTINCOUNT = nOTINCOUNT;
	}

	public int getNOTINCOUNT(){
		return nOTINCOUNT;
	}

	public void setABSENTFULLDAYS(String aBSENTFULLDAYS){
		this.aBSENTFULLDAYS = aBSENTFULLDAYS;
	}

	public String getABSENTFULLDAYS(){
		return aBSENTFULLDAYS;
	}
}