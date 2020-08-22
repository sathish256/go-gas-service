package com.gogas.app.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "gogasuser")
public class User extends GoGasEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7178441752343638821L;
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "id", unique = true)
	private String id;
	@Column(name = "firstname")
	private String firstName;
	@Column(name = "lastname")
	private String lastName;

	@Column(name = "phone", unique = true)
	private Long phone;

	@Column(name = "role")
	private UserRole role;

	@Column(name = "profileImage")
	private String profileImage;

	@OneToOne
    @JoinColumn(name="addressId")
	private Address address;

	@OneToOne
    @JoinColumn(name="identityProofId")
	private IdentityProof identityProof;

	@JsonIgnore
	@Column(name = "password")
	private String password;

	@Column(name = "lastLoginTimestamp")
	private LocalDateTime lastLoginTimestamp;

	@Column(name = "candfId", nullable = false)
	private String candfId;

	@Column(name = "dealershipId", nullable = false)
	private String dealershipId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public Long getPhone() {
		return phone;
	}

	public void setPhone(Long phone) {
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

	public LocalDateTime getLastLoginTimestamp() {
		return lastLoginTimestamp;
	}

	public void setLastLoginTimestamp(LocalDateTime lastLoginTimestamp) {
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