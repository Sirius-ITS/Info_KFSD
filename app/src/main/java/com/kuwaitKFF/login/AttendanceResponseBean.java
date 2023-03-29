package com.kuwaitKFF.login;

public class AttendanceResponseBean {
	
	public String getEmpSignAmInTime() {
		return empSignAmInTime;
	}

	public void setEmpSignAmInTime(String empSignAmInTime) {
		this.empSignAmInTime = empSignAmInTime;
	}

	public String getEmpSignAmOutTime() {
		return empSignAmOutTime;
	}

	public void setEmpSignAmOutTime(String empSignAmOutTime) {
		this.empSignAmOutTime = empSignAmOutTime;
	}

	public String getEmpSignPmInTime() {
		return empSignPmInTime;
	}

	public void setEmpSignPmInTime(String empSignPmInTime) {
		this.empSignPmInTime = empSignPmInTime;
	}

	public String getEmpSignPmOutTime() {
		return empSignPmOutTime;
	}

	public void setEmpSignPmOutTime(String empSignPmOutTime) {
		this.empSignPmOutTime = empSignPmOutTime;
	}

	public String getEmpAttendanceDate() {
		return empAttendanceDate;
	}

	public void setEmpAttendanceDate(String empAttendanceDate) {
		this.empAttendanceDate = empAttendanceDate;
	}

	public String getEmpCivilId() {
		return empCivilId;
	}

	public void setEmpCivilId(String empCivilId) {
		this.empCivilId = empCivilId;
	}

	public String getEmpDateNoteAr() {
		return empDateNoteAr;
	}

	public void setEmpDateNoteAr(String empDateNoteAr) {
		this.empDateNoteAr = empDateNoteAr;
	}

	public String getEmpDateNoteEn() {
		return empDateNoteEn;
	}

	public void setEmpDateNoteEn(String empDateNoteEn) {
		this.empDateNoteEn = empDateNoteEn;
	}

	
	String empAttendanceDate,empCivilId,empDateNoteAr,
	       empDateNoteEn,empSignAmInTime,empSignAmOutTime,empSignPmInTime,empSignPmOutTime;

}
