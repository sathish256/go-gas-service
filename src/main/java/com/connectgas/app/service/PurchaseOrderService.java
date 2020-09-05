package com.connectgas.app.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.connectgas.app.model.order.PurchaseOrder;
import com.connectgas.app.model.order.PurchaseOrderStatus;
import com.connectgas.app.model.user.User;
import com.connectgas.app.model.user.UserRole;
import com.connectgas.app.repository.SimpleFirestoreRepository;

@Service
public class PurchaseOrderService {

	@Autowired
	private SimpleFirestoreRepository<PurchaseOrder, String> purchaseOrderRepository;

	@Autowired
	private SimpleFirestoreRepository<User, String> userRepository;

	public PurchaseOrder getOrder(String id) {
		return purchaseOrderRepository.fetchById(id, PurchaseOrder.class)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
						"Order id " + id + " not available in the system"));
	}

	private PurchaseOrder saveOrUpdateOrder(PurchaseOrder order, String loggedInUser) {
		return purchaseOrderRepository.save(order, PurchaseOrder.class);

	}

	public PurchaseOrder directOrder(PurchaseOrder order) {
		String loggedInUser = SecurityContextHolder.getContext().getAuthentication().getName();
		order.setId("PO" + Instant.now().getEpochSecond());
		order.setCreatedAt(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
		order.setLastmodifiedAt(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
		return saveOrUpdateOrder(order, loggedInUser);
	}

	public PurchaseOrder updateOrderStatus(String orderId, String status) {

		String loggedInUser = SecurityContextHolder.getContext().getAuthentication().getName();

		PurchaseOrder dbOrder = purchaseOrderRepository.fetchById(orderId, PurchaseOrder.class)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
						"Error while updating order id " + orderId + " not available in the system"));
		dbOrder.setLastmodifiedAt(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
		dbOrder.setLastmodifiedBy(loggedInUser);
		dbOrder.setPurchaseOrderStatus(PurchaseOrderStatus.valueOf(status));

		return purchaseOrderRepository.save(dbOrder, PurchaseOrder.class);
	}

	public List<PurchaseOrder> getOrders(String phone) {

		User user = userRepository.findAll(User.class).stream().filter(u -> u.getPhone().equals(phone)).findFirst()
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
						"User id " + phone + " does not exists in the system"));

		if (user.getRole().equals(UserRole.CANDF))
			return purchaseOrderRepository.findAll(PurchaseOrder.class).stream()
					.filter(o -> o.getCandfId().equals(user.getCandfId())).collect(Collectors.toList());

		if (user.getRole().equals(UserRole.DEALER))
			return purchaseOrderRepository.findAll(PurchaseOrder.class).stream()
					.filter(o -> o.getDealerId().equals(user.getDealershipId())).collect(Collectors.toList());

		return null;
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

	public List<PurchaseOrder> search(String candfId) {
		return purchaseOrderRepository.findAll(PurchaseOrder.class).stream().filter(o -> o.getCandfId().equals(candfId))
				.collect(Collectors.toList());
	}

}
