package com.connectgas.app.model.order;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.connectgas.app.model.common.GoGasEntity;
import com.connectgas.app.model.customer.CustomerType;

@Entity
@Table(name = "cg_order")
public class Order extends GoGasEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8799719695774680513L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_seq")
	@SequenceGenerator(name = "order_seq", sequenceName = "order_seq", initialValue = 1000, allocationSize = 1)
	@Column(name = "id", unique = true)
	private Long id;

	@Column(name = "customerId")
	private String customerId;

	@Column(name = "orderType")
	private CustomerType orderType;

	@Column(name = "dealerId")
	private String dealerId;

	@Column(name = "quoteId")
	private String quoteId;

	@Column(name = "orderedBy")
	private String orderedBy;

	@Column(name = "deliveryPersonId")
	private String deliveryPersonId;

	@Column(name = "orderStatus")
	private String orderStatus;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	private List<PaymentInfo> paymentInfo;

	@Column(name = "deliveredTimestamp")
	private LocalDateTime deliveredTimestamp;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	private List<OrderProduct> orderedProducts;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	private List<OrderProduct> returnProducts;

	@Column(name = "scheduledAt")
	private LocalDateTime scheduledAt;

	public LocalDateTime getScheduledAt() {
		return scheduledAt;
	}

	public void setScheduledAt(LocalDateTime scheduledAt) {
		this.scheduledAt = scheduledAt;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public CustomerType getOrderType() {
		return orderType;
	}

	public void setOrderType(CustomerType orderType) {
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

	public String getOrderedBy() {
		return orderedBy;
	}

	public void setOrderedBy(String orderedBy) {
		this.orderedBy = orderedBy;
	}

	public String getDeliveryPersonId() {
		return deliveryPersonId;
	}

	public void setDeliveryPersonId(String deliveryPersonId) {
		this.deliveryPersonId = deliveryPersonId;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
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
}