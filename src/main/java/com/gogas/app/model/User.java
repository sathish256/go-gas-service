package com.gogas.app.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name = "gogasuser")
public class User extends GoGasEntity implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7178441752343638821L;
	@Id
	@Column(name = "uid", unique = true)
	private String uid;
	@Column(name = "firstname")
	private String firstName;
	@Column(name = "lastname")
	private String lastName;
	@Column(name = "contact", unique = true)
	private Long contact;
	@Column(name = "govtidproof")
	private String govtidproof;
	@Column(name = "emergencycontact")
	private String emergencycontact;
	@Column(name = "role")
	private UserRole role;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "address_id", referencedColumnName = "id")
	private Address address;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
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

	public Long getContact() {
		return contact;
	}

	public void setContact(Long contact) {
		this.contact = contact;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getGovtidproof() {
		return govtidproof;
	}

	public void setGovtidproof(String govtidproof) {
		this.govtidproof = govtidproof;
	}

	public String getEmergencycontact() {
		return emergencycontact;
	}

	public void setEmergencycontact(String emergencycontact) {
		this.emergencycontact = emergencycontact;
	}

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}

}