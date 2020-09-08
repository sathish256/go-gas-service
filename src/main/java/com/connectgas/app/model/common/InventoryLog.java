package com.connectgas.app.model.common;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class InventoryLog implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4196287064172212481L;

	private String orderId;

	private String updatedBy; // CustomerOrder or PurchaseOrder;

	private String message;

	private String createdTimeStamp;

	public InventoryLog() {
		// DefaultConstructor
	}

	public InventoryLog(String orderId, String message, String updatedBy) {
		this.orderId = orderId;
		this.message = message;
		this.createdTimeStamp = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
		this.updatedBy = updatedBy;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getCreatedTimeStamp() {
		return createdTimeStamp;
	}

	public void setCreatedTimeStamp(String createdTimeStamp) {
		this.createdTimeStamp = createdTimeStamp;
	}

}
