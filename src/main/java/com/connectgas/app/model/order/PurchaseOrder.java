package com.connectgas.app.model.order;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.connectgas.app.model.common.ConnectGasEntity;

public class PurchaseOrder extends ConnectGasEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8799719695774680513L;

	private String candfId;

	private String dealerId;

	private PurchaseOrderStatus purchaseOrderStatus;

	private List<OrderProduct> orderedProducts;

	private List<OrderProduct> returnProducts;

	private BigDecimal poAmount;

	private List<PoPaymentInfo> paymentInfo;

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

}