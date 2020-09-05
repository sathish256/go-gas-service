package com.connectgas.app.model.product;

import java.util.List;

import com.connectgas.app.model.common.ConnectGasEntity;

public class Product extends ConnectGasEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7541870822535152218L;
	private String name;
	private String type;
	private String description;
	private String specification;

	private List<CAndFPrice> candfPrice;

	private List<DealerPrice> dealerPrice;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSpecification() {
		return specification;
	}

	public List<CAndFPrice> getCandfPrice() {
		return candfPrice;
	}

	public void setCandfPrice(List<CAndFPrice> candfPrice) {
		this.candfPrice = candfPrice;
	}

	public List<DealerPrice> getDealerPrice() {
		return dealerPrice;
	}

	public void setDealerPrice(List<DealerPrice> dealerPrice) {
		this.dealerPrice = dealerPrice;
	}

	public void setSpecification(String specification) {
		this.specification = specification;
	}

}