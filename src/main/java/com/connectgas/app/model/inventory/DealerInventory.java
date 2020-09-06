package com.connectgas.app.model.inventory;

import com.connectgas.app.model.common.ConnectGasEntity;

public class DealerInventory extends ConnectGasEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1814805152714164637L;

	private String dealerId;

	private Stock availableStock;

	private Stock inTransitStock;

	private Stock customerHoldings;

	private Stock emptyStock;

	public String getDealerId() {
		return dealerId;
	}

	public void setDealerId(String dealerId) {
		this.dealerId = dealerId;
	}

	public Stock getAvailableStock() {
		return availableStock;
	}

	public void setAvailableStock(Stock availableStock) {
		this.availableStock = availableStock;
	}

	public Stock getInTransitStock() {
		return inTransitStock;
	}

	public void setInTransitStock(Stock inTransitStock) {
		this.inTransitStock = inTransitStock;
	}

	public Stock getCustomerHoldings() {
		return customerHoldings;
	}

	public void setCustomerHoldings(Stock customerHoldings) {
		this.customerHoldings = customerHoldings;
	}

	public Stock getEmptyStock() {
		return emptyStock;
	}

	public void setEmptyStock(Stock emptyStock) {
		this.emptyStock = emptyStock;
	}

}
