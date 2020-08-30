package com.connectgas.app.model.customer;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.connectgas.app.model.common.Address;

@Entity
@Table(name = "organization")
public class Organization {

	@Id
	@GeneratedValue(generator = "org-uuid")
	@GenericGenerator(name = "org-uuid", strategy = "uuid2")
	@Column(name = "id", unique = true)
	private String id;
	@Column(name = "orgName")
	private String orgName;

	@Column(name = "orgPhone")
	private String orgPhone;

	@Column(name = "pcName")
	private String pcName;

	@Column(name = "pcPhone")
	private String pcPhone;

	@Column(name = "pcDesignation")
	private String pcDesignation;

	@OneToOne
	@JoinColumn(name = "addressId")
	private Address orgAddress;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getOrgPhone() {
		return orgPhone;
	}

	public void setOrgPhone(String orgPhone) {
		this.orgPhone = orgPhone;
	}

	public String getPcName() {
		return pcName;
	}

	public void setPcName(String pcName) {
		this.pcName = pcName;
	}

	public String getPcPhone() {
		return pcPhone;
	}

	public void setPcPhone(String pcPhone) {
		this.pcPhone = pcPhone;
	}

	public String getPcDesignation() {
		return pcDesignation;
	}

	public void setPcDesignation(String pcDesignation) {
		this.pcDesignation = pcDesignation;
	}

	public Address getOrgAddress() {
		return orgAddress;
	}

	public void setOrgAddress(Address orgAddress) {
		this.orgAddress = orgAddress;
	}

}
