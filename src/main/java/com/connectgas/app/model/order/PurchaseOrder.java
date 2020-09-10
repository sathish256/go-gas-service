package com.connectgas.app.model.order;

import java.util.List;
import java.util.stream.Collectors;

import com.connectgas.app.model.common.ConnectGasEntity;
import com.connectgas.app.model.order.dto.OrderLedger;

public class PurchaseOrder extends ConnectGasEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8799719695774680513L;

	private String candfId;

	private String dealerId;

	private PurchaseOrderStatus purchaseOrderStatus;

	private List<OrderProduct> orderedProducts;

	private List<OrderProduct> returnProducts;

	private PaymentInfo paymentInfo;

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

	public PaymentInfo getPaymentInfo() {
		return paymentInfo;
	}

	public void setPaymentInfo(PaymentInfo paymentInfo) {
		this.paymentInfo = paymentInfo;
	}

	public OrderLedger getOrderLedger() {

		OrderLedger ledger = new OrderLedger();
		ledger.setBillAmount(this.paymentInfo.getBillAmount());
		ledger.setDeliveredProducts(this.orderedProducts);
		ledger.setReturnedProducts(this.returnProducts);
		ledger.setOrderCreatedAt(this.getCreatedAt());
		Double totalAmount = this.paymentInfo.getPaidDetails().stream().mapToDouble(pd -> pd.getAmount()).reduce(0,
				Double::sum);
		String paymentReference = this.paymentInfo.getPaidDetails().stream().map(pr -> pr.getReference())
				.collect(Collectors.joining("|"));
		ledger.setPaymentReference(paymentReference);
		ledger.setPaidAmount(totalAmount);
		ledger.setOrderId(this.getId());

		return ledger;
	}

}