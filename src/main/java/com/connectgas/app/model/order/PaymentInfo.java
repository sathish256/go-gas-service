package com.connectgas.app.model.order;

import java.io.Serializable;
import java.util.List;

public class PaymentInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -164205329727066976L;
	private Double arrearAmount;
	private Double billAmount;
	private List<PaidDetails> paidDetails;
	private PaymentStatus paymentStatus;

	public Double getBillAmount() {
		return billAmount;
	}

	public void setBillAmount(Double billAmount) {
		this.billAmount = billAmount;
	}

	public Double getArrearAmount() {
		return arrearAmount;
	}

	public void setArrearAmount(Double arrearAmount) {
		this.arrearAmount = arrearAmount;
	}


	public List<PaidDetails> getPaidDetails() {
		return paidDetails;
	}

	public void setPaidDetails(List<PaidDetails> paidDetails) {
		this.paidDetails = paidDetails;
	}

	public PaymentStatus getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(PaymentStatus paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

}
