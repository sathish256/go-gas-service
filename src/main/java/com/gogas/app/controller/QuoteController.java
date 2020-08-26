package com.gogas.app.controller;

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

import com.gogas.app.model.order.GoGasQuote;
import com.gogas.app.service.QuoteService;

@RestController
@RequestMapping("/v1/quote")
public class QuoteController {

	@Autowired
	private QuoteService quoteService;

	@GetMapping("/{uid}")
	public ResponseEntity<GoGasQuote> getQuote(@PathVariable String uid) {
		return ResponseEntity.ok(quoteService.getGoGasQuote(uid));
	}

	@PostMapping
	public ResponseEntity<GoGasQuote> addQuote(@RequestBody GoGasQuote goGasQuote) {
		return ResponseEntity.ok(quoteService.addGoGasQuote(goGasQuote));
	}

	@PutMapping
	public ResponseEntity<GoGasQuote> updateQuote(@RequestBody GoGasQuote goGasQuote) {
		return ResponseEntity.ok(quoteService.updateGoGasQuote(goGasQuote));
	}

	@GetMapping("/findall")
	public List<GoGasQuote> findAll() {
		return quoteService.search(null, null);
	}

	@GetMapping("/search")
	public List<GoGasQuote> search(@RequestParam(value = "dealerId") String dealerId,
			@RequestParam(value = "customerId") String customerId) {
		return quoteService.search(customerId, dealerId);
	}
}
