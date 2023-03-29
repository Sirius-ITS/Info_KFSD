package com.kuwaitKFF.decisions;

public class DecisionResponseBean {
	
	public String getDecisionSerial() {
		return decisionSerial;
	}

	public void setDecisionSerial(String decisionSerial) {
		this.decisionSerial = decisionSerial;
	}

	public String getDecisionSubject() {
		return decisionSubject;
	}

	public void setDecisionSubject(String decisionSubject) {
		this.decisionSubject = decisionSubject;
	}

	public String getDecisionFilePath() {
		return decisionFilePath;
	}

	public void setDecisionFilePath(String decisionFilePath) {
		this.decisionFilePath = decisionFilePath;
	}

	public String getDecisionFileExtension() {
		return decisionFileExtension;
	}

	public void setDecisionFileExtension(String decisionFileExtension) {
		this.decisionFileExtension = decisionFileExtension;
	}

	public String getDecisionLangFlag() {
		return decisionLangFlag;
	}

	public void setDecisionLangFlag(String decisionLangFlag) {
		this.decisionLangFlag = decisionLangFlag;
	}

	public String getDecisionDate() {
		return decisionDate;
	}

	public void setDecisionDate(String decisionDate) {
		this.decisionDate = decisionDate;
	}

	String decisionSerial,decisionSubject,
	       decisionFilePath,decisionFileExtension,decisionLangFlag,decisionDate;

}
