package com.contact.mgmt.sharedbos;

import javax.persistence.Embeddable;

@Embeddable
public class SharedAddContactEvents {
	
	private String event;
	private String eventDate;
	public String getEvent() {
		return event;
	}
	public void setEvent(String event) {
		this.event = event;
	}
	public String getEventDate() {
		return eventDate;
	}
	public void setEventDate(String eventDate) {
		this.eventDate = eventDate;
	}
	

}
