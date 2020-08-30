package com.connectgas.app.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.connectgas.app.model.customer.Customer;
import com.connectgas.app.model.order.OrderProduct;
import com.connectgas.app.model.order.OrderStatus;
import com.connectgas.app.model.order.PaymentInfo;
import com.connectgas.app.model.order.dto.OrderCustomer;
import com.connectgas.app.model.order.dto.OrderDTO;
import com.connectgas.app.repository.CustomerRepository;

@Service
public class OrderService {

	@Autowired
	private CustomerRepository customerRepository;

	public OrderDTO getOrder(String id) {
		return null;
	}

	public OrderDTO addConnectGasQuote(String quoteid) {
		return null;
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
		return sampleOrder();
	}

}
