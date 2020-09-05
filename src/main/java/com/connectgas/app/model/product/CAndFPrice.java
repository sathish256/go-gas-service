package com.connectgas.app.model.product;

import java.io.Serializable;

public class CAndFPrice implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6714145480641094900L;

	private String candfId;

	private Double price;

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getCandfId() {
		return candfId;
	}

	public void setCandfId(String candfId) {
		this.candfId = candfId;
	}

}
