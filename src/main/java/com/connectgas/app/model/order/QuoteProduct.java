package com.connectgas.app.model.order;

import java.io.Serializable;
import java.math.BigDecimal;

public class QuoteProduct implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5447306926506385382L;
	private String productId;
	private Integer minQty;
	private Integer maxQty;
	private Integer quantity;
	private BigDecimal quotePrice;

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

	public BigDecimal getQuotePrice() {
		return quotePrice;
	}

	public void setQuotePrice(BigDecimal quotePrice) {
		this.quotePrice = quotePrice;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

}
