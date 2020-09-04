package com.connectgas.app.model.order;

import java.math.BigDecimal;

public class PaymentInfo {
	private BigDecimal billAmount;
	private BigDecimal paidAmount;
	private boolean partialPay;
	private BigDecimal arrearAmount;
	private String paymentType;
	private String paymentStatus;


	public BigDecimal getBillAmount() {
		return billAmount;
	}

	public void setBillAmount(BigDecimal billAmount) {
		this.billAmount = billAmount;
	}

	public BigDecimal getPaidAmount() {
		return paidAmount;
	}

	public void setPaidAmount(BigDecimal paidAmount) {
		this.paidAmount = paidAmount;
	}

	public boolean isPartialPay() {
		return partialPay;
	}

	public void setPartialPay(boolean partialPay) {
		this.partialPay = partialPay;
	}

	public BigDecimal getArrearAmount() {
		return arrearAmount;
	}

	public void setArrearAmount(BigDecimal arrearAmount) {
		this.arrearAmount = arrearAmount;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

}
