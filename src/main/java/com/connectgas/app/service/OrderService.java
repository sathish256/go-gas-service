package com.connectgas.app.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import com.connectgas.app.model.Dealership;
import com.connectgas.app.model.common.State;
import com.connectgas.app.model.customer.Customer;
import com.connectgas.app.model.customer.CustomerType;
import com.connectgas.app.model.order.ConnectGasQuote;
import com.connectgas.app.model.order.Order;
import com.connectgas.app.model.order.OrderProduct;
import com.connectgas.app.model.order.OrderStatus;
import com.connectgas.app.model.order.PaidDetails;
import com.connectgas.app.model.order.PaymentInfo;
import com.connectgas.app.model.order.QuoteProduct;
import com.connectgas.app.model.order.QuoteStatus;
import com.connectgas.app.model.order.dto.OrderCustomer;
import com.connectgas.app.model.order.dto.OrderLedger;
import com.connectgas.app.model.order.dto.OrderType;
import com.connectgas.app.model.payment.AccountHolderType;
import com.connectgas.app.model.payment.PaymentBacklog;
import com.connectgas.app.model.user.User;
import com.connectgas.app.model.user.UserRole;
import com.connectgas.app.notification.Notification;
import com.connectgas.app.notification.NotificationService;
import com.connectgas.app.repository.SimpleFirestoreRepository;
import com.connectgas.app.utils.SMSUtil;

@Service
public class OrderService {

	@Autowired
	private SimpleFirestoreRepository<Customer, String> customerRepository;

	@Autowired
	private SimpleFirestoreRepository<ConnectGasQuote, String> quoteRepository;

	@Autowired
	private SimpleFirestoreRepository<Order, String> orderRepository;

	@Autowired
	private SimpleFirestoreRepository<Dealership, String> dealershipRepository;

	@Autowired
	private SimpleFirestoreRepository<User, String> userRepository;

	@Autowired
	private SimpleFirestoreRepository<PaymentBacklog, String> paymentRepository;

	@Autowired
	private DealerInventoryProcessor dealerInventoryProcessor;

	@Autowired
	private NotificationService notificationService;

	public Order getOrder(String id) {
		return orderRepository.fetchById(id, Order.class)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
						"Order id " + id + " not available in the system"));
	}

	public Order generateOrderbyQuote(String quoteId, String modifiedBy) {

		ConnectGasQuote quote = quoteRepository.fetchById(quoteId, ConnectGasQuote.class)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
						"Error while placing order for Quote id " + quoteId + " not available in the system"));

		Order order = new Order();

		order.setId("OD" + Instant.now().getEpochSecond());

		order.setCreatedAt(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
		order.setCreatedBy(modifiedBy);

		order.setOrderType(OrderType.COMMERCIAL);
		order.setQuoteId(quoteId);
		order.setDealerId(quote.getDealerId());

		Customer customer = customerRepository.fetchById(quote.getCustomerId(), Customer.class).orElseThrow(
				() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error while placing order for Customer id "
						+ quote.getCustomerId() + " not available in the system"));

		OrderCustomer orderCustomer = new OrderCustomer();
		orderCustomer.setId(quote.getCustomerId());
		orderCustomer.setName(customer.getName());
		orderCustomer.setType(CustomerType.COMMERCIAL);
		orderCustomer.setAddress(customer.getOrganization().getOrgAddress());
		orderCustomer.setPhone(customer.getPhone());
		order.setCustomer(orderCustomer);
		order.setLastmodifiedAt(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
		order.setLastmodifiedBy(modifiedBy);

		List<OrderProduct> orderedProducts = new ArrayList<>();
		Double billAmount = new Double(0.0);
		for (QuoteProduct qp : quote.getQuoteProducts()) {
			OrderProduct product = new OrderProduct();
			product.setOrderedPrice(qp.getQuotePrice());
			product.setQuantity(qp.getQuantity());
			product.setProductId(qp.getProductId());

			billAmount = billAmount + (qp.getQuotePrice() * qp.getQuantity());
			orderedProducts.add(product);
		}

		order.setOrderedProducts(orderedProducts);
		order.setOrderStatus(OrderStatus.PLACED);
		Map<String, String> criteria = new HashMap<>();
		criteria.put("accountHolderType", AccountHolderType.CUSTOMER.toString());
		criteria.put("id", order.getCustomer().getId());
		List<PaymentBacklog> pb = paymentRepository.findByPathAndValue(criteria, PaymentBacklog.class);
		PaymentInfo payment = new PaymentInfo();
		if (CollectionUtils.isEmpty(pb))
			payment.setArrearAmount(0.0);
		else
			payment.setArrearAmount(pb.get(0).getBacklogAmount());

		payment.setBillAmount(billAmount);

		order.setPaymentInfo(payment);
		order.setReturnProducts(null);

		order.setScheduledAt(LocalDateTime.now().plusHours(2).format(DateTimeFormatter.ISO_DATE_TIME));
		return saveOrUpdateOrder(order, quote, modifiedBy);
	}

	private Order saveOrUpdateOrder(Order order, ConnectGasQuote quote, String modifiedBy) {
		Dealership dealer = dealershipRepository.fetchById(order.getDealerId(), Dealership.class).orElseThrow(
				() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error while placing order to dealer id "
						+ order.getDealerId() + " not available in the system"));

		if (OrderStatus.PLACED.equals(order.getOrderStatus())) {
			dealerInventoryProcessor.validateAndProcessNewOrder(order);
			if (StringUtils.hasText(order.getCustomer().getPhone()))
				SMSUtil.sendSMS(Long.parseLong(order.getCustomer().getPhone()),
						"Order ID " + order.getId() + " placed to " + dealer.getName());
		}
		if (OrderStatus.CANCELLED.equals(order.getOrderStatus()))
			dealerInventoryProcessor.processCancellation(order);

		if (OrderStatus.DELIVERED.equals(order.getOrderStatus())) {
			updatePaymentInfoAndPaymentBacklog(order);
			order.setDeliveredTimestamp(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
			dealerInventoryProcessor.processDelivery(order);
			Notification notification = new Notification(
					"Order Id " + order.getId() + " has been delivered to " + order.getCustomer().getName());
			notificationService.notify(notification, order.getDealerId());
		} else {
			Notification notification = new Notification(
					"Order Id " + order.getId() + " status changed to " + order.getOrderStatus());
			notificationService.notify(notification, order.getDealerId());
		}

		orderRepository.save(order, Order.class);

		if (quote != null) {
			quote.setQuoteStatus(QuoteStatus.ORDERED);
			quote.setLastmodifiedAt(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
			quote.setLastmodifiedBy(modifiedBy);
			quoteRepository.save(quote, ConnectGasQuote.class);
		}

		return order;
	}

	public Order directOrder(Order order, String modifiedBy) {
		order.setId("OD" + Instant.now().getEpochSecond());
		order.setCreatedAt(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
		order.setLastmodifiedAt(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
		if (!order.getOrderStatus().equals(OrderStatus.DELIVERED)
				&& !order.getOrderStatus().equals(OrderStatus.ASSIGNED))
			order.setOrderStatus(OrderStatus.PLACED);

		if (order.getOrderStatus().equals(OrderStatus.ASSIGNED))
			dealerInventoryProcessor.validateAndProcessNewOrder(order);

		return saveOrUpdateOrder(order, null, modifiedBy);
	}

	public Order updateOrderStatus(String orderId, String status, String userId) {

		Order dbOrder = orderRepository.fetchById(orderId, Order.class)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
						"Error while updating order id " + orderId + " not available in the system"));
		dbOrder.setLastmodifiedAt(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
		dbOrder.setLastmodifiedBy(userId);
		dbOrder.setOrderStatus(OrderStatus.valueOf(status));

		return saveOrUpdateOrder(dbOrder, null, userId);
	}

	public Order updatePaymentInfo(String orderId, PaymentInfo paymentInfo, String userId) {

		Order dbOrder = orderRepository.fetchById(orderId, Order.class)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
						"Error while updating order id " + orderId + " not available in the system"));
		dbOrder.setLastmodifiedAt(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
		dbOrder.setLastmodifiedBy(userId);
		dbOrder.setPaymentInfo(paymentInfo);

		return orderRepository.save(dbOrder, Order.class);
	}

	private void updatePaymentInfoAndPaymentBacklog(Order dbOrder) {

		Double totalPaid = 0.0;
		for (PaidDetails pd : dbOrder.getPaymentInfo().getPaidDetails()) {
			totalPaid = totalPaid + pd.getAmount();
		}
		Double backlogAmount = dbOrder.getPaymentInfo().getBillAmount() - totalPaid;
		Map<String, String> criteria = new HashMap<>();
		criteria.put("accountHolderType", AccountHolderType.CUSTOMER.toString());
		criteria.put("id", dbOrder.getCustomer().getId());
		List<PaymentBacklog> pbs = paymentRepository.findByPathAndValue(criteria, PaymentBacklog.class);
		PaymentBacklog pb = null;
		if (CollectionUtils.isEmpty(pbs)) {
			pb = new PaymentBacklog();
			pb.setAccountHolderType(AccountHolderType.CUSTOMER);
			pb.setId(dbOrder.getCustomer().getId());
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

	public List<Order> getOrders(String phone) {

		User user = userRepository.findAll(User.class).stream().filter(u -> u.getPhone().equals(phone)).findFirst()
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
						"User id " + phone + " does not exists in the system"));

		List<Order> orders = new ArrayList<>();

		if (user.getRole().equals(UserRole.DELIVERY))
			orders = orderRepository.findAll(Order.class).stream().filter(
					o -> StringUtils.hasText(o.getDeliveryPersonId()) && o.getDeliveryPersonId().equals(user.getId()))
					.collect(Collectors.toList());

		if (user.getRole().equals(UserRole.DEALER))
			orders = orderRepository.findAll(Order.class).stream()
					.filter(o -> StringUtils.hasText(o.getDealerId()) && o.getDealerId().equals(user.getDealershipId()))
					.collect(Collectors.toList());

		List<OrderStatus> definedOrder = // define your custom order
				Arrays.asList(OrderStatus.PLACED, OrderStatus.ASSIGNED, OrderStatus.IN_TRANSIT, OrderStatus.DELIVERED,
						OrderStatus.CANCELLED);

		Comparator<Order> comparator = Comparator.comparing(c -> definedOrder.indexOf(c.getOrderStatus()));
		comparator.thenComparing(c -> LocalDateTime.parse(c.getLastmodifiedAt()));
		orders.sort(comparator);

		return orders;
	}

	public Order assignDeliveryPerson(String orderId, String userid, String modifiedBy) {
		Order dbOrder = orderRepository.fetchById(orderId, Order.class)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
						"Error while updating order id " + orderId + " not available in the system"));
		dbOrder.setDeliveryPersonId(userid);
		dbOrder.setLastmodifiedAt(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
		dbOrder.setLastmodifiedBy(modifiedBy);
		dbOrder.setOrderStatus(OrderStatus.ASSIGNED);

		orderRepository.save(dbOrder, Order.class);

		Notification notification = new Notification("New Delivery assigned with Order Id " + orderId);
		notificationService.notify(notification, userid);

		return dbOrder;
	}

	public List<Order> search(String dealerId) {
		return orderRepository.findAll(Order.class).stream().filter(o -> o.getDealerId().equals(dealerId))
				.collect(Collectors.toList());
	}

	public Order updateReturnProducts(String orderId, List<OrderProduct> returnProducts, String modifiedBy) {
		Order dbOrder = orderRepository.fetchById(orderId, Order.class)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
						"Error while updating order id " + orderId + " not available in the system"));
		dbOrder.setLastmodifiedAt(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
		dbOrder.setLastmodifiedBy(modifiedBy);
		dbOrder.setReturnProducts(returnProducts);

		return saveOrUpdateOrder(dbOrder, null, modifiedBy);
	}

	public List<OrderLedger> getOrderLedgerByCustomer(String dealerId, String customerId, LocalDateTime fromDate,
			LocalDateTime toDate) {
		Predicate<Order> predicate = o -> o.getOrderStatus().equals(OrderStatus.DELIVERED)
				&& o.getDealerId().equals(dealerId) && o.getCustomer().getId().equals(customerId)
				&& LocalDateTime.parse(o.getCreatedAt()).isAfter(fromDate)
				&& LocalDateTime.parse(o.getCreatedAt()).isBefore(toDate);
		return orderRepository.findAll(Order.class).stream().filter(predicate).map(Order::getOrderLedger)
				.collect(Collectors.toList());
	}

}
