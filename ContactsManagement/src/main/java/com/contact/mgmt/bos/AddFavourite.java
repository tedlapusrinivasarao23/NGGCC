package com.contact.mgmt.bos;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
/**
 * @author SRINIVAS SRI
 *
 */
@Entity
@Table(name = "favourite")
public class AddFavourite {
	
	@Id
	@GenericGenerator(name="kaugen" , strategy="increment") 
	@GeneratedValue(generator="kaugen")
	@Column(name="id")
	private int favId;
	private String ownerNumber;
	private int sharedFavourite;
	
	@Type(type="org.hibernate.type.NumericBooleanType")
	@Column
	private boolean isActive=true;
	
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	
	public int getFavId() {
		return favId;
	}
	public void setFavId(int favId) {
		this.favId = favId;
	}
	public String getOwnerNumber() {
		return ownerNumber;
	}
	public void setOwnerNumber(String ownerNumber) {
		this.ownerNumber = ownerNumber;
	}
	public int getSharedFavourite() {
		return sharedFavourite;
	}
	public void setSharedFavourite(int sharedFavourite) {
		this.sharedFavourite = sharedFavourite;
	}
	
	

}
