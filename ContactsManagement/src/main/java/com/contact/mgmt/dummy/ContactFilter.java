package com.contact.mgmt.dummy;

import java.util.List;
import java.util.Set;

import com.contact.mgmt.bos.AddContact;

public class ContactFilter {
	
	private Set<AddContact> contact1;
	
	

	public Set<AddContact> getContact1() {
		return contact1;
	}


	public void setContact1(Set<AddContact> contact1) {
		this.contact1 = contact1;
	}


	private List<AddContact> contact;
	
	public List<AddContact> getContact() {
		return contact;
	}
	
	
	public void setContact(List<AddContact> contact) {
		this.contact = contact;
	}



	
}
