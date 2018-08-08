package com.contact.mgmt.bos;

import java.util.ArrayList;
import java.util.List;


public class ContactXref {
	
	private int groupId;
	private String ownerNumber;
	private List<Integer> contactIdsList=new ArrayList<Integer>();
	private String groupName;
	
	public int getGroupId() {
		return groupId;
	}
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	

public String getOwnerNumber() {
		return ownerNumber;
	}
	public void setOwnerNumber(String ownerNumber) {
		this.ownerNumber = ownerNumber;
	}





public List<Integer> getContactIdsList() {
		return contactIdsList;
	}
	public void setContactIdsList(List<Integer> contactIdsList) {
		this.contactIdsList = contactIdsList;
	}
public String getGroupName() {
	return groupName;
}
public void setGroupName(String groupName) {
	this.groupName = groupName;
}
}
