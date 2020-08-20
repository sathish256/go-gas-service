package com.gogas.app.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.gogas.app.model.Address;
import com.gogas.app.model.CAndF;
import com.gogas.app.repository.CAndFRepository;
import com.gogas.app.service.CAndFService;

@RestController
@RequestMapping("/v1/gogas/candf")
public class CAndFController {

	@Autowired
	private CAndFService candFService;

	@Autowired
	private CAndFRepository candFRepository;

	@GetMapping("/{uid}")
	public ResponseEntity<CAndF> getCAndF(@PathVariable String uid) {
		return ResponseEntity.ok(candFService.getCAndF(uid));
	}

	@PostMapping
	public ResponseEntity<CAndF> addCAndF(@RequestBody CAndF candF) {
		candF.setId(UUID.randomUUID().toString());
		return ResponseEntity.ok(candFService.addCAndF(candF));
	}

	@PutMapping
	public ResponseEntity<CAndF> updateCAndF(@RequestBody CAndF candF) {
		Optional.ofNullable(candF.getId())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid candF ID"));
		return ResponseEntity.ok(candFService.updateCAndF(candF));
	}

	@GetMapping("/dummycandF")
	public CAndF bulkcreate() {

		CAndF candF = new CAndF();
		candF.setId(UUID.randomUUID().toString());
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
		candF.setAddress(address);
		candF.setCreateat(LocalDateTime.now());
		candF.setCreatedby("ADMIN01");
		candF.setLastmodifiedat(LocalDateTime.now());
		candF.setLastmodifiedby("ADMIN01");

		return candFRepository.save(candF);
	}

	@GetMapping("/findall")
	public List<CAndF> findAll() {
		return candFRepository.findAll();
	}
}
