package com.connectgas.app.model.inventory;

import java.io.Serializable;
import java.util.Map;

public class CustomerHolding implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4930262736387111912L;

	private String customerId;

	private String customerName;

	private Map<String, Integer> products;

	public CustomerHolding() {

		// Default Constructor to support serialisation
	}

	public CustomerHolding(String id, String customerName, Map<String, Integer> products) {
		this.customerId = id;
		this.customerName = customerName;
		this.products = products;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public Map<String, Integer> getProducts() {
		return products;
	}

	public void setProducts(Map<String, Integer> products) {
		this.products = products;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((customerId == null) ? 0 : customerId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CustomerHolding other = (CustomerHolding) obj;
		if (customerId == null) {
			if (other.customerId != null)
				return false;
		} else if (!customerId.equals(other.customerId))
			return false;
		return true;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

}
