package com.connectgas.app.model.order;

import java.time.LocalDateTime;
import java.util.List;

import com.connectgas.app.model.common.ConnectGasEntity;
import com.connectgas.app.model.order.dto.OrderCustomer;
import com.connectgas.app.model.order.dto.OrderType;

public class Order extends ConnectGasEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8799719695774680513L;

	private OrderCustomer customer;
	private String dealerId;
	private String quoteId;
	private OrderType orderType;
	private String deliveryPersonId;
	private OrderStatus orderStatus;
	private List<PaymentInfo> paymentInfo;
	private String deliveredTimestamp;
	private List<OrderProduct> orderedProducts;
	private List<OrderProduct> returnProducts;
	private LocalDateTime scheduledAt;

	public LocalDateTime getScheduledAt() {
		return scheduledAt;
	}

	public void setScheduledAt(LocalDateTime scheduledAt) {
		this.scheduledAt = scheduledAt;
	}

	public OrderType getOrderType() {
		return orderType;
	}

	public void setOrderType(OrderType orderType) {
		this.orderType = orderType;
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

	public String getDeliveredTimestamp() {
		return deliveredTimestamp;
	}

	public void setDeliveredTimestamp(String deliveredTimestamp) {
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

	public OrderCustomer getCustomer() {
		return customer;
	}

	public void setCustomer(OrderCustomer customer) {
		this.customer = customer;
	}
}