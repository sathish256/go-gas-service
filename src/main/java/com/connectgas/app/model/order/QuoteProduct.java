package com.connectgas.app.model.order;

import java.io.Serializable;

public class QuoteProduct implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5447306926506385382L;
	private String productId;
	private Integer minQty;
	private Integer maxQty;
	private Integer quantity;
	private Double quotePrice;

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public Integer getMinQty() {
		return minQty;
	}

	public void setMinQty(Integer minQty) {
		this.minQty = minQty;
	}

	public Integer getMaxQty() {
		return maxQty;
	}

	public void setMaxQty(Integer maxQty) {
		this.maxQty = maxQty;
	}

	public Double getQuotePrice() {
		return quotePrice;
	}

	public void setQuotePrice(Double quotePrice) {
		this.quotePrice = quotePrice;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

}
