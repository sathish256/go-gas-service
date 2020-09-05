package com.connectgas.app.model;

import java.io.Serializable;

public class DealerAllocation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6608322129804398404L;
	private String productId;
	private Integer quantity;

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
}
