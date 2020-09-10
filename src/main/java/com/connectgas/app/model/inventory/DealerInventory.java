package com.connectgas.app.model.inventory;

import java.util.HashSet;
import java.util.Map;

import com.connectgas.app.model.common.ConnectGasEntity;

public class DealerInventory extends ConnectGasEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1814805152714164637L;

	private Map<String, Integer> availableStock;

	private Map<String, Integer> inTransitStock;

	private HashSet<CustomerHolding> customerHoldings;

	private Map<String, Integer> emptyStock;

	public Map<String, Integer> getAvailableStock() {
		return availableStock;
	}

	public void setAvailableStock(Map<String, Integer> availableStock) {
		this.availableStock = availableStock;
	}

	public Map<String, Integer> getInTransitStock() {
		return inTransitStock;
	}

	public void setInTransitStock(Map<String, Integer> inTransitStock) {
		this.inTransitStock = inTransitStock;
	}

	public HashSet<CustomerHolding> getCustomerHoldings() {
		return customerHoldings;
	}

	public void setCustomerHoldings(HashSet<CustomerHolding> customerHoldings) {
		this.customerHoldings = customerHoldings;
	}

	public Map<String, Integer> getEmptyStock() {
		return emptyStock;
	}

	public void setEmptyStock(Map<String, Integer> emptyStock) {
		this.emptyStock = emptyStock;
	}

}