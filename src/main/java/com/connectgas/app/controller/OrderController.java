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

import com.connectgas.app.model.order.dto.OrderDTO;
import com.connectgas.app.service.OrderService;

@RestController
@RequestMapping("/v1/order")
public class OrderController {

	@Autowired
	private OrderService orderService;

	@GetMapping("/{id}")
	public ResponseEntity<OrderDTO> getOrder(@PathVariable String id) {
		return ResponseEntity.ok(orderService.getOrder(id));
	}

	@PostMapping("/{quoteid}")
	public ResponseEntity<OrderDTO> generateOrderbyQuote(@PathVariable String quoteid) {
		return ResponseEntity.ok(orderService.generateOrderbyQuote(quoteid));
	}

	@PostMapping("/new")
	public ResponseEntity<OrderDTO> directOrder(@RequestBody OrderDTO order) {
		return ResponseEntity.ok(orderService.directOrder(order));
	}

	@PutMapping("/{orderId}/change-status/{status}")
	public ResponseEntity<OrderDTO> updateOrderStatus(@PathVariable Long orderId, @PathVariable String status) {
		return ResponseEntity.ok(orderService.updateOrderStatus(orderId, status));
	}

	@GetMapping("/myorders")
	public List<OrderDTO> findOrdersByLoggedInUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return orderService.getOrders(auth.getName());
	}

	@GetMapping("/search")
	public List<OrderDTO> search(@RequestParam("dealerId") String dealerId) {
		return orderService.search(dealerId);
	}

	@PutMapping("/{orderId}/assign/{userId}")
	public ResponseEntity<OrderDTO> assignDeliveryPerson(@PathVariable Long orderId, @PathVariable String userId) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return ResponseEntity.ok(orderService.assignDeliveryPerson(auth.getName(), orderId, userId));
	}

}
