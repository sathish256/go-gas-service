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

import com.connectgas.app.model.order.ConnectGasQuote;
import com.connectgas.app.service.QuoteService;

@RestController
@RequestMapping("/v1/quote")
public class QuoteController {

	@Autowired
	private QuoteService quoteService;

	@GetMapping("/{uid}")
	public ResponseEntity<ConnectGasQuote> getQuote(@PathVariable String uid) {
		return ResponseEntity.ok(quoteService.getConnectGasQuote(uid));
	}

	@PostMapping
	public ResponseEntity<ConnectGasQuote> addQuote(@RequestBody ConnectGasQuote ConnectGasQuote) {
		return ResponseEntity.ok(quoteService.addConnectGasQuote(ConnectGasQuote));
	}

	@PutMapping
	public ResponseEntity<ConnectGasQuote> updateQuote(@RequestBody ConnectGasQuote ConnectGasQuote) {
		return ResponseEntity.ok(quoteService.updateConnectGasQuote(ConnectGasQuote));
	}

	@GetMapping("/findall")
	public List<ConnectGasQuote> findAll() {
		return quoteService.search(null, null);
	}

	@GetMapping("/search")
	public List<ConnectGasQuote> search(@RequestParam(value = "dealerId") String dealerId,
			@RequestParam(value = "customerId") String customerId) {
		return quoteService.search(customerId, dealerId);
	}
}
