package com.connectgas.app.controller;

import java.util.List;

import javax.validation.Valid;

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

import com.connectgas.app.model.customer.Customer;
import com.connectgas.app.model.dto.ConnectGasResponse;
import com.connectgas.app.model.dto.CredentialsDTO;
import com.connectgas.app.service.CustomerService;

@RestController
@RequestMapping("/v1/customer")
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	@GetMapping("/{uid}")
	public ResponseEntity<Customer> getCustomer(@PathVariable String uid) {
		return ResponseEntity.ok(customerService.getCustomer(uid));
	}

	@PostMapping
	public ResponseEntity<Customer> addCustomer(@RequestBody Customer customer) {
		return ResponseEntity.ok(customerService.addCustomer(customer));
	}

	@PutMapping
	public ResponseEntity<Customer> updateCustomer(@RequestBody Customer customer) {
		return ResponseEntity.ok(customerService.updateCustomer(customer));
	}

	@GetMapping("/search")
	public List<Customer> search(@RequestParam("dealerId") String dealerId) {
		return customerService.search(dealerId);
	}

	@PostMapping("/change-password")
	public ResponseEntity<ConnectGasResponse> changePassword(@RequestBody @Valid CredentialsDTO credentialsDTO) {
		return ResponseEntity.ok(customerService.changePassword(credentialsDTO, false));
	}

	@PostMapping("/reset-password")
	public ResponseEntity<ConnectGasResponse> resetPassword(@RequestBody @Valid CredentialsDTO credentialsDTO) {
		return ResponseEntity.ok(customerService.changePassword(credentialsDTO, true));
	}
}
