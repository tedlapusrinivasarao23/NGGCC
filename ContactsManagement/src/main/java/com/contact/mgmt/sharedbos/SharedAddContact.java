package com.contact.mgmt.sharedbos;

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

import org.hibernate.annotations.CollectionId;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class SharedAddContact implements Cloneable{

	@Id
	@GenericGenerator(name = "hib_increment", strategy = "increment")
	@GeneratedValue(generator = "hib_increment")
	private int id;

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
	
	

	@ElementCollection(fetch=FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	@JoinTable(name="SharedContact_Address",joinColumns=@JoinColumn(name="SharedContact_ID"))
	@GenericGenerator(name="hilo-gen",strategy="hilo")
	@CollectionId(columns={@Column(name="Address_ID")},generator="hilo-gen",type=@Type(type="int"))
	private Collection<SharedAddress> address = new ArrayList<SharedAddress>();

	@ElementCollection(fetch=FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	@JoinTable(name="SharedContact_PhoneNum",joinColumns=@JoinColumn(name="SharedContact_ID"))
	@GenericGenerator(name="hilo-gen",strategy="hilo")
	@CollectionId(columns={@Column(name="Major_PhoneNum")},generator="hilo-gen",type=@Type(type="int"))
	private Collection<SharedAddContactPhoneNumbers> addContactPhoneNumbers = new ArrayList<SharedAddContactPhoneNumbers>();


	@ElementCollection(fetch=FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	@JoinTable(name="SharedContact_Emails",joinColumns=@JoinColumn(name="SharedContact_ID"))
	@GenericGenerator(name="hilo-gen",strategy="hilo")
	@CollectionId(columns={@Column(name="Major_emailId")},generator="hilo-gen",type=@Type(type="int"))
	private Collection<SharedAddContactEmails> addContactEmails = new ArrayList<SharedAddContactEmails>();


	@ElementCollection(fetch=FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	@JoinTable(name="SharedContact_Events",joinColumns=@JoinColumn(name="SharedContact_ID"))
	@GenericGenerator(name="hilo-gen",strategy="hilo")
	@CollectionId(columns={@Column(name="Major_events")},generator="hilo-gen",type=@Type(type="int"))
	private Collection<SharedAddContactEvents> addContactEvents = new ArrayList<SharedAddContactEvents>();
	
	
	@ElementCollection(fetch=FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	@JoinTable(name="SharedContact_Urls",joinColumns=@JoinColumn(name="SharedContact_ID"))
	@GenericGenerator(name="hilo-gen",strategy="hilo")
	@CollectionId(columns={@Column(name="Major_urls")},generator="hilo-gen",type=@Type(type="int"))
	private Collection<SharedAddContactUrls> addContactUrls = new ArrayList<SharedAddContactUrls>();
	
	
	@ElementCollection(fetch=FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	@JoinTable(name="SharedContact_Texts",joinColumns=@JoinColumn(name="SharedContact_ID"))
	@GenericGenerator(name="hilo-gen",strategy="hilo")
	@CollectionId(columns={@Column(name="Major_texts")},generator="hilo-gen",type=@Type(type="int"))
	private Collection<SharedAddContactTexts> addContactTexts = new ArrayList<SharedAddContactTexts>();


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
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


	public Collection<SharedAddress> getAddress() {
		return address;
	}


	public void setAddress(Collection<SharedAddress> address) {
		this.address = address;
	}


	public Collection<SharedAddContactPhoneNumbers> getAddContactPhoneNumbers() {
		return addContactPhoneNumbers;
	}


	public void setAddContactPhoneNumbers(Collection<SharedAddContactPhoneNumbers> addContactPhoneNumbers) {
		this.addContactPhoneNumbers = addContactPhoneNumbers;
	}


	public Collection<SharedAddContactEmails> getAddContactEmails() {
		return addContactEmails;
	}


	public void setAddContactEmails(Collection<SharedAddContactEmails> addContactEmails) {
		this.addContactEmails = addContactEmails;
	}


	public Collection<SharedAddContactEvents> getAddContactEvents() {
		return addContactEvents;
	}


	public void setAddContactEvents(Collection<SharedAddContactEvents> addContactEvents) {
		this.addContactEvents = addContactEvents;
	}


	public Collection<SharedAddContactUrls> getAddContactUrls() {
		return addContactUrls;
	}


	public void setAddContactUrls(Collection<SharedAddContactUrls> addContactUrls) {
		this.addContactUrls = addContactUrls;
	}


	public Collection<SharedAddContactTexts> getAddContactTexts() {
		return addContactTexts;
	}


	public void setAddContactTexts(Collection<SharedAddContactTexts> addContactTexts) {
		this.addContactTexts = addContactTexts;
	}


	@Override
	public Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return (SharedAddContact)super.clone();
	}
	
	


}
