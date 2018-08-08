package com.contact.mgmt.sharedbos;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class SharedAddContactPhoneNumbers {
	
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
