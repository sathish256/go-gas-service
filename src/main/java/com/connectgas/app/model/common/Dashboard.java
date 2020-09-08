package com.connectgas.app.model.common;

import java.io.Serializable;
import java.util.Map;

public class Dashboard implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -968263020046623199L;

	private Map<String, Integer> orderSummary;
	private Map<String, Double> accountSummary;

	public Map<String, Integer> getOrderSummary() {
		return orderSummary;
	}

	public void setOrderSummary(Map<String, Integer> orderSummary) {
		this.orderSummary = orderSummary;
	}

	public Map<String, Double> getAccountSummary() {
		return accountSummary;
	}

	public void setAccountSummary(Map<String, Double> accountSummary) {
		this.accountSummary = accountSummary;
	}

}
