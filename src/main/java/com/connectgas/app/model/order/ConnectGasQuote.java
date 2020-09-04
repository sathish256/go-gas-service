package com.connectgas.app.model.order;

import java.io.Serializable;
import java.util.List;

import com.connectgas.app.model.common.ConnectGasEntity;

public class ConnectGasQuote extends ConnectGasEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7541870822535152218L;
	private String customerId;
	private String dealerId;
	private List<QuoteProduct> quoteProducts;
	private QuoteStatus quoteStatus;

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getDealerId() {
		return dealerId;
	}

	public void setDealerId(String dealerId) {
		this.dealerId = dealerId;
	}

	public List<QuoteProduct> getQuoteProducts() {
		return quoteProducts;
	}

	public void setQuoteProducts(List<QuoteProduct> quoteProducts) {
		this.quoteProducts = quoteProducts;
	}

	public QuoteStatus getQuoteStatus() {
		return quoteStatus;
	}

	public void setQuoteStatus(QuoteStatus quoteStatus) {
		this.quoteStatus = quoteStatus;
	}

	
}