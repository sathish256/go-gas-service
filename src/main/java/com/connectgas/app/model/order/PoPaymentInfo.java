package com.connectgas.app.model.order;

import java.io.Serializable;

public class PoPaymentInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1261520107117083210L;

	private Double amount;

	private String paymentType; // CARD / ONLINE / CHEQUE/ CREDIT

	private String transactionReference;

	private String transactionDateTime;

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getTransactionReference() {
		return transactionReference;
	}

	public void setTransactionReference(String transactionReference) {
		this.transactionReference = transactionReference;
	}

	public String getTransactionDateTime() {
		return transactionDateTime;
	}

	public void setTransactionDateTime(String transactionDateTime) {
		this.transactionDateTime = transactionDateTime;
	}

}
