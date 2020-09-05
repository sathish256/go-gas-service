package com.connectgas.app.model.product;

import java.io.Serializable;

public class DealerPrice implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 141250380383410245L;
	private String dealerId;
	private Double price;

	public String getDealerId() {
		return dealerId;
	}

	public void setDealerId(String dealerId) {
		this.dealerId = dealerId;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

}
