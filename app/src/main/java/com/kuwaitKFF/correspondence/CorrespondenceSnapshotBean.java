package com.kuwaitKFF.correspondence;

import java.io.Serializable;

public class CorrespondenceSnapshotBean  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2972415255135756828L;
	String CorrespondSerial;   
	String CorrespondTypeCode;    
    String CorrespondTypeDescAr;   
    String CorrespondTypeDescEn;   
    String CorrespondContCode;    
    String CorrespondContDescAr;   
    String CorrespondContDescEn;   
    String CorrespondSecurityCode;
    String CorrespondSecurityDescAr;
    String CorrespondSecurityDescEn;
    String CorrespondPriorityCode; 
    String CorrespondPriorityDescAr;
    String CorrespondPriorityDescEn;
    String CorrespondFromEntityId;    
    String CorrespondFromEntityNameAr;
    String CorrespondFromEntityNameEn;
    String CorrespondToEntityId;  
    String CorrespondToEntityNameAr;  
    String CorrespondToEntityNameEn;  
    String CorrespondNo;    
    String CorrespondDate;
    String CorrespondToEntitiesId;
    String CorrespondToEntitiesNameAr;
    String CorrespondToEntitiesNameEn;
    String CorrespondCcEntitiesId;
    String CorrespondCcEntitiesNameAr;
    String CorrespondCcEntitiesNameEn;
    String CorrespondReceiveDate;  
    String CorrespondEntryDate;    
    String CorrespondEntityNo;    
    String CorrespondSubject;    
    String CorrespondBody;   
    String CorrespondFlag;    
    String SnapshotSerial; 
    String SnapshotEntityId;    
    String SnapshotEmpCivilId;  
    String SnapshotToNameAr;  
    String SnapshotToNameEn;  
    String SnapshotDate;   
    String SnapshotRelatedSerial;
    String SnapshotFromEntityId; 
    String SnapshotFromEntityNameAr; 
    String SnapshotFromEntityNameEn; 
    String SnapshotActionEmpCivilId;   
    String SnapshotActionEmpNameAr;  
    String SnapshotActionEmpNameEn;  
    String SnapshotActionCode;    
    String SnapshotActionDescAr;   
    String SnapshotActionDescEn;   
    String SnapshotActionEndDate;
    String SnapshotActionNote;  
    String SnapshotReplyEmpCivilId;   
    String SnapshotReplyEmpNameAr;  
    String SnapshotReplyEmpNameEn;  
    String SnapshotReplyDate;    
    String SnapshotReplyNote;   
    String SnapshotFollowupFlag; 
    String SnapshotStatusCode;    
    String SnapshotStatusDescAr;   
    String SnapshotStatusDescEn;
    
    
    
	public CorrespondenceSnapshotBean(String correspondSerial,
			String correspondTypeCode, String correspondTypeDescAr,
			String correspondTypeDescEn, String correspondContCode,
			String correspondContDescAr, String correspondContDescEn,
			String correspondSecurityCode, String correspondSecurityDescAr,
			String correspondSecurityDescEn, String correspondPriorityCode,
			String correspondPriorityDescAr, String correspondPriorityDescEn,
			String correspondFromEntityId, String correspondFromEntityNameAr,
			String correspondFromEntityNameEn, String correspondToEntityId,
			String correspondToEntityNameAr, String correspondToEntityNameEn,
			String correspondNo, String correspondDate,
			String correspondToEntitiesId, String correspondToEntitiesNameAr,
			String correspondToEntitiesNameEn, String correspondCcEntitiesId,
			String correspondCcEntitiesNameAr,
			String correspondCcEntitiesNameEn, String correspondReceiveDate,
			String correspondEntryDate, String correspondEntityNo,
			String correspondSubject, String correspondBody,
			String correspondFlag, String snapshotSerial,
			String snapshotEntityId, String snapshotEmpCivilId,
			String snapshotToNameAr, String snapshotToNameEn,
			String snapshotDate, String snapshotRelatedSerial,
			String snapshotFromEntityId, String snapshotFromEntityNameAr,
			String snapshotFromEntityNameEn, String snapshotActionEmpCivilId,
			String snapshotActionEmpNameAr, String snapshotActionEmpNameEn,
			String snapshotActionCode, String snapshotActionDescAr,
			String snapshotActionDescEn, String snapshotActionEndDate,
			String snapshotActionNote, String snapshotReplyEmpCivilId,
			String snapshotReplyEmpNameAr, String snapshotReplyEmpNameEn,
			String snapshotReplyDate, String snapshotReplyNote,
			String snapshotFollowupFlag, String snapshotStatusCode,
			String snapshotStatusDescAr, String snapshotStatusDescEn) {
		super();
		CorrespondSerial = correspondSerial;
		CorrespondTypeCode = correspondTypeCode;
		CorrespondTypeDescAr = correspondTypeDescAr;
		CorrespondTypeDescEn = correspondTypeDescEn;
		CorrespondContCode = correspondContCode;
		CorrespondContDescAr = correspondContDescAr;
		CorrespondContDescEn = correspondContDescEn;
		CorrespondSecurityCode = correspondSecurityCode;
		CorrespondSecurityDescAr = correspondSecurityDescAr;
		CorrespondSecurityDescEn = correspondSecurityDescEn;
		CorrespondPriorityCode = correspondPriorityCode;
		CorrespondPriorityDescAr = correspondPriorityDescAr;
		CorrespondPriorityDescEn = correspondPriorityDescEn;
		CorrespondFromEntityId = correspondFromEntityId;
		CorrespondFromEntityNameAr = correspondFromEntityNameAr;
		CorrespondFromEntityNameEn = correspondFromEntityNameEn;
		CorrespondToEntityId = correspondToEntityId;
		CorrespondToEntityNameAr = correspondToEntityNameAr;
		CorrespondToEntityNameEn = correspondToEntityNameEn;
		CorrespondNo = correspondNo;
		CorrespondDate = correspondDate;
		CorrespondToEntitiesId = correspondToEntitiesId;
		CorrespondToEntitiesNameAr = correspondToEntitiesNameAr;
		CorrespondToEntitiesNameEn = correspondToEntitiesNameEn;
		CorrespondCcEntitiesId = correspondCcEntitiesId;
		CorrespondCcEntitiesNameAr = correspondCcEntitiesNameAr;
		CorrespondCcEntitiesNameEn = correspondCcEntitiesNameEn;
		CorrespondReceiveDate = correspondReceiveDate;
		CorrespondEntryDate = correspondEntryDate;
		CorrespondEntityNo = correspondEntityNo;
		CorrespondSubject = correspondSubject;
		CorrespondBody = correspondBody;
		CorrespondFlag = correspondFlag;
		SnapshotSerial = snapshotSerial;
		SnapshotEntityId = snapshotEntityId;
		SnapshotEmpCivilId = snapshotEmpCivilId;
		SnapshotToNameAr = snapshotToNameAr;
		SnapshotToNameEn = snapshotToNameEn;
		SnapshotDate = snapshotDate;
		SnapshotRelatedSerial = snapshotRelatedSerial;
		SnapshotFromEntityId = snapshotFromEntityId;
		SnapshotFromEntityNameAr = snapshotFromEntityNameAr;
		SnapshotFromEntityNameEn = snapshotFromEntityNameEn;
		SnapshotActionEmpCivilId = snapshotActionEmpCivilId;
		SnapshotActionEmpNameAr = snapshotActionEmpNameAr;
		SnapshotActionEmpNameEn = snapshotActionEmpNameEn;
		SnapshotActionCode = snapshotActionCode;
		SnapshotActionDescAr = snapshotActionDescAr;
		SnapshotActionDescEn = snapshotActionDescEn;
		SnapshotActionEndDate = snapshotActionEndDate;
		SnapshotActionNote = snapshotActionNote;
		SnapshotReplyEmpCivilId = snapshotReplyEmpCivilId;
		SnapshotReplyEmpNameAr = snapshotReplyEmpNameAr;
		SnapshotReplyEmpNameEn = snapshotReplyEmpNameEn;
		SnapshotReplyDate = snapshotReplyDate;
		SnapshotReplyNote = snapshotReplyNote;
		SnapshotFollowupFlag = snapshotFollowupFlag;
		SnapshotStatusCode = snapshotStatusCode;
		SnapshotStatusDescAr = snapshotStatusDescAr;
		SnapshotStatusDescEn = snapshotStatusDescEn;
	}
	public String getCorrespondSerial() {
		return CorrespondSerial;
	}
	public void setCorrespondSerial(String correspondSerial) {
		CorrespondSerial = correspondSerial;
	}
	public String getCorrespondTypeCode() {
		return CorrespondTypeCode;
	}
	public void setCorrespondTypeCode(String correspondTypeCode) {
		CorrespondTypeCode = correspondTypeCode;
	}
	public String getCorrespondTypeDescAr() {
		return CorrespondTypeDescAr;
	}
	public void setCorrespondTypeDescAr(String correspondTypeDescAr) {
		CorrespondTypeDescAr = correspondTypeDescAr;
	}
	public String getCorrespondTypeDescEn() {
		return CorrespondTypeDescEn;
	}
	public void setCorrespondTypeDescEn(String correspondTypeDescEn) {
		CorrespondTypeDescEn = correspondTypeDescEn;
	}
	public String getCorrespondContCode() {
		return CorrespondContCode;
	}
	public void setCorrespondContCode(String correspondContCode) {
		CorrespondContCode = correspondContCode;
	}
	public String getCorrespondContDescAr() {
		return CorrespondContDescAr;
	}
	public void setCorrespondContDescAr(String correspondContDescAr) {
		CorrespondContDescAr = correspondContDescAr;
	}
	public String getCorrespondContDescEn() {
		return CorrespondContDescEn;
	}
	public void setCorrespondContDescEn(String correspondContDescEn) {
		CorrespondContDescEn = correspondContDescEn;
	}
	public String getCorrespondSecurityCode() {
		return CorrespondSecurityCode;
	}
	public void setCorrespondSecurityCode(String correspondSecurityCode) {
		CorrespondSecurityCode = correspondSecurityCode;
	}
	public String getCorrespondSecurityDescAr() {
		return CorrespondSecurityDescAr;
	}
	public void setCorrespondSecurityDescAr(String correspondSecurityDescAr) {
		CorrespondSecurityDescAr = correspondSecurityDescAr;
	}
	public String getCorrespondSecurityDescEn() {
		return CorrespondSecurityDescEn;
	}
	public void setCorrespondSecurityDescEn(String correspondSecurityDescEn) {
		CorrespondSecurityDescEn = correspondSecurityDescEn;
	}
	public String getCorrespondPriorityCode() {
		return CorrespondPriorityCode;
	}
	public void setCorrespondPriorityCode(String correspondPriorityCode) {
		CorrespondPriorityCode = correspondPriorityCode;
	}
	public String getCorrespondPriorityDescAr() {
		return CorrespondPriorityDescAr;
	}
	public void setCorrespondPriorityDescAr(String correspondPriorityDescAr) {
		CorrespondPriorityDescAr = correspondPriorityDescAr;
	}
	public String getCorrespondPriorityDescEn() {
		return CorrespondPriorityDescEn;
	}
	public void setCorrespondPriorityDescEn(String correspondPriorityDescEn) {
		CorrespondPriorityDescEn = correspondPriorityDescEn;
	}
	public String getCorrespondFromEntityId() {
		return CorrespondFromEntityId;
	}
	public void setCorrespondFromEntityId(String correspondFromEntityId) {
		CorrespondFromEntityId = correspondFromEntityId;
	}
	public String getCorrespondFromEntityNameAr() {
		return CorrespondFromEntityNameAr;
	}
	public void setCorrespondFromEntityNameAr(String correspondFromEntityNameAr) {
		CorrespondFromEntityNameAr = correspondFromEntityNameAr;
	}
	public String getCorrespondFromEntityNameEn() {
		return CorrespondFromEntityNameEn;
	}
	public void setCorrespondFromEntityNameEn(String correspondFromEntityNameEn) {
		CorrespondFromEntityNameEn = correspondFromEntityNameEn;
	}
	public String getCorrespondToEntityId() {
		return CorrespondToEntityId;
	}
	public void setCorrespondToEntityId(String correspondToEntityId) {
		CorrespondToEntityId = correspondToEntityId;
	}
	public String getCorrespondToEntityNameAr() {
		return CorrespondToEntityNameAr;
	}
	public void setCorrespondToEntityNameAr(String correspondToEntityNameAr) {
		CorrespondToEntityNameAr = correspondToEntityNameAr;
	}
	public String getCorrespondToEntityNameEn() {
		return CorrespondToEntityNameEn;
	}
	public void setCorrespondToEntityNameEn(String correspondToEntityNameEn) {
		CorrespondToEntityNameEn = correspondToEntityNameEn;
	}
	public String getCorrespondNo() {
		return CorrespondNo;
	}
	public void setCorrespondNo(String correspondNo) {
		CorrespondNo = correspondNo;
	}
	public String getCorrespondDate() {
		return CorrespondDate;
	}
	public void setCorrespondDate(String correspondDate) {
		CorrespondDate = correspondDate;
	}
	public String getCorrespondToEntitiesId() {
		return CorrespondToEntitiesId;
	}
	public void setCorrespondToEntitiesId(String correspondToEntitiesId) {
		CorrespondToEntitiesId = correspondToEntitiesId;
	}
	public String getCorrespondToEntitiesNameAr() {
		return CorrespondToEntitiesNameAr;
	}
	public void setCorrespondToEntitiesNameAr(String correspondToEntitiesNameAr) {
		CorrespondToEntitiesNameAr = correspondToEntitiesNameAr;
	}
	public String getCorrespondToEntitiesNameEn() {
		return CorrespondToEntitiesNameEn;
	}
	public void setCorrespondToEntitiesNameEn(String correspondToEntitiesNameEn) {
		CorrespondToEntitiesNameEn = correspondToEntitiesNameEn;
	}
	public String getCorrespondCcEntitiesId() {
		return CorrespondCcEntitiesId;
	}
	public void setCorrespondCcEntitiesId(String correspondCcEntitiesId) {
		CorrespondCcEntitiesId = correspondCcEntitiesId;
	}
	public String getCorrespondCcEntitiesNameAr() {
		return CorrespondCcEntitiesNameAr;
	}
	public void setCorrespondCcEntitiesNameAr(String correspondCcEntitiesNameAr) {
		CorrespondCcEntitiesNameAr = correspondCcEntitiesNameAr;
	}
	public String getCorrespondCcEntitiesNameEn() {
		return CorrespondCcEntitiesNameEn;
	}
	public void setCorrespondCcEntitiesNameEn(String correspondCcEntitiesNameEn) {
		CorrespondCcEntitiesNameEn = correspondCcEntitiesNameEn;
	}
	public String getCorrespondReceiveDate() {
		return CorrespondReceiveDate;
	}
	public void setCorrespondReceiveDate(String correspondReceiveDate) {
		CorrespondReceiveDate = correspondReceiveDate;
	}
	public String getCorrespondEntryDate() {
		return CorrespondEntryDate;
	}
	public void setCorrespondEntryDate(String correspondEntryDate) {
		CorrespondEntryDate = correspondEntryDate;
	}
	public String getCorrespondEntityNo() {
		return CorrespondEntityNo;
	}
	public void setCorrespondEntityNo(String correspondEntityNo) {
		CorrespondEntityNo = correspondEntityNo;
	}
	public String getCorrespondSubject() {
		return CorrespondSubject;
	}
	public void setCorrespondSubject(String correspondSubject) {
		CorrespondSubject = correspondSubject;
	}
	public String getCorrespondBody() {
		return CorrespondBody;
	}
	public void setCorrespondBody(String correspondBody) {
		CorrespondBody = correspondBody;
	}
	public String getCorrespondFlag() {
		return CorrespondFlag;
	}
	public void setCorrespondFlag(String correspondFlag) {
		CorrespondFlag = correspondFlag;
	}
	public String getSnapshotSerial() {
		return SnapshotSerial;
	}
	public void setSnapshotSerial(String snapshotSerial) {
		SnapshotSerial = snapshotSerial;
	}
	public String getSnapshotEntityId() {
		return SnapshotEntityId;
	}
	public void setSnapshotEntityId(String snapshotEntityId) {
		SnapshotEntityId = snapshotEntityId;
	}
	public String getSnapshotEmpCivilId() {
		return SnapshotEmpCivilId;
	}
	public void setSnapshotEmpCivilId(String snapshotEmpCivilId) {
		SnapshotEmpCivilId = snapshotEmpCivilId;
	}
	public String getSnapshotToNameAr() {
		return SnapshotToNameAr;
	}
	public void setSnapshotToNameAr(String snapshotToNameAr) {
		SnapshotToNameAr = snapshotToNameAr;
	}
	public String getSnapshotToNameEn() {
		return SnapshotToNameEn;
	}
	public void setSnapshotToNameEn(String snapshotToNameEn) {
		SnapshotToNameEn = snapshotToNameEn;
	}
	public String getSnapshotDate() {
		return SnapshotDate;
	}
	public void setSnapshotDate(String snapshotDate) {
		SnapshotDate = snapshotDate;
	}
	public String getSnapshotRelatedSerial() {
		return SnapshotRelatedSerial;
	}
	public void setSnapshotRelatedSerial(String snapshotRelatedSerial) {
		SnapshotRelatedSerial = snapshotRelatedSerial;
	}
	public String getSnapshotFromEntityId() {
		return SnapshotFromEntityId;
	}
	public void setSnapshotFromEntityId(String snapshotFromEntityId) {
		SnapshotFromEntityId = snapshotFromEntityId;
	}
	public String getSnapshotFromEntityNameAr() {
		return SnapshotFromEntityNameAr;
	}
	public void setSnapshotFromEntityNameAr(String snapshotFromEntityNameAr) {
		SnapshotFromEntityNameAr = snapshotFromEntityNameAr;
	}
	public String getSnapshotFromEntityNameEn() {
		return SnapshotFromEntityNameEn;
	}
	public void setSnapshotFromEntityNameEn(String snapshotFromEntityNameEn) {
		SnapshotFromEntityNameEn = snapshotFromEntityNameEn;
	}
	public String getSnapshotActionEmpCivilId() {
		return SnapshotActionEmpCivilId;
	}
	public void setSnapshotActionEmpCivilId(String snapshotActionEmpCivilId) {
		SnapshotActionEmpCivilId = snapshotActionEmpCivilId;
	}
	public String getSnapshotActionEmpNameAr() {
		return SnapshotActionEmpNameAr;
	}
	public void setSnapshotActionEmpNameAr(String snapshotActionEmpNameAr) {
		SnapshotActionEmpNameAr = snapshotActionEmpNameAr;
	}
	public String getSnapshotActionEmpNameEn() {
		return SnapshotActionEmpNameEn;
	}
	public void setSnapshotActionEmpNameEn(String snapshotActionEmpNameEn) {
		SnapshotActionEmpNameEn = snapshotActionEmpNameEn;
	}
	public String getSnapshotActionCode() {
		return SnapshotActionCode;
	}
	public void setSnapshotActionCode(String snapshotActionCode) {
		SnapshotActionCode = snapshotActionCode;
	}
	public String getSnapshotActionDescAr() {
		return SnapshotActionDescAr;
	}
	public void setSnapshotActionDescAr(String snapshotActionDescAr) {
		SnapshotActionDescAr = snapshotActionDescAr;
	}
	public String getSnapshotActionDescEn() {
		return SnapshotActionDescEn;
	}
	public void setSnapshotActionDescEn(String snapshotActionDescEn) {
		SnapshotActionDescEn = snapshotActionDescEn;
	}
	public String getSnapshotActionEndDate() {
		return SnapshotActionEndDate;
	}
	public void setSnapshotActionEndDate(String snapshotActionEndDate) {
		SnapshotActionEndDate = snapshotActionEndDate;
	}
	public String getSnapshotActionNote() {
		return SnapshotActionNote;
	}
	public void setSnapshotActionNote(String snapshotActionNote) {
		SnapshotActionNote = snapshotActionNote;
	}
	public String getSnapshotReplyEmpCivilId() {
		return SnapshotReplyEmpCivilId;
	}
	public void setSnapshotReplyEmpCivilId(String snapshotReplyEmpCivilId) {
		SnapshotReplyEmpCivilId = snapshotReplyEmpCivilId;
	}
	public String getSnapshotReplyEmpNameAr() {
		return SnapshotReplyEmpNameAr;
	}
	public void setSnapshotReplyEmpNameAr(String snapshotReplyEmpNameAr) {
		SnapshotReplyEmpNameAr = snapshotReplyEmpNameAr;
	}
	public String getSnapshotReplyEmpNameEn() {
		return SnapshotReplyEmpNameEn;
	}
	public void setSnapshotReplyEmpNameEn(String snapshotReplyEmpNameEn) {
		SnapshotReplyEmpNameEn = snapshotReplyEmpNameEn;
	}
	public String getSnapshotReplyDate() {
		return SnapshotReplyDate;
	}
	public void setSnapshotReplyDate(String snapshotReplyDate) {
		SnapshotReplyDate = snapshotReplyDate;
	}
	public String getSnapshotReplyNote() {
		return SnapshotReplyNote;
	}
	public void setSnapshotReplyNote(String snapshotReplyNote) {
		SnapshotReplyNote = snapshotReplyNote;
	}
	public String getSnapshotFollowupFlag() {
		return SnapshotFollowupFlag;
	}
	public void setSnapshotFollowupFlag(String snapshotFollowupFlag) {
		SnapshotFollowupFlag = snapshotFollowupFlag;
	}
	public String getSnapshotStatusCode() {
		return SnapshotStatusCode;
	}
	public void setSnapshotStatusCode(String snapshotStatusCode) {
		SnapshotStatusCode = snapshotStatusCode;
	}
	public String getSnapshotStatusDescAr() {
		return SnapshotStatusDescAr;
	}
	public void setSnapshotStatusDescAr(String snapshotStatusDescAr) {
		SnapshotStatusDescAr = snapshotStatusDescAr;
	}
	public String getSnapshotStatusDescEn() {
		return SnapshotStatusDescEn;
	}
	public void setSnapshotStatusDescEn(String snapshotStatusDescEn) {
		SnapshotStatusDescEn = snapshotStatusDescEn;
	}

}
