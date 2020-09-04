package com.connectgas.app.model;

import java.util.List;

import com.connectgas.app.model.common.Address;
import com.connectgas.app.model.common.ConnectGasEntity;

public class Dealership extends ConnectGasEntity {

	private String id;
	private String name;
	private String phone;
	private Address address;
	private String ownerName;
	private String ownerPhone;
	private String candfId;
	private List<DealerAllocation> dealerAllocation;

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

	public List<DealerAllocation> getDealerAllocation() {
		return dealerAllocation;
	}

	public void setDealerAllocation(List<DealerAllocation> dealerAllocation) {
		this.dealerAllocation = dealerAllocation;
	}

	public String getCandfId() {
		return candfId;
	}

	public void setCandfId(String candfId) {
		this.candfId = candfId;
	}

}
