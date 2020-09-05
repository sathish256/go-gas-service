package com.connectgas.app.model.order;

import java.io.Serializable;

public class PaymentInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -164205329727066976L;
	private Double billAmount;
	private Double paidAmount;
	private boolean partialPay;
	private Double arrearAmount;
	private String paymentType;
	private String paymentStatus;


	public Double getBillAmount() {
		return billAmount;
	}

	public void setBillAmount(Double billAmount) {
		this.billAmount = billAmount;
	}

	public Double getPaidAmount() {
		return paidAmount;
	}

	public void setPaidAmount(Double paidAmount) {
		this.paidAmount = paidAmount;
	}

	public boolean isPartialPay() {
		return partialPay;
	}

	public void setPartialPay(boolean partialPay) {
		this.partialPay = partialPay;
	}

	public Double getArrearAmount() {
		return arrearAmount;
	}

	public void setArrearAmount(Double arrearAmount) {
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
