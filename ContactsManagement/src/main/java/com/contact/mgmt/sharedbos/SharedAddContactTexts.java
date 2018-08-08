package com.contact.mgmt.sharedbos;

import javax.persistence.Embeddable;

@Embeddable
public class SharedAddContactTexts {
	
	private String text;
	private String textType;
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getTextType() {
		return textType;
	}
	public void setTextType(String textType) {
		this.textType = textType;
	}
	
}
