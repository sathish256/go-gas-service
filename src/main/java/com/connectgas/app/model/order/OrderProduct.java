package com.connectgas.app.model.order;

import java.math.BigDecimal;

public class OrderProduct {

	private String id;
	private String productId;
	private Integer quantity;
	private BigDecimal orderedPrice;

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

	public BigDecimal getOrderedPrice() {
		return orderedPrice;
	}

	public void setOrderedPrice(BigDecimal orderedPrice) {
		this.orderedPrice = orderedPrice;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
