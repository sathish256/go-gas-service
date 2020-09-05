package com.connectgas.app.model.customer;

import com.connectgas.app.model.common.Address;
import com.connectgas.app.model.common.ConnectGasEntity;
import com.connectgas.app.model.common.IdentityProof;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class Customer extends ConnectGasEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7178441752343638821L;

	private String name;

	private CustomerType type;

	private String phone;

	private Address address;

	private IdentityProof identityProof;

	private IdentityProof addressProofId;

	private Organization organization;

	private String dealerId;

	@JsonIgnore
	private String password;

	private String profileImage;
	
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