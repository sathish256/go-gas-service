package com.connectgas.app.model.inventory;

import java.util.List;

import com.connectgas.app.model.common.ConnectGasEntity;

public class CandFDispatch extends ConnectGasEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5513982296093797008L;

	private String dealerId;

	private String candfId;

	private List<StockProduct> products;

	private DispatchStatus dispatchStatus;

	public String getDealerId() {
		return dealerId;
	}

	public void setDealerId(String dealerId) {
		this.dealerId = dealerId;
	}

	public String getCandfId() {
		return candfId;
	}

	public void setCandfId(String candfId) {
		this.candfId = candfId;
	}

	public List<StockProduct> getProducts() {
		return products;
	}

	public void setProducts(List<StockProduct> products) {
		this.products = products;
	}

	public DispatchStatus getDispatchStatus() {
		return dispatchStatus;
	}

	public void setDispatchStatus(DispatchStatus dispatchStatus) {
		this.dispatchStatus = dispatchStatus;
	}

}
