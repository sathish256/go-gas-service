package com.connectgas.app.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.connectgas.app.model.order.PurchaseOrder;
import com.connectgas.app.repository.SimpleFirestoreRepository;

@Service
public class PurchaseOrderService {

	@Autowired
	private SimpleFirestoreRepository<PurchaseOrder, String> purchaseOrderRepository;

	public PurchaseOrder getOrder(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	public PurchaseOrder generateOrderbyQuote(String quoteid) {
		// TODO Auto-generated method stub
		return null;
	}

	public PurchaseOrder directOrder(PurchaseOrder order) {
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
		purchaseOrder.setLastmodifiedAt(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
		purchaseOrder.setCreatedAt(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));

		return purchaseOrderRepository.save(purchaseOrder, getCollectionName());
	}

	private Class<PurchaseOrder> getCollectionName() {
		return PurchaseOrder.class;
	}

	public PurchaseOrder getPorder(String orderId) {
		return purchaseOrderRepository.fetchById(orderId, getCollectionName())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
						"CAndF id " + orderId + " does not exists in the system"));
	}

	public String deletePorder(String orderId) {
		String status = "successfully deleted!!!";
		purchaseOrderRepository.deleteById(orderId, getCollectionName());
		return status;
	}

}
