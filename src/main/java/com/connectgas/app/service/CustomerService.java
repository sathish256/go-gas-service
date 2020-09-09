package com.connectgas.app.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import com.connectgas.app.exception.EntityNotFoundException;
import com.connectgas.app.model.customer.Customer;
import com.connectgas.app.model.dto.ConnectGasResponse;
import com.connectgas.app.model.dto.CredentialsDTO;
import com.connectgas.app.repository.SimpleFirestoreRepository;
import com.connectgas.app.utils.PasswordUtil;
import com.connectgas.app.utils.SMSUtil;

@Service
public class CustomerService {

	@Autowired
	private SimpleFirestoreRepository<Customer, String> customerRepository;

	@Autowired
	@Qualifier("bCryptPasswordEncoder")
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public Customer getCustomer(String uid) {
		return customerRepository.fetchById(uid, getCollectionName())
				.orElseThrow(() -> new EntityNotFoundException(Customer.class, "id", uid));
	}

	private Class<Customer> getCollectionName() {
		return Customer.class;
	}

	public Customer addCustomer(Customer customer) {

		List<Customer> customers = customerRepository.findAll(getCollectionName());

		if (customers.stream().anyMatch(c -> c.getPhone().equals(customer.getPhone())))
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Customer id " + customer.getId() + " already exists in the system");
		Customer savedCustomer = null;
		try {
			customer.setId(UUID.randomUUID().toString());
			String generatedPwd = PasswordUtil.generateRandomPassword();
			customer.setPassword(bCryptPasswordEncoder.encode(generatedPwd));
			customer.setCreatedAt(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
			customer.setLastmodifiedAt(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
			savedCustomer = customerRepository.save(customer, getCollectionName());

			SMSUtil.sendSMS(Long.parseLong(customer.getPhone()),
					"GoGas - NewCustomer login Password : " + generatedPwd);

		} catch (Exception pe) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, pe.getLocalizedMessage());
		}

		return savedCustomer;
	}

	public Customer updateCustomer(Customer customer) {

		List<Customer> customers = customerRepository.findAll(getCollectionName());

		Customer dbCustomer = customers.stream().filter(c -> c.getPhone().equals(customer.getPhone())).findFirst()
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
						"Customer id " + customer.getPhone() + " does not exists in the system"));
		Customer savedCustomer = null;
		try {
			customer.setPassword(dbCustomer.getPassword());
			customer.setLastmodifiedAt(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
			savedCustomer = customerRepository.save(customer, getCollectionName());

		} catch (Exception pe) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, pe.getLocalizedMessage());
		}

		return savedCustomer;
	}

	public ConnectGasResponse changePassword(CredentialsDTO credentialsDTO, boolean isReset) {

		String message = "Password changed successfully!";
		List<Customer> customers = customerRepository.findAll(getCollectionName());

		Customer customer = customers.stream().filter(c -> c.getPhone().equals(credentialsDTO.getPhone())).findFirst()
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
						"Customer id " + credentialsDTO.getPhone() + " does not exists in the system"));

		if (isReset) {
			String generatedPwd = PasswordUtil.generateRandomPassword();
			credentialsDTO.setNewPassword(generatedPwd);
			SMSUtil.sendSMS(Long.parseLong(customer.getPhone()), "ConnectGas Password reset : " + generatedPwd);
			message = "Password changed successfully!";

		} else if (!bCryptPasswordEncoder.matches(credentialsDTO.getCurrentPassword(), customer.getPassword())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Current password for  " + credentialsDTO.getPhone() + " does not match");
		}

		customer.setPassword(bCryptPasswordEncoder.encode(credentialsDTO.getNewPassword()));
		customer.setLastmodifiedAt(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
		customerRepository.save(customer, getCollectionName());

		return new ConnectGasResponse(HttpStatus.OK, message);
	}

	public List<Customer> search(String dealerId) {

		if (StringUtils.hasText(dealerId)) {
			return customerRepository.findAll(getCollectionName()).stream()
					.filter(c -> c.getDealerId().equals(dealerId)).collect(Collectors.toList());
		}

		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Customer Search requires either dealerId");
	}

	public Customer findCustomerByPhone(String phone) {
		return customerRepository.findAll(getCollectionName()).stream().filter(c -> c.getPhone().equals(phone))
				.findFirst().orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
						"Customer id " + phone + " does not exists in the system"));
	}

	public List<Customer> findAll() {
		return customerRepository.findAll(Customer.class);
	}

	public Customer deleteCustomer(String phone) {

		Customer customer = findCustomerByPhone(phone);
		customerRepository.deleteById(customer.getId(), getCollectionName());
		return customer;
	}

}
