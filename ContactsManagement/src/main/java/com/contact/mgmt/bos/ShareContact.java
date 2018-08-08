package com.contact.mgmt.bos;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "sharecontact_trans")
public class ShareContact {
	@Id
	@GenericGenerator(name="kaugen" , strategy="increment") 
	@GeneratedValue(generator="kaugen")
	@Column(name="id")
	private int shareContactId;
	
	private String sharedOwnerId;
	private String borroweId;
	private String borrowerEmail;
	
	private int sharedCntct;
	@Type(type="org.hibernate.type.NumericBooleanType")
	@Column
	private boolean isActive=true;
	
	
	
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	public int getShareContactId() {
		return shareContactId;
	}
	public void setShareContactId(int shareContactId) {
		this.shareContactId = shareContactId;
	}
	
	public String getBorroweId() {
		return borroweId;
	}
	public void setBorroweId(String borroweId) {
		this.borroweId = borroweId;
	}
	public int getSharedCntct() {
		return sharedCntct;
	}
	public void setSharedCntct(int sharedCntct) {
		this.sharedCntct = sharedCntct;
	}
	
	public String getSharedOwnerId() {
		return sharedOwnerId;
	}
	public void setSharedOwnerId(String sharedOwnerId) {
		this.sharedOwnerId = sharedOwnerId;
	}
	public String getBorrowerEmail() {
		return borrowerEmail;
	}
	public void setBorrowerEmail(String borrowerEmail) {
		this.borrowerEmail = borrowerEmail;
	}
	
	
	

}
