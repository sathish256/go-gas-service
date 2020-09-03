package com.connectgas.app.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.connectgas.app.model.order.PurchaseOrder;
import com.connectgas.app.model.order.dto.OrderDTO;

@Service
public class PorderService {

	public PurchaseOrder getOrder(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	public PurchaseOrder generateOrderbyQuote(String quoteid) {
		// TODO Auto-generated method stub
		return null;
	}

	public PurchaseOrder directOrder(OrderDTO order) {
		// TODO Auto-generated method stub
		return null;
	}

	public PurchaseOrder updateOrderStatus(Long orderId, String status) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<PurchaseOrder> getOrders(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<PurchaseOrder> search(String dealerId) {
		// TODO Auto-generated method stub
		return null;
	}

}
