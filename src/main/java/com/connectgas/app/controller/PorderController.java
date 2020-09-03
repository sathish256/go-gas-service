package com.connectgas.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.connectgas.app.model.order.PurchaseOrder;
import com.connectgas.app.model.order.dto.OrderDTO;
import com.connectgas.app.service.PorderService;

@RestController
@RequestMapping("/v1/purchase-order")
public class PorderController {

	@Autowired
	private PorderService porderService;

	@GetMapping("/{id}")
	public ResponseEntity<PurchaseOrder> getOrder(@PathVariable String id) {
		return ResponseEntity.ok(porderService.getOrder(id));
	}

	@PostMapping("/{quoteid}")
	public ResponseEntity<PurchaseOrder> generateOrderbyQuote(@PathVariable String quoteid) {
		return ResponseEntity.ok(porderService.generateOrderbyQuote(quoteid));
	}

	@PostMapping("/new")
	public ResponseEntity<PurchaseOrder> directOrder(@RequestBody OrderDTO order) {
		return ResponseEntity.ok(porderService.directOrder(order));
	}

	@PutMapping("/{orderId}/change-status/{status}")
	public ResponseEntity<PurchaseOrder> updateOrderStatus(@PathVariable Long orderId, @PathVariable String status) {
		return ResponseEntity.ok(porderService.updateOrderStatus(orderId, status));
	}

	@GetMapping("/myorders")
	public List<PurchaseOrder> findOrdersByLoggedInUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return porderService.getOrders(auth.getName());
	}

	@GetMapping("/search")
	public List<PurchaseOrder> search(@RequestParam("dealerId") String dealerId) {
		return porderService.search(dealerId);
	}

}
