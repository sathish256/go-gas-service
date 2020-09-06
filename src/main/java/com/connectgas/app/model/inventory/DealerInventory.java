package com.connectgas.app.model.inventory;

import java.util.List;

import com.connectgas.app.model.common.ConnectGasEntity;

public class DealerInventory extends ConnectGasEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1814805152714164637L;

	private String dealerId;

	private List<StockProduct> availableStock;

	private List<StockProduct> inTransitStock;

	private List<StockProduct> customerHoldings;

	private List<StockProduct> emptyStock;

	public String getDealerId() {
		return dealerId;
	}

	public void setDealerId(String dealerId) {
		this.dealerId = dealerId;
	}

	public List<StockProduct> getAvailableStock() {
		return availableStock;
	}

	public void setAvailableStock(List<StockProduct> availableStock) {
		this.availableStock = availableStock;
	}

	public List<StockProduct> getInTransitStock() {
		return inTransitStock;
	}

	public void setInTransitStock(List<StockProduct> inTransitStock) {
		this.inTransitStock = inTransitStock;
	}

	public List<StockProduct> getCustomerHoldings() {
		return customerHoldings;
	}

	public void setCustomerHoldings(List<StockProduct> customerHoldings) {
		this.customerHoldings = customerHoldings;
	}

	public List<StockProduct> getEmptyStock() {
		return emptyStock;
	}

	public void setEmptyStock(List<StockProduct> emptyStock) {
		this.emptyStock = emptyStock;
	}

}