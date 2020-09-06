package com.connectgas.app.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.connectgas.app.model.order.Order;
import com.connectgas.app.model.order.OrderProduct;

@Service
public class DealerInventoryProcessor {

	public void processNewOrder(Order order) {

		// move from Available inventory to in-transit

	}

	public void processDelivery(Order dbOrder) {

		// move from in-transit to customer holdings

		udpateEmptyStock(dbOrder.getReturnProducts());

	}

	private void udpateEmptyStock(List<OrderProduct> returnProducts) {
		// Update empty stocks for the dealer

	}

}
