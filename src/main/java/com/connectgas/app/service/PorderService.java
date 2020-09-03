package com.connectgas.app.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.connectgas.app.fb.service.PurchaseOrderFbService;
import com.connectgas.app.model.order.PurchaseOrder;
import com.connectgas.app.model.order.dto.OrderDTO;

@Service
public class PorderService {

	@Autowired
	private PurchaseOrderFbService purchaseOrderFbService;

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

	public PurchaseOrder directPurchaseOrder() {

		PurchaseOrder purchaseOrder = new PurchaseOrder();

		purchaseOrder.setId(UUID.randomUUID().toString());
		purchaseOrder.setDealerId("asdf");
		purchaseOrder.setLastmodifiedAt(LocalDateTime.now().toString());
		purchaseOrder.setCreatedAt(LocalDateTime.now().toString());

		return purchaseOrderFbService.save(purchaseOrder);
	}

	public PurchaseOrder getPorder(String orderId) {
		try {
			return purchaseOrderFbService.fetchById(orderId);
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public String deletePorder(String orderId) {
		String status = "successfully deleted!!!";

		try {
			purchaseOrderFbService.deleteById(orderId);
		} catch (InterruptedException | ExecutionException e) {
			status = "Error while deleting!!!";

			e.printStackTrace();
		}
		return status;
	}

}
