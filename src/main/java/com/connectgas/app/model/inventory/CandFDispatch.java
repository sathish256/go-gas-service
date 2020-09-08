package com.connectgas.app.model.inventory;

import java.util.Map;

import com.connectgas.app.model.common.ConnectGasEntity;

public class CandFDispatch extends ConnectGasEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5513982296093797008L;

	private String dealerId;

	private String candfId;

	private Map<String, Integer> products;

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

	public DispatchStatus getDispatchStatus() {
		return dispatchStatus;
	}

	public void setDispatchStatus(DispatchStatus dispatchStatus) {
		this.dispatchStatus = dispatchStatus;
	}

	public Map<String, Integer> getProducts() {
		return products;
	}

	public void setProducts(Map<String, Integer> products) {
		this.products = products;
	}

}
