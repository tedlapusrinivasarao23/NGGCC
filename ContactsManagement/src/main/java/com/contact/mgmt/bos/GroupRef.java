package com.contact.mgmt.bos;

import java.io.File;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name ="group_ref") 
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class GroupRef {

	@Id
	@GenericGenerator(name="kaugen" , strategy="increment")
	@GeneratedValue(generator="kaugen")
	@Column(name="group_id")
	private int groupId;
	@Column(name = "image", unique = false, length = 100000)
	private byte[] image;
	private String groupName;
	private String ownerNumber;
	@Type(type="org.hibernate.type.NumericBooleanType")
	@Column
	private boolean isActive=true;
	
	
	@Transient
	private int count;
	
	@Transient
	private List<AddContact> groupContactsList;
	
	@Transient
	private File file;
	
	
	
	
	
	public List<AddContact> getGroupContactsList() {
		return groupContactsList;
	}
	public void setGroupContactsList(List<AddContact> groupContactsList) {
		this.groupContactsList = groupContactsList;
	}
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
	
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	

	
	public byte[] getImage() {
		return image;
	}
	public void setImage(byte[] image) {
		this.image = image;
	}
	public String getOwnerNumber() {
		return ownerNumber;
	}
	public void setOwnerNumber(String ownerNumber) {
		this.ownerNumber = ownerNumber;
	}
	public int getGroupId() {
		return groupId;
	}
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	


}
