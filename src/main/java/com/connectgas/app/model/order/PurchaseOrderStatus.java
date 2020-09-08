package com.connectgas.app.model.order;

public enum PurchaseOrderStatus {

	INITIATED("Dealer"),

	QUOTE_REQUEST("Candf"), QUOTE_UPDATED("Dealer"), QUOTE_ACCEPTED("Dealer"),

	REQUEST_PAYMENT_INFO("Dealer"), PAYMENT_INFO_UPDATED("Candf"), PAYMENT_REVIEW("Candf"), CANDF_REJECT("Dealer"),

	ORDER_PROCESSED("Dealer"),

	// Future states
	DISPATCHED("Dealer"), IN_TRANSIT("Dealer"), DELIVERED("Dealer"),

	ARCHIVED("Candf");

	public final String role;

	private PurchaseOrderStatus(String role) {
		this.role = role;
	}

	public String getRole() {
		return role;
	}
}

// Dealer will initiate PO
// Send to CAndF for Review
// CANDF will send to delar with Quote
// Dealer will accept/cancel the quote and update payment details and send back
// cand will approve the request and either will be sent for order processing or dealer-rework.