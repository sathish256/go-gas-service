package com.connectgas.app.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.connectgas.app.exception.ConnectGasDataAccessException;
import com.connectgas.app.model.common.InventoryLog;
import com.connectgas.app.model.common.State;
import com.connectgas.app.model.inventory.CustomerHolding;
import com.connectgas.app.model.inventory.DealerInventory;
import com.connectgas.app.model.order.Order;
import com.connectgas.app.model.order.PurchaseOrder;
import com.connectgas.app.repository.FirebaseRealtimeDatabase;
import com.connectgas.app.repository.SimpleFirestoreRepository;

@Service
public class DealerInventoryProcessor {

	private static final String CUSTOMER_ORDER = "CustomerOrder";
	private static final String PURCHASE_ORDER = "PurchaseOrder";

	@Autowired
	private SimpleFirestoreRepository<DealerInventory, String> dealerInventoryRepository;

	@Autowired
	private FirebaseRealtimeDatabase firebaseRealtimeDatabase;

	public void validateAndProcessNewOrder(Order order) {

		DealerInventory di = initializeDealerInventory(order.getDealerId());

		Map<String, Integer> availableStock = Optional.ofNullable(di.getAvailableStock()).orElse(new HashMap<>());
		Map<String, Integer> inTransit = Optional.ofNullable(di.getInTransitStock()).orElse(new HashMap<>());

		if (!CollectionUtils.isEmpty(order.getOrderedProducts())) {
			order.getOrderedProducts().forEach((p) -> {
				Integer availableQty = availableStock.getOrDefault(p.getProductId(), 0);
				if (availableQty < p.getQuantity())
					throw new ConnectGasDataAccessException(
							"No available stock at the moment to process your Order! Please contact dealer");
				Integer updateQty = availableStock.getOrDefault(p.getProductId(), 0) - p.getQuantity();
				availableStock.put(p.getProductId(), updateQty);

				updateQty = inTransit.getOrDefault(p.getProductId(), 0) + p.getQuantity();
				inTransit.put(p.getProductId(), updateQty);

			});

		}

		di.setAvailableStock(availableStock);
		di.setInTransitStock(inTransit);
		di.setLastmodifiedAt(LocalDateTime.now().toString());
		di.setLastmodifiedBy("SYSTEM");
		dealerInventoryRepository.save(di, DealerInventory.class);
		InventoryLog inventoryLog = new InventoryLog(order.getId(),
				"AvailableStocks to In-Transit :: deliveryId :: " + order.getDeliveryPersonId(), CUSTOMER_ORDER);
		firebaseRealtimeDatabase.save(inventoryLog, InventoryLog.class);

	}

	public void processDelivery(Order order) {

		DealerInventory di = initializeDealerInventory(order.getDealerId());

		Map<String, Integer> inTransit = Optional.ofNullable(di.getInTransitStock()).orElse(new HashMap<>());
		Set<CustomerHolding> customerHoldings = Optional.ofNullable(di.getCustomerHoldings()).orElse(new HashSet<>());
		Map<String, Integer> emptyStocks = Optional.ofNullable(di.getEmptyStock()).orElse(new HashMap<>());

		CustomerHolding customerHldgs = customerHoldings.stream()
				.filter(ch -> ch.getCustomerId().equals(order.getCustomer().getId())).findFirst()
				.orElse(new CustomerHolding(order.getCustomer().getId(), order.getCustomer().getId(), new HashMap<>()));

		Map<String, Integer> products = Optional.ofNullable(customerHldgs.getProducts()).orElse(new HashMap<>());

		if (!CollectionUtils.isEmpty(order.getOrderedProducts())) {
			order.getOrderedProducts().forEach((p) -> {
				Integer updateQty = inTransit.getOrDefault(p.getProductId(), 0) - p.getQuantity();
				inTransit.put(p.getProductId(), updateQty);

				updateQty = products.getOrDefault(p.getProductId(), 0) + p.getQuantity();
				products.put(p.getProductId(), updateQty);
			});

		}

		if (!CollectionUtils.isEmpty(order.getReturnProducts())) {
			order.getReturnProducts().forEach((p) -> {

				Integer updateQty = products.getOrDefault(p.getProductId(), 0) + p.getQuantity();
				products.put(p.getProductId(), updateQty);

				updateQty = emptyStocks.getOrDefault(p.getProductId(), 0) + p.getQuantity();
				emptyStocks.put(p.getProductId(), updateQty);

			});

		}

		customerHldgs.setProducts(products); // List of Products
		customerHoldings.add(customerHldgs); // Set
		di.setInTransitStock(inTransit);
		di.setCustomerHoldings(customerHoldings);
		di.setEmptyStock(emptyStocks);
		di.setLastmodifiedAt(LocalDateTime.now().toString());
		di.setLastmodifiedBy("SYSTEM");
		dealerInventoryRepository.save(di, DealerInventory.class);
		InventoryLog inventoryLog = new InventoryLog(order.getId(),
				"In-Transit to CustomerHoldings:: deliveryId :: " + order.getDeliveryPersonId(), CUSTOMER_ORDER);
		firebaseRealtimeDatabase.save(inventoryLog, InventoryLog.class);

	}

	public void updateDealerInventory(PurchaseOrder order) {

		DealerInventory di = initializeDealerInventory(order.getDealerId());

		Map<String, Integer> availableStock = Optional.ofNullable(di.getAvailableStock()).orElse(new HashMap<>());
		Map<String, Integer> emptyStocks = Optional.ofNullable(di.getEmptyStock()).orElse(new HashMap<>());

		if (!CollectionUtils.isEmpty(order.getOrderedProducts())) {
			order.getOrderedProducts().forEach((p) -> {
				Integer updateQty = availableStock.getOrDefault(p.getProductId(), 0) + p.getQuantity();
				availableStock.put(p.getProductId(), updateQty);
			});

		}

		if (!CollectionUtils.isEmpty(order.getReturnProducts())) {
			order.getReturnProducts().forEach((p) -> {
				Integer updateQty = emptyStocks.getOrDefault(p.getProductId(), 0) - p.getQuantity();
				emptyStocks.put(p.getProductId(), updateQty);
			});

		}

		di.setEmptyStock(emptyStocks);
		di.setAvailableStock(availableStock);
		di.setLastmodifiedAt(LocalDateTime.now().toString());
		di.setLastmodifiedBy("SYSTEM");
		dealerInventoryRepository.save(di, DealerInventory.class);
		InventoryLog inventoryLog = new InventoryLog(order.getId(),
				"New Dispatch from CandF to AvailableStocks:: candfId :: " + order.getCandfId(), PURCHASE_ORDER);
		firebaseRealtimeDatabase.save(inventoryLog, InventoryLog.class);
	}

	private DealerInventory initializeDealerInventory(String dealerId) {

		DealerInventory di = dealerInventoryRepository.fetchById(dealerId, DealerInventory.class)
				.orElse(new DealerInventory());
		if (StringUtils.isEmpty(di.getId())) {
			di.setId(dealerId);
			di.setCreatedAt(LocalDateTime.now().toString());
			di.setCreatedBy("SYSTEM");
			di.setLastmodifiedAt(LocalDateTime.now().toString());
			di.setLastmodifiedBy("SYSTEM");
			di.setStatus(State.ACTIVE);
		}
		return di;
	}

	public void processCancellation(Order order) {

		DealerInventory di = initializeDealerInventory(order.getDealerId());

		Map<String, Integer> availableStock = Optional.ofNullable(di.getAvailableStock()).orElse(new HashMap<>());
		Map<String, Integer> inTransit = Optional.ofNullable(di.getInTransitStock()).orElse(new HashMap<>());

		if (!CollectionUtils.isEmpty(order.getOrderedProducts())) {
			order.getOrderedProducts().forEach((p) -> {

				Integer updateQty = availableStock.getOrDefault(p.getProductId(), 0) + p.getQuantity();
				availableStock.put(p.getProductId(), updateQty);

				updateQty = inTransit.getOrDefault(p.getProductId(), 0) - p.getQuantity();
				inTransit.put(p.getProductId(), updateQty);

			});

		}

		di.setAvailableStock(availableStock);
		di.setInTransitStock(inTransit);
		di.setLastmodifiedAt(LocalDateTime.now().toString());
		di.setLastmodifiedBy("SYSTEM");
		dealerInventoryRepository.save(di, DealerInventory.class);
		InventoryLog inventoryLog = new InventoryLog(order.getId(), "In-Transit to AvailableStocks:: Order Cancelled",
				CUSTOMER_ORDER);
		firebaseRealtimeDatabase.save(inventoryLog, InventoryLog.class);
	}

}
