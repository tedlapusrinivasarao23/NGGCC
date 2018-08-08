package com.contact.mgmt.forms;

public class LoginContact {

	private String emailId;

	private String password;

	private String phoneNumber;

	private String loginDeviceOS;

	private String loginDeviceID;

	private String loginDeviceOSVersion;

	private String loginDeviceModel;

	private String loginLocation;

	public String getLoginDeviceOSVersion() {
		return loginDeviceOSVersion;
	}

	public void setLoginDeviceOSVersion(String loginDeviceOSVersion) {
		this.loginDeviceOSVersion = loginDeviceOSVersion;
	}

	public String getLoginDeviceModel() {
		return loginDeviceModel;
	}

	public void setLoginDeviceModel(String loginDeviceModel) {
		this.loginDeviceModel = loginDeviceModel;
	}

	public String getLoginDeviceOS() {
		return loginDeviceOS;
	}

	public void setLoginDeviceOS(String loginDeviceOS) {
		this.loginDeviceOS = loginDeviceOS;
	}

	public String getLoginDeviceID() {
		return loginDeviceID;
	}

	public void setLoginDeviceID(String loginDeviceID) {
		this.loginDeviceID = loginDeviceID;
	}

	public String getLoginLocation() {
		return loginLocation;
	}

	public void setLoginLocation(String loginLocation) {
		this.loginLocation = loginLocation;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

}
