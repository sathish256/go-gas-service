package com.connectgas.app.model.product;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "dealerprice")
public class DealerPrice {

	@Id
	@GeneratedValue(generator = "dsp-uuid")
	@GenericGenerator(name = "dsp-uuid", strategy = "uuid2")
	@Column(name = "id", unique = true)
	private String id;

	@Column(name = "dealerId", nullable = false)
	private String dealerId;

	@Column(name = "price")
	private BigDecimal price;

	public String getDealerId() {
		return dealerId;
	}

	public void setDealerId(String dealerId) {
		this.dealerId = dealerId;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
