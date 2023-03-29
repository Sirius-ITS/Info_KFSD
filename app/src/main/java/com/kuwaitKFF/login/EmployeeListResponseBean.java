package com.kuwaitKFF.login;

public class EmployeeListResponseBean {
	public String getCivilId() {
		return civilId;
	}

	public void setCivilId(String civilId) {
		this.civilId = civilId;
	}

	public String getFileNo() {
		return fileNo;
	}

	public void setFileNo(String fileNo) {
		this.fileNo = fileNo;
	}

	public String getNameAr() {
		return nameAr;
	}

	public void setNameAr(String nameAr) {
		this.nameAr = nameAr;
	}

	public String getNameEn() {
		return nameEn;
	}

	public void setNameEn(String nameEn) {
		this.nameEn = nameEn;
	}

	public String getEmpTypeCode() {
		return empTypeCode;
	}

	public void setEmpTypeCode(String empTypeCode) {
		this.empTypeCode = empTypeCode;
	}

	public String getEntityLevelCode() {
		return entityLevelCode;
	}

	public void setEntityLevelCode(String entityLevelCode) {
		this.entityLevelCode = entityLevelCode;
	}

	public String getManagerEntityId() {
		return managerEntityId;
	}

	public void setManagerEntityId(String managerEntityId) {
		this.managerEntityId = managerEntityId;
	}

	public String getManagerCivilId() {
		return managerCivilId;
	}

	public void setManagerCivilId(String managerCivilId) {
		this.managerCivilId = managerCivilId;
	}

	String civilId,fileNo,nameAr,nameEn,empTypeCode,
	entityLevelCode,managerEntityId,managerCivilId;

}
