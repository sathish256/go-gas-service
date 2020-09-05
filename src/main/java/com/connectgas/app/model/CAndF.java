package com.connectgas.app.model;

import com.connectgas.app.model.common.Address;
import com.connectgas.app.model.common.ConnectGasEntity;

public class CAndF extends ConnectGasEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2336187197788379024L;
	private String name;
	private String phone;
	private Address address;
	private String ownerName;
	private String ownerPhone;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getOwnerPhone() {
		return ownerPhone;
	}

	public void setOwnerPhone(String ownerPhone) {
		this.ownerPhone = ownerPhone;
	}

}
