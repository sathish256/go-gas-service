package com.connectgas.app.model.order;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class PurchaseOrder implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8799719695774680513L;

	private String id; // cabdfId + dealerid + ddmmyy+hhmm

	private String candfId;

	private String dealerId;

	private PurchaseOrderStatus purchaseOrderStatus;

	private List<OrderProduct> orderedProducts;

	private List<OrderProduct> returnProducts;

	private BigDecimal poAmount;

	private List<PoPaymentInfo> paymentInfo;

	private String createdAt;

	private String createdBy;

	private String lastmodifiedAt;

	private String lastmodifiedBy;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCandfId() {
		return candfId;
	}

	public void setCandfId(String candfId) {
		this.candfId = candfId;
	}

	public String getDealerId() {
		return dealerId;
	}

	public void setDealerId(String dealerId) {
		this.dealerId = dealerId;
	}

	public PurchaseOrderStatus getPurchaseOrderStatus() {
		return purchaseOrderStatus;
	}

	public void setPurchaseOrderStatus(PurchaseOrderStatus purchaseOrderStatus) {
		this.purchaseOrderStatus = purchaseOrderStatus;
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

	public BigDecimal getPoAmount() {
		return poAmount;
	}

	public void setPoAmount(BigDecimal poAmount) {
		this.poAmount = poAmount;
	}

	public List<PoPaymentInfo> getPaymentInfo() {
		return paymentInfo;
	}

	public void setPaymentInfo(List<PoPaymentInfo> paymentInfo) {
		this.paymentInfo = paymentInfo;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getLastmodifiedAt() {
		return lastmodifiedAt;
	}

	public void setLastmodifiedAt(String lastmodifiedAt) {
		this.lastmodifiedAt = lastmodifiedAt;
	}

	public String getLastmodifiedBy() {
		return lastmodifiedBy;
	}

	public void setLastmodifiedBy(String lastmodifiedBy) {
		this.lastmodifiedBy = lastmodifiedBy;
	}

}