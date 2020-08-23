package com.gogas.app.model.customer;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gogas.app.model.common.Address;
import com.gogas.app.model.common.GoGasEntity;
import com.gogas.app.model.common.IdentityProof;

@Entity
@Table(name = "customer")
public class Customer extends GoGasEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7178441752343638821L;

	@Id
	@GeneratedValue(generator = "customer-uuid")
	@GenericGenerator(name = "customer-uuid", strategy = "uuid2")
	@Column(name = "id", unique = true)
	private String id;

	@Column(name = "name")
	private String name;

	@Column(name = "type")
	private CustomerType type;

	@Column(name = "phone", unique = true, nullable = false)
	private Long phone;

	@OneToOne
	@JoinColumn(name = "addressId")
	private Address address;

	@OneToOne
	@JoinColumn(name = "identityProofId")
	private IdentityProof identityProof;

	@OneToOne
	@JoinColumn(name = "primaryContact")
	private PrimaryContact primaryContact;

	@JsonIgnore
	@Column(name = "password", nullable = false)
	private String password;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public CustomerType getType() {
		return type;
	}

	public void setType(CustomerType type) {
		this.type = type;
	}

	public Long getPhone() {
		return phone;
	}

	public void setPhone(Long phone) {
		this.phone = phone;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public IdentityProof getIdentityProof() {
		return identityProof;
	}

	public void setIdentityProof(IdentityProof identityProof) {
		this.identityProof = identityProof;
	}

	public PrimaryContact getPrimaryContact() {
		return primaryContact;
	}

	public void setPrimaryContact(PrimaryContact primaryContact) {
		this.primaryContact = primaryContact;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}