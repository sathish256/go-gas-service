package com.connectgas.app.model.customer;

import java.io.Serializable;

import com.connectgas.app.model.common.Address;

public class Organization implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3549273289404735482L;

	private String orgName;

	private String orgPhone;

	private String pcName;

	private String pcPhone;

	private String pcDesignation;

	private Address orgAddress;

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
