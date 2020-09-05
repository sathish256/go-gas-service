package com.connectgas.app.model.order;

import java.io.Serializable;

public class PaidDetails implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7043184365159285704L;
	private String paymentType;
	private Double amount;
	private String paidOn;

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getPaidOn() {
		return paidOn;
	}

	public void setPaidOn(String paidOn) {
		this.paidOn = paidOn;
	}

}
