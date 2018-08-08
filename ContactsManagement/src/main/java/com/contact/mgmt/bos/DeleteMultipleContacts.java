package com.contact.mgmt.bos;

import java.util.List;

public class DeleteMultipleContacts {

	private String ownerNumber;
	private List<Integer> contactIds;
	public String getOwnerNumber() {
		return ownerNumber;
	}
	public void setOwnerNumber(String ownerNumber) {
		this.ownerNumber = ownerNumber;
	}
	public List<Integer> getContactIds() {
		return contactIds;
	}
	public void setContactIds(List<Integer> contactIds) {
		this.contactIds = contactIds;
	}
	
	
}
