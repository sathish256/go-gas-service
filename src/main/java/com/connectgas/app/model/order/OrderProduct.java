package com.connectgas.app.model.order;

import java.io.Serializable;

public class OrderProduct implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4562297863054986443L;
	private String productId;
	private Integer quantity;
	private Double orderedPrice;

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

	public Double getOrderedPrice() {
		return orderedPrice;
	}

	public void setOrderedPrice(Double orderedPrice) {
		this.orderedPrice = orderedPrice;
	}

}
