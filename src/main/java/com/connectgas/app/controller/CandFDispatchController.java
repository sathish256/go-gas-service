package com.connectgas.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.connectgas.app.model.inventory.CandFDispatch;
import com.connectgas.app.repository.SimpleFirestoreRepository;

@RestController
@RequestMapping("/v1/dispatch")
public class CandFDispatchController {

	@Autowired
	private SimpleFirestoreRepository<CandFDispatch, String> customerRepository;

	@GetMapping("/{id}")
	public ResponseEntity<CandFDispatch> getOrder(@PathVariable String id) {
		CandFDispatch candFDispatch = customerRepository.fetchById(id, CandFDispatch.class)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "DispatchDetails not available"));
		return ResponseEntity.ok(candFDispatch);
	}
}
