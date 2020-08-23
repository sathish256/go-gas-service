package com.gogas.app.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.gogas.app.model.common.Address;
import com.gogas.app.model.common.GoGasEntity;

@Entity
@Table(name = "dealership")
public class Dealership extends GoGasEntity {

	@Id
	@GeneratedValue(generator = "dealership-uuid")
	@GenericGenerator(name = "dealership-uuid", strategy = "uuid2")
	@Column(name = "id", unique = true)
	private String id;

	@Column(name = "name")
	private String name;

	@Column(name = "phone")
	private String phone;

	@OneToOne
	@JoinColumn(name = "addressId")
	private Address address;

	@Column(name = "ownerName")
	private String ownerName;

	@Column(name = "ownerPhone")
	private String ownerPhone;

	@Column(name = "candfId")
	private String candfId;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
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
