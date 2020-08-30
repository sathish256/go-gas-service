package com.connectgas.app.model.customer;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.connectgas.app.model.common.Address;
import com.connectgas.app.model.common.GoGasEntity;
import com.connectgas.app.model.common.IdentityProof;
import com.fasterxml.jackson.annotation.JsonIgnore;

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
	private String phone;

	@OneToOne
	@JoinColumn(name = "addressId")
	private Address address;

	@OneToOne
	@JoinColumn(name = "identityProofId")
	private IdentityProof identityProof;

	@OneToOne
	@JoinColumn(name = "addressProofId")
	private IdentityProof addressProofId;

	@OneToOne
	@JoinColumn(name = "orgInfo")
	private Organization organization;

	@Column(name = "dealerId")
	private String dealerId;

	@JsonIgnore
	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "profileImage")
	private String profileImage;

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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
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

	public IdentityProof getAddressProofId() {
		return addressProofId;
	}

	public void setAddressProofId(IdentityProof addressProofId) {
		this.addressProofId = addressProofId;
	}

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	public String getDealerId() {
		return dealerId;
	}

	public void setDealerId(String dealerId) {
		this.dealerId = dealerId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getProfileImage() {
		return profileImage;
	}

	public void setProfileImage(String profileImage) {
		this.profileImage = profileImage;
	}

}