package com.connectgas.app.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
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
import com.connectgas.app.model.order.dto.OrderCustomer;
import com.connectgas.app.model.order.dto.OrderDTO;
import com.connectgas.app.repository.CustomerRepository;
import com.connectgas.app.repository.DealershipRepository;
import com.connectgas.app.repository.OrderRepository;
import com.connectgas.app.repository.QuoteRepository;
import com.connectgas.app.utils.SMSUtil;

@Service
public class OrderService {

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private QuoteRepository quoteRepository;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private DealershipRepository dealershipRepository;

	public OrderDTO getOrder(String id) {
		return null;
	}

	public OrderDTO generateOrderbyQuote(String quoteId) {

		String loggedInUser = SecurityContextHolder.getContext().getAuthentication().getName();

		ConnectGasQuote quote = quoteRepository.findById(quoteId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
						"Error while placing order for Quote id " + quoteId + " not available in the system"));

		OrderDTO order = new OrderDTO();

		order.setCreatedAt(LocalDateTime.now());
		order.setCreatedBy(loggedInUser);

		order.setOrderedBy("Quote");
		order.setQuoteId(quoteId);
		order.setDealerId(quote.getDealerId());

		Customer customer = customerRepository.findById(quote.getCustomerId()).orElseThrow(
				() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error while placing order for Customer id "
						+ quote.getCustomerId() + " not available in the system"));

		OrderCustomer orderCustomer = new OrderCustomer();
		orderCustomer.setId(quote.getCustomerId());
		orderCustomer.setName(customer.getName());
		orderCustomer.setType(CustomerType.COMMERCIAL);
		orderCustomer.setAddress(customer.getOrganization().getOrgAddress());
		orderCustomer.setPhone(customer.getPhone());
		order.setCustomer(orderCustomer);

		order.setLastmodifiedAt(LocalDateTime.now());
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
		order.setOrderStatus(OrderStatus.PLACED.toString());

		List<PaymentInfo> paymentInfo = new ArrayList<>();
		PaymentInfo payment = new PaymentInfo();
		payment.setBillAmount(billAmount);
		paymentInfo.add(payment);

		order.setPaymentInfo(null);
		order.setReturnProducts(null);

		return saveOrUpdateOrder(order);
	}

	private OrderDTO saveOrUpdateOrder(OrderDTO order) {
		Dealership dealer = dealershipRepository.findById(order.getDealerId()).orElseThrow(
				() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error while placing order to dealer id "
						+ order.getDealerId() + " not available in the system"));
		Order dbOrder = new Order();

		BeanUtils.copyProperties(order, dbOrder);
		dbOrder.setCustomerId(order.getCustomer().getId());

		orderRepository.save(dbOrder);

		order.setId(dbOrder.getId());
		if (StringUtils.hasText(order.getCustomer().getPhone()))
			SMSUtil.sendSMS(Long.parseLong(order.getCustomer().getPhone()),
					"Order ID " + dbOrder.getId() + " placed to " + dealer.getName());

		return order;
	}

	public OrderDTO directOrder(OrderDTO order) {
		return null;
	}

	public OrderDTO updateOrderStatus(String status) {
		return null;
	}

	public List<OrderDTO> getOrders(String name) {
		return null;
	}

	public OrderDTO sampleOrder() {
		Customer cus = customerRepository.findAll().get(0);
		OrderDTO order = new OrderDTO();
		order.setId(1001L);
		order.setCreatedAt(LocalDateTime.now());
		order.setCreatedBy("user23");
		OrderCustomer customer = new OrderCustomer();
		customer.setAddress(cus.getAddress());
		customer.setId(cus.getId());
		customer.setName(cus.getName());
		customer.setType(cus.getType());
		order.setCustomer(customer);

		order.setDealerId("dealerid");
		order.setDeliveryPersonId("deliveryPersonId");
		order.setLastmodifiedAt(LocalDateTime.now());
		order.setLastmodifiedBy("user23");
		order.setOrderedBy("Quote");
		order.setQuoteId("quote123");
		List<OrderProduct> orderedProducts = new ArrayList<>();
		OrderProduct product = new OrderProduct();
		product.setOrderedPrice(BigDecimal.valueOf(1300.00));
		product.setQuantity(5);
		product.setProductId("productId");
		orderedProducts.add(product);
		order.setOrderedProducts(orderedProducts);
		order.setOrderStatus(OrderStatus.PLACED.toString());

		List<PaymentInfo> paymentInfo = new ArrayList<>();
		PaymentInfo payment = new PaymentInfo();
		payment.setPaymentType("COD");
		payment.setBillAmount(BigDecimal.valueOf(1300.00));
		payment.setPartialPay(false);
		paymentInfo.add(payment);
		order.setPaymentInfo(paymentInfo);
		order.setReturnProducts(orderedProducts);

		return order;
	}

	public OrderDTO assignDeliveryPerson(String name, String orderid, String userid) {
		Order dbOrder = orderRepository.findById(orderid)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
						"Error while updating order id " + orderid + " not available in the system"));
		dbOrder.setDeliveryPersonId(userid);
		dbOrder.setLastmodifiedAt(LocalDateTime.now());
		dbOrder.setLastmodifiedBy(name);

		orderRepository.save(dbOrder);

		OrderDTO order = new OrderDTO();
		BeanUtils.copyProperties(dbOrder, order);

		Customer customer = customerRepository.findById(dbOrder.getCustomerId()).orElseThrow(
				() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error while placing order for Customer id "
						+ dbOrder.getCustomerId() + " not available in the system"));

		OrderCustomer orderCustomer = new OrderCustomer();
		orderCustomer.setId(customer.getId());
		orderCustomer.setName(customer.getName());
		orderCustomer.setType(CustomerType.COMMERCIAL);
		orderCustomer.setAddress(customer.getOrganization().getOrgAddress());

		order.setCustomer(orderCustomer);
		return order;
	}

}
