package com.kuwaitKFF.more.bean;

public class ContactUsRequest {
    
   String lang;
   String contactUsType;
   String contactUsName;
   String contactUsMail;
   String contactUsCell;
   String contactUsSub;
   String contactUsCont;
   
   
public ContactUsRequest(String lang, String contactUsType,
		String contactUsName, String contactUsMail, String contactUsCell,
		String contactUsSub, String contactUsCont) {
	super();
	this.lang = lang;
	this.contactUsType = contactUsType;
	this.contactUsName = contactUsName;
	this.contactUsMail = contactUsMail;
	this.contactUsCell = contactUsCell;
	this.contactUsSub = contactUsSub;
	this.contactUsCont = contactUsCont;
}
public String getLang() {
	return lang;
}
public void setLang(String lang) {
	this.lang = lang;
}
public String getContactUsType() {
	return contactUsType;
}
public void setContactUsType(String contactUsType) {
	this.contactUsType = contactUsType;
}
public String getContactUsName() {
	return contactUsName;
}
public void setContactUsName(String contactUsName) {
	this.contactUsName = contactUsName;
}
public String getContactUsMail() {
	return contactUsMail;
}
public void setContactUsMail(String contactUsMail) {
	this.contactUsMail = contactUsMail;
}
public String getContactUsCell() {
	return contactUsCell;
}
public void setContactUsCell(String contactUsCell) {
	this.contactUsCell = contactUsCell;
}
public String getContactUsSub() {
	return contactUsSub;
}
public void setContactUsSub(String contactUsSub) {
	this.contactUsSub = contactUsSub;
}
public String getContactUsCont() {
	return contactUsCont;
}
public void setContactUsCont(String contactUsCont) {
	this.contactUsCont = contactUsCont;
}	
	
}
