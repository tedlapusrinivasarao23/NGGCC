package com.contact.mgmt.dummy;

import java.util.Collection;

import com.contact.mgmt.bos.SignUpUserAddress;

public class LoginResponse {
	
	private String userName;
	private String emailId;
	private String phoneNumber;
	private byte[] profileImage;
	private Collection<SignUpUserAddress> address;
	private boolean paid;
	private String loginMessage;
	private String firstLoginMessage;
	
	
	public String getFirstLoginMessage() {
		return firstLoginMessage;
	}
	public void setFirstLoginMessage(String firstLoginMessage) {
		this.firstLoginMessage = firstLoginMessage;
	}
	public Collection<SignUpUserAddress> getAddress() {
		return address;
	}
	public void setAddress(Collection<SignUpUserAddress> address) {
		this.address = address;
	}
		
	
	public boolean isPaid() {
		return paid;
	}
	public void setPaid(boolean paid) {
		this.paid = paid;
	}
	public byte[] getProfileImage() {
		return profileImage;
	}
	public void setProfileImage(byte[] profileImage) {
		this.profileImage = profileImage;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getLoginMessage() {
		return loginMessage;
	}
	public void setLoginMessage(String loginMessage) {
		this.loginMessage = loginMessage;
	}
	
	

}
