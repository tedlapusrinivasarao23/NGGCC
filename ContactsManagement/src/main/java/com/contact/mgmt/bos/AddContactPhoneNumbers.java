package com.contact.mgmt.bos;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class AddContactPhoneNumbers {
	
	@Column(nullable=false)
	private String phoneNumber;
	private String phoneNumberType;
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getPhoneNumberType() {
		return phoneNumberType;
	}
	public void setPhoneNumberType(String phoneNumberType) {
		this.phoneNumberType = phoneNumberType;
	}
	
	
	

}
