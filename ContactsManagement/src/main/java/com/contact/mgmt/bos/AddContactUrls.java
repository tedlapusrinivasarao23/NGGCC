package com.contact.mgmt.bos;

import javax.persistence.Embeddable;

@Embeddable
public class AddContactUrls {

	private String url;
	private String utltype;
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUtltype() {
		return utltype;
	}
	public void setUtltype(String utltype) {
		this.utltype = utltype;
	}
	
	
	
}
