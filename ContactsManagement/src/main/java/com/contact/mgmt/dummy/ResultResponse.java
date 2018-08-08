package com.contact.mgmt.dummy;

import java.util.List;

import com.contact.mgmt.bos.AddContact;
import com.contact.mgmt.bos.GroupRef;

public class ResultResponse {
	
	private String result;
	
	private AddContact contact;
	
	private List<AddContact> contactsList;
	
	private GroupRef group;
	
	private byte[] profileImage;
	
	private String profileName;
	
	
	private List<String> unAddedContacts;
	

	

	public List<String> getUnAddedContacts() {
		return unAddedContacts;
	}

	public void setUnAddedContacts(List<String> unAddedContacts) {
		this.unAddedContacts = unAddedContacts;
	}

	public byte[] getProfileImage() {
		return profileImage;
	}

	public void setProfileImage(byte[] profileImage) {
		this.profileImage = profileImage;
	}

	public String getProfileName() {
		return profileName;
	}

	public void setProfileName(String profileName) {
		this.profileName = profileName;
	}

	public GroupRef getGroup() {
		return group;
	}

	public void setGroup(GroupRef group) {
		this.group = group;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public AddContact getContact() {
		return contact;
	}

	public void setContact(AddContact contact) {
		this.contact = contact;
	}

	public List<AddContact> getContactsList() {
		return contactsList;
	}

	public void setContactsList(List<AddContact> contactsList) {
		this.contactsList = contactsList;
	}
	
	
}
