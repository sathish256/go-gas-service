package com.connectgas.app.model.product;

import java.io.Serializable;
import java.math.BigDecimal;

public class DealerPrice implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 141250380383410245L;
	private String dealerId;
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

}
