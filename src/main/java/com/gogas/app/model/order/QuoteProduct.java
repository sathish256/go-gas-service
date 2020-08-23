package com.gogas.app.model.order;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "quoteproduct")
public class QuoteProduct {

	@Id
	@Column(name = "productId", nullable = false)
	private String productId;

	@Column(name = "minQty")
	private Integer minQty;

	@Column(name = "maxQty")
	private Integer maxQty;

	@Column(name = "quotePrice")
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

}
