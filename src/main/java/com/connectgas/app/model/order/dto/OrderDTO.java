package com.connectgas.app.model.order.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import com.connectgas.app.model.common.State;
import com.connectgas.app.model.order.OrderProduct;
import com.connectgas.app.model.order.OrderStatus;
import com.connectgas.app.model.order.PaymentInfo;

public class OrderDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8116467442580638226L;

	private Long id;

	private OrderCustomer customer;

	private String dealerId;

	private String quoteId;

	private OrderType orderType;

	private String deliveryPersonId;

	private OrderStatus orderStatus;

	private List<PaymentInfo> paymentInfo;

	private LocalDateTime deliveredTimestamp;

	private List<OrderProduct> orderedProducts;

	private List<OrderProduct> returnProducts;

	private LocalDateTime createdAt;

	private String createdBy;

	private LocalDateTime lastmodifiedAt;

	private String lastmodifiedBy;

	private State status;

	private LocalDateTime scheduledAt;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public OrderCustomer getCustomer() {
		return customer;
	}

	public void setCustomer(OrderCustomer customer) {
		this.customer = customer;
	}

	public String getDealerId() {
		return dealerId;
	}

	public void setDealerId(String dealerId) {
		this.dealerId = dealerId;
	}

	public String getQuoteId() {
		return quoteId;
	}

	public void setQuoteId(String quoteId) {
		this.quoteId = quoteId;
	}

	public OrderType getOrderType() {
		return orderType;
	}

	public void setOrderType(OrderType orderType) {
		this.orderType = orderType;
	}

	public String getDeliveryPersonId() {
		return deliveryPersonId;
	}

	public void setDeliveryPersonId(String deliveryPersonId) {
		this.deliveryPersonId = deliveryPersonId;
	}

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	public List<PaymentInfo> getPaymentInfo() {
		return paymentInfo;
	}

	public void setPaymentInfo(List<PaymentInfo> paymentInfo) {
		this.paymentInfo = paymentInfo;
	}

	public LocalDateTime getDeliveredTimestamp() {
		return deliveredTimestamp;
	}

	public void setDeliveredTimestamp(LocalDateTime deliveredTimestamp) {
		this.deliveredTimestamp = deliveredTimestamp;
	}

	public List<OrderProduct> getOrderedProducts() {
		return orderedProducts;
	}

	public void setOrderedProducts(List<OrderProduct> orderedProducts) {
		this.orderedProducts = orderedProducts;
	}

	public List<OrderProduct> getReturnProducts() {
		return returnProducts;
	}

	public void setReturnProducts(List<OrderProduct> returnProducts) {
		this.returnProducts = returnProducts;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public LocalDateTime getLastmodifiedAt() {
		return lastmodifiedAt;
	}

	public void setLastmodifiedAt(LocalDateTime lastmodifiedAt) {
		this.lastmodifiedAt = lastmodifiedAt;
	}

	public String getLastmodifiedBy() {
		return lastmodifiedBy;
	}

	public void setLastmodifiedBy(String lastmodifiedBy) {
		this.lastmodifiedBy = lastmodifiedBy;
	}

	public State getStatus() {
		return status;
	}

	public void setStatus(State status) {
		this.status = status;
	}

	public LocalDateTime getScheduledAt() {
		return scheduledAt;
	}

	public void setScheduledAt(LocalDateTime scheduledAt) {
		this.scheduledAt = scheduledAt;
	}

}