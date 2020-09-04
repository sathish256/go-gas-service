package com.connectgas.app.service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import com.connectgas.app.model.Dealership;
import com.connectgas.app.model.customer.Customer;
import com.connectgas.app.model.customer.CustomerType;
import com.connectgas.app.model.order.ConnectGasQuote;
import com.connectgas.app.model.order.Order;
import com.connectgas.app.model.order.OrderProduct;
import com.connectgas.app.model.order.OrderStatus;
import com.connectgas.app.model.order.PaymentInfo;
import com.connectgas.app.model.order.QuoteProduct;
import com.connectgas.app.model.order.QuoteStatus;
import com.connectgas.app.model.order.dto.OrderCustomer;
import com.connectgas.app.model.order.dto.OrderType;
import com.connectgas.app.model.user.User;
import com.connectgas.app.model.user.UserRole;
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

	public Order getOrder(String id) {
		return orderRepository.fetchById(id, getCollectionName(Order.class), Order.class)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
						"Order id " + id + " not available in the system"));
	}

	private String getCollectionName(Class clazz) {
		return clazz.getSimpleName().toLowerCase();
	}

	public Order generateOrderbyQuote(String quoteId) {

		String loggedInUser = SecurityContextHolder.getContext().getAuthentication().getName();

		ConnectGasQuote quote = quoteRepository
				.fetchById(quoteId, getCollectionName(ConnectGasQuote.class), ConnectGasQuote.class)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
						"Error while placing order for Quote id " + quoteId + " not available in the system"));

		Order order = new Order();

		order.setId("OD" + Instant.now().getEpochSecond());

		order.setCreatedAt(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
		order.setCreatedBy(loggedInUser);

		order.setOrderType(OrderType.COMMERCIAL);
		order.setQuoteId(quoteId);
		order.setDealerId(quote.getDealerId());

		Customer customer = customerRepository
				.fetchById(quote.getCustomerId(), getCollectionName(Customer.class), Customer.class)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
						"Error while placing order for Customer id " + quote.getCustomerId()
								+ " not available in the system"));

		OrderCustomer orderCustomer = new OrderCustomer();
		orderCustomer.setId(quote.getCustomerId());
		orderCustomer.setName(customer.getName());
		orderCustomer.setType(CustomerType.COMMERCIAL);
		orderCustomer.setAddress(customer.getOrganization().getOrgAddress());
		orderCustomer.setPhone(customer.getPhone());
		order.setCustomer(orderCustomer);
		order.setLastmodifiedAt(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
		order.setLastmodifiedBy(loggedInUser);

		List<OrderProduct> orderedProducts = new ArrayList<>();
		BigDecimal billAmount = new BigDecimal(0.0);
		for (QuoteProduct qp : quote.getQuoteProducts()) {
			OrderProduct product = new OrderProduct();
			product.setOrderedPrice(qp.getQuotePrice());
			product.setQuantity(qp.getQuantity());
			product.setProductId(qp.getProductId());

			billAmount = billAmount.add(qp.getQuotePrice().multiply(new BigDecimal(qp.getQuantity())));
			orderedProducts.add(product);
		}

		order.setOrderedProducts(orderedProducts);
		order.setOrderStatus(OrderStatus.PLACED);

		List<PaymentInfo> paymentInfo = new ArrayList<>();
		PaymentInfo payment = new PaymentInfo();
		payment.setBillAmount(billAmount);
		paymentInfo.add(payment);

		order.setPaymentInfo(null);
		order.setReturnProducts(null);

		order.setScheduledAt(LocalDateTime.now().plusHours(2));
		return saveOrUpdateOrder(order, quote, loggedInUser);
	}

	private Order saveOrUpdateOrder(Order order, ConnectGasQuote quote, String loggedInUser) {
		Dealership dealer = dealershipRepository
				.fetchById(order.getDealerId(), getCollectionName(Dealership.class), Dealership.class)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
						"Error while placing order to dealer id " + order.getDealerId()
								+ " not available in the system"));

		orderRepository.save(order, getCollectionName(Order.class));

		if (StringUtils.hasText(order.getCustomer().getPhone()))
			SMSUtil.sendSMS(Long.parseLong(order.getCustomer().getPhone()),
					"Order ID " + order.getId() + " placed to " + dealer.getName());

		if (quote != null) {
			quote.setQuoteStatus(QuoteStatus.ORDERED);
			quote.setLastmodifiedAt(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
			quote.setLastmodifiedBy(loggedInUser);
			quoteRepository.save(quote, getCollectionName(ConnectGasQuote.class));
		}

		return order;
	}

	public Order directOrder(Order order) {
		String loggedInUser = SecurityContextHolder.getContext().getAuthentication().getName();
		order.setId("OD" + Instant.now().getEpochSecond());
		order.setCreatedAt(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
		order.setLastmodifiedAt(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
		if (!order.getOrderStatus().equals(OrderStatus.DELIVERED)
				&& !order.getOrderStatus().equals(OrderStatus.ASSIGNED))
			order.setOrderStatus(OrderStatus.PLACED);
		return saveOrUpdateOrder(order, null, loggedInUser);
	}

	public Order updateOrderStatus(String orderId, String status) {
		String loggedInUser = SecurityContextHolder.getContext().getAuthentication().getName();

		Order dbOrder = orderRepository.fetchById(orderId, getCollectionName(Order.class), Order.class)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
						"Error while updating order id " + orderId + " not available in the system"));
		dbOrder.setLastmodifiedAt(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
		dbOrder.setLastmodifiedBy(loggedInUser);
		dbOrder.setOrderStatus(OrderStatus.valueOf(status));

		if (OrderStatus.DELIVERED.toString().equals(status))
			dbOrder.setDeliveredTimestamp(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));

		return orderRepository.save(dbOrder, getCollectionName(Order.class));
	}

	public List<Order> getOrders(String phone) {

		User user = userRepository.findByPathAndValue("phone", phone, getCollectionName(User.class), User.class)
				.stream().findFirst().orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
						"User id " + phone + " does not exists in the system"));

		if (user.getRole().equals(UserRole.DELIVERY))
			return orderRepository.findByPathAndValue("deliveryPersonId", user.getId(), getCollectionName(Order.class),
					Order.class);

		if (user.getRole().equals(UserRole.DEALER))
			return orderRepository.findByPathAndValue("dealerId", user.getDealershipId(),
					getCollectionName(Order.class), Order.class);

		return null;
	}

	public Order assignDeliveryPerson(String name, String orderid, String userid) {
		Order dbOrder = orderRepository.fetchById(orderid, getCollectionName(Order.class), Order.class)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
						"Error while updating order id " + orderid + " not available in the system"));
		dbOrder.setDeliveryPersonId(userid);
		dbOrder.setLastmodifiedAt(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
		dbOrder.setLastmodifiedBy(name);
		dbOrder.setOrderStatus(OrderStatus.ASSIGNED);

		orderRepository.save(dbOrder, getCollectionName(Order.class));

		return dbOrder;
	}

	public List<Order> search(String dealerId) {
		return orderRepository.findByPathAndValue("dealerId", dealerId, getCollectionName(Order.class), Order.class);
	}

}
