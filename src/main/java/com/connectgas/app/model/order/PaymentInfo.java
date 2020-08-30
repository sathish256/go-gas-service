package com.connectgas.app.model.order;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "paymentInfo")
public class PaymentInfo {

	@Id
	@GeneratedValue(generator = "pi-uuid")
	@GenericGenerator(name = "pi-uuid", strategy = "uuid2")
	@Column(name = "id", unique = true)
	private String id;

	@Column(name = "billAmount")
	private BigDecimal billAmount;
	@Column(name = "paidAmount")
	private BigDecimal paidAmount;
	@Column(name = "partialPay")
	private boolean partialPay;
	@Column(name = "arrearAmount")
	private BigDecimal arrearAmount;
	@Column(name = "paymentType")
	private String paymentType;
	@Column(name = "paymentStatus")
	private String paymentStatus;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

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
