package com.connectgas.app.model.inventory;

import java.io.Serializable;

public class StockProduct implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
