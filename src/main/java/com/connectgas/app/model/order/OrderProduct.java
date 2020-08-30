package com.connectgas.app.model.order;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "orderProduct")
public class OrderProduct {

	@Id
	@Column(name = "productId", nullable = false)
	private String productId;

	@Column(name = "quantity")
	private Integer quantity;

	@Column(name = "orderedPrice")
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

}
