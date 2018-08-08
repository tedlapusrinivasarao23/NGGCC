package com.contact.mgmt.dummy;

import java.util.List;

import com.contact.mgmt.bos.AddContact;


public class GroupCount {
	private Long count;
	private List<AddContact> contact;
	
	public List<AddContact> getContact() {
		return contact;
	}
	public void setContact(List<AddContact> contact) {
		this.contact = contact;
	}
	public Long getCount() {
		return count;
	}
	public void setCount(Long count) {
		this.count = count;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	private String groupName;

}
