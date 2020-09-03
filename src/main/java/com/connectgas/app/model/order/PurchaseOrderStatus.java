package com.connectgas.app.model.order;

public enum PurchaseOrderStatus {

	INITIATED,

	QUOTE_REQUEST, QUOTE_UPDATED, QUOTE_ACCEPTED,

	REQUEST_PAYMENT_INFO, PAYMENT_INFO_UPDATED, PAYMENT_REVIEW, CANDF_REJECT,

	ORDER_PROCESSED,

	// Future states
	DISPATCHED, IN_TRANSIT, DELIVERED,

	ARCHIVED;

}

// Dealer will initiate PO
// Send to CAndF for Review
// CANDF will send to delar with Quote
// Dealer will accept/cancel the quote and update payment details and send back
// cand will approve the request and either will be sent for order processing or dealer-rework.