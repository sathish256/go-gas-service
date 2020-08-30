package com.connectgas.app.model.order;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "orderProduct")
public class OrderProduct {

	@Id
	@GeneratedValue(generator = "op-uuid")
	@GenericGenerator(name = "op-uuid", strategy = "uuid2")
	@Column(name = "id", unique = true)
	private String id;

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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
