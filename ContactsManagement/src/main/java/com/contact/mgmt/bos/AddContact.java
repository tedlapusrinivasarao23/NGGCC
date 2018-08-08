package com.contact.mgmt.bos;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.CollectionId;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table
@JsonIgnoreProperties
public class AddContact implements Cloneable{

	@Id
	@GenericGenerator(name = "hib_increment", strategy = "increment")
	@GeneratedValue(generator = "hib_increment")
	private int id;
	
	private String contact_id;
	
	@Column(nullable=false)
	private String ownerNumber;
	
	@Column(name = "image", unique = false, length = 100000)
	private byte[] image;
	private String prefix;
	@Column(nullable = false)
	private String firstName;
	private String lastName;
	private String designation;
	private String department;
	private String company;
	private String groupName;
	private double km;
	
	@Transient
	private File file;
	
	
	 @Cascade({CascadeType.SAVE_UPDATE, CascadeType.DELETE})
	@ElementCollection(fetch=FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	@JoinTable(name="Contact_Address",joinColumns=@JoinColumn(name="Contact_ID"))
	@GenericGenerator(name="hilo-gen",strategy="hilo")
	@CollectionId(columns={@Column(name="Address_ID")},generator="hilo-gen",type=@Type(type="int"))
	private Collection<Address> address = new ArrayList<Address>();

	 @Cascade({CascadeType.SAVE_UPDATE, CascadeType.DELETE})
	@ElementCollection(fetch=FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	@JoinTable(name="Contact_PhoneNum",joinColumns=@JoinColumn(name="Contact_ID"))
	@GenericGenerator(name="hilo-gen",strategy="hilo")
	@CollectionId(columns={@Column(name="Major_PhoneNum")},generator="hilo-gen",type=@Type(type="int"))
	private Collection<AddContactPhoneNumbers> addContactPhoneNumbers = new ArrayList<AddContactPhoneNumbers>();

	 @Cascade({CascadeType.SAVE_UPDATE, CascadeType.DELETE})
	@ElementCollection(fetch=FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	@JoinTable(name="Contact_Emails",joinColumns=@JoinColumn(name="Contact_ID"))
	@GenericGenerator(name="hilo-gen",strategy="hilo")
	@CollectionId(columns={@Column(name="Major_emailId")},generator="hilo-gen",type=@Type(type="int"))
	private Collection<AddContactEmails> addContactEmails = new ArrayList<AddContactEmails>();


	 @Cascade({CascadeType.SAVE_UPDATE, CascadeType.DELETE})
	@ElementCollection(fetch=FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	@JoinTable(name="Contact_Events",joinColumns=@JoinColumn(name="Contact_ID"))
	@GenericGenerator(name="hilo-gen",strategy="hilo")
	@CollectionId(columns={@Column(name="Major_events")},generator="hilo-gen",type=@Type(type="int"))
	private Collection<AddContactEvents> addContactEvents = new ArrayList<AddContactEvents>();
	
	 @Cascade({CascadeType.SAVE_UPDATE, CascadeType.DELETE})
	@ElementCollection(fetch=FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	@JoinTable(name="Contact_Urls",joinColumns=@JoinColumn(name="Contact_ID"))
	@GenericGenerator(name="hilo-gen",strategy="hilo")
	@CollectionId(columns={@Column(name="Major_urls")},generator="hilo-gen",type=@Type(type="int"))
	private Collection<AddContactUrls> addContactUrls = new ArrayList<AddContactUrls>();
	
	@Cascade({CascadeType.SAVE_UPDATE, CascadeType.DELETE})
	@ElementCollection(fetch=FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	@JoinTable(name="Contact_Texts",joinColumns=@JoinColumn(name="Contact_ID"))
	@GenericGenerator(name="hilo-gen",strategy="hilo")
	@CollectionId(columns={@Column(name="Major_texts")},generator="hilo-gen",type=@Type(type="int"))
	private Collection<AddContactTexts> addContactTexts = new ArrayList<AddContactTexts>();


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}

	
	public String getContact_id() {
		return contact_id;
	}


	public void setContact_id(String contact_id) {
		this.contact_id = contact_id;
	}


	public String getOwnerNumber() {
		return ownerNumber;
	}


	public void setOwnerNumber(String ownerNumber) {
		this.ownerNumber = ownerNumber;
	}



	public byte[] getImage() {
		return image;
	}


	public void setImage(byte[] image) {
		this.image = image;
	}


	public String getPrefix() {
		return prefix;
	}


	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public String getDesignation() {
		return designation;
	}


	public void setDesignation(String designation) {
		this.designation = designation;
	}


	public String getDepartment() {
		return department;
	}


	public void setDepartment(String department) {
		this.department = department;
	}


	public String getCompany() {
		return company;
	}


	public void setCompany(String company) {
		this.company = company;
	}


	public String getGroupName() {
		return groupName;
	}


	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}


	public Collection<Address> getAddress() {
		return address;
	}


	public void setAddress(Collection<Address> address) {
		this.address = address;
	}


	public Collection<AddContactPhoneNumbers> getAddContactPhoneNumbers() {
		return addContactPhoneNumbers;
	}


	public void setAddContactPhoneNumbers(Collection<AddContactPhoneNumbers> addContactPhoneNumbers) {
		this.addContactPhoneNumbers = addContactPhoneNumbers;
	}


	public Collection<AddContactEmails> getAddContactEmails() {
		return addContactEmails;
	}


	public void setAddContactEmails(Collection<AddContactEmails> addContactEmails) {
		this.addContactEmails = addContactEmails;
	}


	public Collection<AddContactEvents> getAddContactEvents() {
		return addContactEvents;
	}


	public void setAddContactEvents(Collection<AddContactEvents> addContactEvents) {
		this.addContactEvents = addContactEvents;
	}


	public Collection<AddContactUrls> getAddContactUrls() {
		return addContactUrls;
	}


	public void setAddContactUrls(Collection<AddContactUrls> addContactUrls) {
		this.addContactUrls = addContactUrls;
	}


	public Collection<AddContactTexts> getAddContactTexts() {
		return addContactTexts;
	}


	public void setAddContactTexts(Collection<AddContactTexts> addContactTexts) {
		this.addContactTexts = addContactTexts;
	}


	public double getKm() {
		return km;
	}


	public void setKm(double km) {
		this.km = km;
	}


	public File getFile() {
		return file;
	}


	public void setFile(File file) {
		this.file = file;
	}


	@Override
	public Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return (AddContact)super.clone();
	}
	
	


}
