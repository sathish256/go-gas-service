package com.gogas.app.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
import com.gogas.app.model.DealerAllocation;
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
		dealership.setId("dealerid");
		Address address = new Address();
		address.setCity("Bengaluru");
		address.setGeoLat("12.3344");
		address.setGeoLong("23.5555");
		address.setDoorNo("No 7");
		address.setLocality("RT Nagar");
		address.setPincode("560069");
		address.setState("Karnataka");
		address.setStreetName("MG Street");
		dealership.setAddress(address);
		dealership.setCreatedAt(LocalDateTime.now());
		dealership.setCreatedBy("ADMIN01");
		dealership.setLastmodifiedAt(LocalDateTime.now());
		dealership.setLastmodifiedBy("ADMIN01");

		List<DealerAllocation> dealerAllocation = new ArrayList<>();
		DealerAllocation de = new DealerAllocation();
		de.setProductId("product-1");
		de.setQuantity(1000);
		dealerAllocation.add(de);

		dealership.setDealerAllocation(dealerAllocation);

		return dealershipService.addDealership(dealership);
	}

	@GetMapping("/findall")
	public List<Dealership> findAll() {
		return dealershipRepository.findAll();
	}
}
