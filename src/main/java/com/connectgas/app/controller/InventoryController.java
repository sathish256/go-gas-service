package com.connectgas.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.connectgas.app.model.inventory.DealerInventory;
import com.connectgas.app.repository.SimpleFirestoreRepository;

@RestController
@RequestMapping("/v1/inventory")
public class InventoryController {

	@Autowired
	private SimpleFirestoreRepository<DealerInventory, String> dealerInventoryRepository;

	@GetMapping("/{dealerId}")
	public ResponseEntity<DealerInventory> getDashboard(@PathVariable String dealerId) {

		DealerInventory di = dealerInventoryRepository.fetchById(dealerId, DealerInventory.class)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
						"No Inventory details availble for the dealerId"));
		return ResponseEntity.ok(di);
	}

}
