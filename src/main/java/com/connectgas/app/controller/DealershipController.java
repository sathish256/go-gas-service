package com.connectgas.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.connectgas.app.model.Dealership;
import com.connectgas.app.service.DealershipService;

@RestController
@RequestMapping("/v1/dealer")
public class DealershipController {

	@Autowired
	private DealershipService dealershipService;

	@GetMapping("/{uid}")
	public ResponseEntity<Dealership> getDealership(@PathVariable String uid) {
		return ResponseEntity.ok(dealershipService.getDealership(uid));
	}

	@PostMapping
	public ResponseEntity<Dealership> addDealership(@RequestBody Dealership dealership) {
		return ResponseEntity.ok(dealershipService.addDealership(dealership));
	}

	@PutMapping
	public ResponseEntity<Dealership> updateDealership(@RequestBody Dealership dealership) {
		return ResponseEntity.ok(dealershipService.updateDealership(dealership));
	}

	@GetMapping("/findall")
	public List<Dealership> findAll() {
		return dealershipService.search(null);
	}

	@GetMapping("/search")
	public List<Dealership> search(@RequestParam(value = "candfId") String candfId) {
		return dealershipService.search(candfId);
	}
}
