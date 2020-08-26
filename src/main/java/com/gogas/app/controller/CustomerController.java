package com.gogas.app.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gogas.app.model.customer.Customer;
import com.gogas.app.model.dto.CredentialsDTO;
import com.gogas.app.model.dto.GoGasResponse;
import com.gogas.app.repository.CustomerRepository;
import com.gogas.app.service.CustomerService;

@RestController
@RequestMapping("/v1/customer")
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	@Autowired
	private CustomerRepository customerRepository;

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

	@PreAuthorize("hasRole('DEALER')")
	@GetMapping("/findall")
	public List<Customer> findAll() {
		return customerRepository.findAll();
	}

	@PostMapping("/change-password")
	public ResponseEntity<GoGasResponse> changePassword(@RequestBody @Valid CredentialsDTO credentialsDTO) {
		return ResponseEntity.ok(customerService.changePassword(credentialsDTO, false));
	}

	@PostMapping("/reset-password")
	public ResponseEntity<GoGasResponse> resetPassword(@RequestBody @Valid CredentialsDTO credentialsDTO) {
		return ResponseEntity.ok(customerService.changePassword(credentialsDTO, true));
	}
}
