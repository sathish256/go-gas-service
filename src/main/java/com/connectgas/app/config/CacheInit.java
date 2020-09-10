package com.connectgas.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.connectgas.app.model.CAndF;
import com.connectgas.app.model.Dealership;
import com.connectgas.app.model.customer.Customer;
import com.connectgas.app.model.inventory.DealerInventory;
import com.connectgas.app.model.order.ConnectGasQuote;
import com.connectgas.app.model.order.Order;
import com.connectgas.app.model.order.PurchaseOrder;
import com.connectgas.app.model.payment.PaymentBacklog;
import com.connectgas.app.model.product.Product;
import com.connectgas.app.model.user.User;
import com.connectgas.app.repository.SimpleFirestoreRepository;

@Component
public class CacheInit implements ApplicationListener<ApplicationReadyEvent> {

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
	private SimpleFirestoreRepository<CAndF, String> candFRepository;

	@Autowired
	private SimpleFirestoreRepository<DealerInventory, String> dealerInventoryRepository;

	@Autowired
	private SimpleFirestoreRepository<Product, String> productRepository;

	@Autowired
	private SimpleFirestoreRepository<PurchaseOrder, String> purchaseOrderRepository;

	@Autowired
	private SimpleFirestoreRepository<ConnectGasQuote, String> connectGasQuoteRepository;

	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {

		quoteRepository.findAll(ConnectGasQuote.class);
		customerRepository.findAll(Customer.class);
		connectGasQuoteRepository.findAll(ConnectGasQuote.class);
		orderRepository.findAll(Order.class);
		dealershipRepository.findAll(Dealership.class);
		userRepository.findAll(User.class);
		paymentRepository.findAll(PaymentBacklog.class);
		candFRepository.findAll(CAndF.class);
		dealerInventoryRepository.findAll(DealerInventory.class);
		productRepository.findAll(Product.class);
		purchaseOrderRepository.findAll(PurchaseOrder.class);

	}

}