package com.connectgas.app.model.order.dto;

import java.io.Serializable;
import java.util.List;

import com.connectgas.app.model.order.OrderProduct;

public class OrderLedger implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8543840424673311851L;
	private String orderCreatedAt;
	private String orderId;
	private List<OrderProduct> deliveredProducts;
	private List<OrderProduct> returnedProducts;
	private Double billAmount;
	private Double paidAmount;
	private String paymentReference;

	public String getOrderCreatedAt() {
		return orderCreatedAt;
	}

	public void setOrderCreatedAt(String orderCreatedAt) {
		this.orderCreatedAt = orderCreatedAt;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public List<OrderProduct> getDeliveredProducts() {
		return deliveredProducts;
	}

	public void setDeliveredProducts(List<OrderProduct> deliveredProducts) {
		this.deliveredProducts = deliveredProducts;
	}

	public List<OrderProduct> getReturnedProducts() {
		return returnedProducts;
	}

	public void setReturnedProducts(List<OrderProduct> returnedProducts) {
		this.returnedProducts = returnedProducts;
	}

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

	public String getPaymentReference() {
		return paymentReference;
	}

	public void setPaymentReference(String paymentReference) {
		this.paymentReference = paymentReference;
	}

}
