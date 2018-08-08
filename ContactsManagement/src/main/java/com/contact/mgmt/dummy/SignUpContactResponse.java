package com.contact.mgmt.dummy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class SignUpContactResponse {
	
	private String subscriptionEndDate;
	
	private int subscriptionDaysLeft;

	public String getSubscriptionEndDate() {
		return subscriptionEndDate;
	}

	public void setSubscriptionEndDate(String subscriptionEndDate) {
		this.subscriptionEndDate = subscriptionEndDate;
	}

	public int getSubscriptionDaysLeft() {
		return subscriptionDaysLeft;
	}

	public void setSubscriptionDaysLeft(int subscriptionDaysLeft) {
		this.subscriptionDaysLeft = subscriptionDaysLeft;
	}
	
	

}
