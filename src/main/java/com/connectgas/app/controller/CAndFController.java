package com.connectgas.app.controller;

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

import com.connectgas.app.model.CAndF;
import com.connectgas.app.repository.SimpleFirestoreRepository;
import com.connectgas.app.service.CAndFService;

@RestController
@RequestMapping("/v1/candf")
public class CAndFController {

	@Autowired
	private CAndFService candFService;

	@Autowired
	private SimpleFirestoreRepository<CAndF, String> candFRepository;

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

	@GetMapping("/findall")
	public List<CAndF> findAll() {
		return candFRepository.findAll(CAndF.class);
	}
}
