package com.connectgas.app.model.order.dto;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import com.connectgas.app.model.order.Order;
import com.connectgas.app.model.order.OrderProduct;
import com.connectgas.app.model.order.PurchaseOrder;

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

	public OrderLedger(Order order) {
		Double totalAmount = 0.0;
		String referenceSummary = "";
		if (order.getPaymentInfo() != null) {
			this.setBillAmount(order.getPaymentInfo().getBillAmount());

			if (order.getPaymentInfo().getPaidDetails() != null) {

				totalAmount = order.getPaymentInfo().getPaidDetails().stream().mapToDouble(pd -> pd.getAmount())
						.reduce(0, Double::sum);
				referenceSummary = order.getPaymentInfo().getPaidDetails().stream().map(pr -> pr.getReference())
						.collect(Collectors.joining("|"));
			}
		}
		this.setDeliveredProducts(order.getOrderedProducts());
		this.setReturnedProducts(order.getReturnProducts());
		this.setOrderCreatedAt(order.getCreatedAt());

		this.setPaymentReference(referenceSummary);
		this.setPaidAmount(totalAmount);
		this.setOrderId(order.getId());

	}

	public OrderLedger(PurchaseOrder order) {
		Double totalAmount = 0.0;
		String referenceSummary = "";
		if (order.getPaymentInfo() != null) {
			this.setBillAmount(order.getPaymentInfo().getBillAmount());

			if (order.getPaymentInfo().getPaidDetails() != null) {

				totalAmount = order.getPaymentInfo().getPaidDetails().stream().mapToDouble(pd -> pd.getAmount())
						.reduce(0, Double::sum);
				referenceSummary = order.getPaymentInfo().getPaidDetails().stream().map(pr -> pr.getReference())
						.collect(Collectors.joining("|"));
			}
		}
		this.setDeliveredProducts(order.getOrderedProducts());
		this.setReturnedProducts(order.getReturnProducts());
		this.setOrderCreatedAt(order.getCreatedAt());

		this.setPaymentReference(referenceSummary);
		this.setPaidAmount(totalAmount);
		this.setOrderId(order.getId());
	}

	public OrderLedger() {
		// Default Constructor
	}

}
