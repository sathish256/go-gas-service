package com.connectgas.app.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.connectgas.app.model.common.InventoryLog;
import com.connectgas.app.model.common.State;
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

	public void processNewOrder(Order order) {

		DealerInventory di = initializeDealerInventory(order.getDealerId());

		Map<String, Integer> availableStock = Optional.ofNullable(di.getAvailableStock()).orElse(new HashMap<>());
		Map<String, Integer> inTransit = Optional.ofNullable(di.getInTransitStock()).orElse(new HashMap<>());

		if (!CollectionUtils.isEmpty(order.getOrderedProducts())) {
			order.getOrderedProducts().forEach((p) -> {
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
		Map<String, Integer> customerHoldings = Optional.ofNullable(di.getCustomerHoldings()).orElse(new HashMap<>());
		Map<String, Integer> emptyStocks = Optional.ofNullable(di.getEmptyStock()).orElse(new HashMap<>());

		if (!CollectionUtils.isEmpty(order.getOrderedProducts())) {
			order.getOrderedProducts().forEach((p) -> {
				Integer updateQty = inTransit.getOrDefault(p.getProductId(), 0) - p.getQuantity();
				inTransit.put(p.getProductId(), updateQty);

				updateQty = customerHoldings.getOrDefault(p.getProductId(), 0) + p.getQuantity();
				customerHoldings.put(p.getProductId(), updateQty);
			});

		}

		if (!CollectionUtils.isEmpty(order.getReturnProducts())) {
			order.getReturnProducts().forEach((p) -> {

				Integer updateQty = customerHoldings.getOrDefault(p.getProductId(), 0) - p.getQuantity();
				customerHoldings.put(p.getProductId(), updateQty);

				updateQty = emptyStocks.getOrDefault(p.getProductId(), 0) + p.getQuantity();
				emptyStocks.put(p.getProductId(), updateQty);

			});

		}

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

}
