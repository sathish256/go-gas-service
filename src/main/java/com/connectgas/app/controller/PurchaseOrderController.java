package com.connectgas.app.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.connectgas.app.model.order.PurchaseOrder;
import com.connectgas.app.model.order.dto.OrderLedger;
import com.connectgas.app.service.PurchaseOrderService;

@RestController
@RequestMapping("/v1/purchase-order")
public class PurchaseOrderController {

	@Autowired
	private PurchaseOrderService purchaseOrderService;

	@GetMapping("/{id}")
	public ResponseEntity<PurchaseOrder> getOrder(@PathVariable String id) {
		return ResponseEntity.ok(purchaseOrderService.getOrder(id));
	}

	@PostMapping("/new")
	public ResponseEntity<PurchaseOrder> directOrder(@RequestBody PurchaseOrder order,
			@RequestHeader("modifiedBy") String modifiedBy) {
		return ResponseEntity.ok(purchaseOrderService.directOrder(order, modifiedBy));
	}

	@PutMapping("/{orderId}")
	public ResponseEntity<PurchaseOrder> udpateOrder(@RequestBody PurchaseOrder order,
			@RequestHeader("modifiedBy") String modifiedBy) {
		return ResponseEntity.ok(purchaseOrderService.udpateOrder(order, modifiedBy));
	}

	@PutMapping("/{orderId}/change-status/{status}")
	public ResponseEntity<PurchaseOrder> updateOrderStatus(@PathVariable String orderId, @PathVariable String status,
			@RequestHeader("modifiedBy") String modifiedBy) {
		return ResponseEntity.ok(purchaseOrderService.updateOrderStatus(orderId, status, modifiedBy));
	}

	@GetMapping("/myorders")
	public List<PurchaseOrder> findOrdersByLoggedInUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String phone = auth.getName().substring(0, auth.getName().indexOf('|'));
		return purchaseOrderService.getOrders(phone);
	}

	@GetMapping("/search")
	public List<PurchaseOrder> search(@RequestParam("candfId") String candfId) {
		return purchaseOrderService.search(candfId);
	}

	@GetMapping("/ledger/{candfId}/{dealerId}")
	public List<OrderLedger> getOrderLedgerByDealer(@PathVariable String candfId, @PathVariable String dealerId,
			@RequestParam(value = "from", required = false) String from,
			@RequestParam(value = "to", required = false) String to) {

		LocalDateTime fromDate = Optional.ofNullable(from).map(s -> s.concat("T00:00:01"))
				.map(s -> LocalDateTime.parse(s)).orElse(LocalDateTime.now().minusDays(30));

		LocalDateTime toDate = Optional.ofNullable(to).map(s -> s.concat("T23:59:59")).map(s -> LocalDateTime.parse(s))
				.orElse(LocalDateTime.now());

		return purchaseOrderService.getOrderLedgerByDealer(candfId, dealerId, fromDate, toDate);

	}
}
