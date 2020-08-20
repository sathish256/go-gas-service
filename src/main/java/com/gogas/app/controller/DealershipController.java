package com.gogas.app.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gogas.app.model.Address;
import com.gogas.app.model.Dealership;
import com.gogas.app.repository.DealershipRepository;
import com.gogas.app.service.DealershipService;

@RestController
@RequestMapping("/v1/gogas/dealer")
@CrossOrigin
public class DealershipController {

	@Autowired
	private DealershipService dealershipService;

	@Autowired
	private DealershipRepository dealershipRepository;

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

	@GetMapping("/dummydealership")
	public Dealership bulkcreate() {

		Dealership dealership = new Dealership();
		Address address = new Address();
		address.setId(UUID.randomUUID().toString());
		address.setCity("Bengaluru");
		address.setGeolat("12.3344");
		address.setGeolong("23.5555");
		address.setHouseno("No 7");
		address.setLocality("RT Nagar");
		address.setPincode("560069");
		address.setState("Karnataka");
		address.setStreetname("MG Street");
		dealership.setAddress(address);
		dealership.setCreateat(LocalDateTime.now());
		dealership.setCreatedby("ADMIN01");
		dealership.setLastmodifiedat(LocalDateTime.now());
		dealership.setLastmodifiedby("ADMIN01");

		return dealershipRepository.save(dealership);
	}

	@GetMapping("/findall")
	public List<Dealership> findAll() {
		return dealershipRepository.findAll();
	}
}
