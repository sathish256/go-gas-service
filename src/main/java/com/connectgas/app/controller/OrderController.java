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

import com.connectgas.app.model.order.Order;
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

	@PutMapping("/{status}")
	public ResponseEntity<OrderDTO> updateOrderStatus(@PathVariable String status) {
		return ResponseEntity.ok(orderService.updateOrderStatus(status));
	}

	@GetMapping("/myorders")
	public List<OrderDTO> findOrdersByLoggedInUser(@PathVariable String dealerid) {
		return orderService.getOrders(dealerid);
	}

	@GetMapping("/search")
	public List<Order> search(@RequestParam("dealerid") String dealerid) {
		return orderService.search(dealerid);
	}

	@PostMapping("/assign/{orderid}/{userid}")
	public ResponseEntity<OrderDTO> assignDeliveryPerson(@PathVariable String orderid, @PathVariable String userid) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return ResponseEntity.ok(orderService.assignDeliveryPerson(auth.getName(), orderid, userid));
	}

	@GetMapping("/sampleorder")
	public ResponseEntity<OrderDTO> sampleOrder() {
		return ResponseEntity.ok(orderService.sampleOrder());
	}

}
