package com.connectgas.app.model.user;

import java.io.Serializable;

import com.connectgas.app.model.common.Address;
import com.connectgas.app.model.common.ConnectGasEntity;
import com.connectgas.app.model.common.IdentityProof;

public class User extends ConnectGasEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7178441752343638821L;
	private String firstName;
	private String lastName;
	private String phone;
	private UserRole role;
	private String profileImage;
	private Address address;
	private IdentityProof identityProof;
	private String password;
	private String lastLoginTimestamp;
	private String candfId;
	private String dealershipId;
	private boolean hasFullAccess;
	private String allowedPages;

	public String getAllowedPages() {
		return allowedPages;
	}

	public void setAllowedPages(String allowedPages) {
		this.allowedPages = allowedPages;
	}

	public boolean isHasFullAccess() {
		return hasFullAccess;
	}

	public void setHasFullAccess(boolean hasFullAccess) {
		this.hasFullAccess = hasFullAccess;
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}

	public String getProfileImage() {
		return profileImage;
	}

	public void setProfileImage(String profileImage) {
		this.profileImage = profileImage;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLastLoginTimestamp() {
		return lastLoginTimestamp;
	}

	public void setLastLoginTimestamp(String lastLoginTimestamp) {
		this.lastLoginTimestamp = lastLoginTimestamp;
	}

	public String getCandfId() {
		return candfId;
	}

	public void setCandfId(String candfId) {
		this.candfId = candfId;
	}

	public String getDealershipId() {
		return dealershipId;
	}

	public void setDealershipId(String dealershipId) {
		this.dealershipId = dealershipId;
	}

}