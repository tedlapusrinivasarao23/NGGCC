package com.contact.mgmt.bos;

import javax.persistence.Embeddable;

@Embeddable
public class AddContactEmails {
	
	private String emailId;
	private String emailIdType;
	
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getEmailIdType() {
		return emailIdType;
	}
	public void setEmailIdType(String emailIdType) {
		this.emailIdType = emailIdType;
	}
	
	

}
