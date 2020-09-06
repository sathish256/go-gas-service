package com.connectgas.app.model.inventory;

import java.io.Serializable;
import java.util.List;

public class Stock implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private StockIdentifierType stockIdentifierType;

	private String stockIdentifierValue;

	private List<StockProduct> products;

	public StockIdentifierType getStockIdentifierType() {
		return stockIdentifierType;
	}

	public void setStockIdentifierType(StockIdentifierType stockIdentifierType) {
		this.stockIdentifierType = stockIdentifierType;
	}

	public String getStockIdentifierValue() {
		return stockIdentifierValue;
	}

	public void setStockIdentifierValue(String stockIdentifierValue) {
		this.stockIdentifierValue = stockIdentifierValue;
	}

	public List<StockProduct> getProducts() {
		return products;
	}

	public void setProducts(List<StockProduct> products) {
		this.products = products;
	}
	
	

}
