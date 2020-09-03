package com.connectgas.app.model.order;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PoPaymentInfo {

	private BigDecimal amount;

	private String paymentType; // CARD / ONLINE / CHEQUE/ CREDIT

	private String transactionReference;

	private LocalDateTime transactionDateTime;

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
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

	public LocalDateTime getTransactionDateTime() {
		return transactionDateTime;
	}

	public void setTransactionDateTime(LocalDateTime transactionDateTime) {
		this.transactionDateTime = transactionDateTime;
	}

}
