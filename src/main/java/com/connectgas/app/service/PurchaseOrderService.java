package com.connectgas.app.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ResponseStatusException;

import com.connectgas.app.model.common.State;
import com.connectgas.app.model.order.PaidDetails;
import com.connectgas.app.model.order.PurchaseOrder;
import com.connectgas.app.model.order.PurchaseOrderStatus;
import com.connectgas.app.model.payment.AccountHolderType;
import com.connectgas.app.model.payment.PaymentBacklog;
import com.connectgas.app.model.user.User;
import com.connectgas.app.model.user.UserRole;
import com.connectgas.app.notification.Notification;
import com.connectgas.app.notification.NotificationService;
import com.connectgas.app.repository.SimpleFirestoreRepository;

@Service
public class PurchaseOrderService {

	@Autowired
	private SimpleFirestoreRepository<PurchaseOrder, String> purchaseOrderRepository;

	@Autowired
	private SimpleFirestoreRepository<User, String> userRepository;

	@Autowired
	private SimpleFirestoreRepository<PaymentBacklog, String> paymentRepository;

	@Autowired
	private NotificationService notificationService;

	@Autowired
	private DealerInventoryProcessor dealerInventoryProcessor;

	public PurchaseOrder getOrder(String id) {
		return purchaseOrderRepository.fetchById(id, PurchaseOrder.class)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
						"Order id " + id + " not available in the system"));
	}

	private PurchaseOrder saveOrUpdateOrder(PurchaseOrder order) {
		if (order.getPurchaseOrderStatus().equals(PurchaseOrderStatus.PAYMENT_INFO_UPDATED)) {
			updatePaymentInfoAndPaymentBacklog(order);
		}
		Notification notification = new Notification(
				"Purchase Order Id " + order.getId() + " status updated to " + order.getPurchaseOrderStatus());

		if ("Dealer".equalsIgnoreCase(order.getPurchaseOrderStatus().getRole())) {
			notificationService.notify(notification, order.getDealerId());
		} else if ("Candf".equalsIgnoreCase(order.getPurchaseOrderStatus().getRole())) {
			notificationService.notify(notification, order.getCandfId());
		}

		if (PurchaseOrderStatus.DELIVERED.equals(order.getPurchaseOrderStatus()))
			dealerInventoryProcessor.updateDealerInventory(order);

		return purchaseOrderRepository.save(order, PurchaseOrder.class);

	}

	public PurchaseOrder directOrder(PurchaseOrder order, String modifiedBy) {
		order.setId("PO" + Instant.now().getEpochSecond());
		order.setCreatedAt(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
		order.setLastmodifiedAt(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
		return saveOrUpdateOrder(order);
	}

	public PurchaseOrder updateOrderStatus(String orderId, String status, String modifiedBy) {

		PurchaseOrder dbOrder = purchaseOrderRepository.fetchById(orderId, PurchaseOrder.class)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
						"Error while updating order id " + orderId + " not available in the system"));
		dbOrder.setLastmodifiedAt(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
		dbOrder.setLastmodifiedBy(modifiedBy);
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

	public PurchaseOrder udpateOrder(PurchaseOrder order, String userId) {

		order.setLastmodifiedAt(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
		order.setLastmodifiedBy(userId);
		return saveOrUpdateOrder(order);
	}

	private void updatePaymentInfoAndPaymentBacklog(PurchaseOrder dbOrder) {

		Double totalPaid = 0.0;
		for (PaidDetails pd : dbOrder.getPaymentInfo().getPaidDetails()) {
			totalPaid = totalPaid + pd.getAmount();
		}
		Double backlogAmount = dbOrder.getPaymentInfo().getBillAmount() - totalPaid;
		Map<String, String> criteria = new HashMap<>();
		criteria.put("accountHolderType", AccountHolderType.DEALER.toString());
		criteria.put("id", dbOrder.getDealerId());
		List<PaymentBacklog> pbs = paymentRepository.findByPathAndValue(criteria, PaymentBacklog.class);
		PaymentBacklog pb = null;
		if (CollectionUtils.isEmpty(pbs)) {
			pb = new PaymentBacklog();
			pb.setAccountHolderType(AccountHolderType.CUSTOMER);
			pb.setId(dbOrder.getDealerId());
			pb.setCreatedAt(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
			pb.setStatus(State.ACTIVE);
			pb.setLastmodifiedBy("SYSTEM");
			pb.setCreatedBy("SYSTEM");
			pb.setAuditLogMsg("New Payment Backlog Created");
			pb.setBacklogAmount(backlogAmount);
		} else {
			pb = pbs.get(0);
			pb.setAuditLogMsg("Payment Backlog updated, Previous balance " + pb.getBacklogAmount());
			pb.setBacklogAmount(pb.getBacklogAmount() + backlogAmount);
		}
		pb.setLastmodifiedAt(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
		paymentRepository.save(pb, PaymentBacklog.class);

	}
}
