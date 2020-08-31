package com.connectgas.app.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.connectgas.app.exception.EntityNotFoundException;
import com.connectgas.app.model.customer.Customer;
import com.connectgas.app.model.customer.Organization;
import com.connectgas.app.model.dto.ConnectGasResponse;
import com.connectgas.app.model.dto.CredentialsDTO;
import com.connectgas.app.repository.AddressRepository;
import com.connectgas.app.repository.CustomerRepository;
import com.connectgas.app.repository.IdentityProofRepository;
import com.connectgas.app.repository.OrganizationRepository;
import com.connectgas.app.utils.PasswordUtil;
import com.connectgas.app.utils.SMSUtil;

@Service
public class CustomerService {

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private AddressRepository addressRepository;

	@Autowired
	private IdentityProofRepository identityProofRepository;

	@Autowired
	private OrganizationRepository organizationRepository;

	@Autowired
	@Qualifier("bCryptPasswordEncoder")
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public Customer getCustomer(String uid) {
		return customerRepository.findById(uid)
				.orElseThrow(() -> new EntityNotFoundException(Customer.class, "id", uid));
	}

	@Transactional
	public Customer addCustomer(Customer customer) {

		if (customerRepository.existsByPhone(customer.getPhone()))
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Customer id " + customer.getId() + " already exists in the system");
		Customer savedCustomer = null;
		try {
			String generatedPwd = PasswordUtil.generateRandomPassword();
			customer.setPassword(bCryptPasswordEncoder.encode(generatedPwd));
			customer.setCreatedAt(LocalDateTime.now());
			customer.setLastmodifiedAt(LocalDateTime.now());
			customer.setAddress(addressRepository.save(customer.getAddress()));
			if (customer.getIdentityProof() != null)
				customer.setIdentityProof(identityProofRepository.save(customer.getIdentityProof()));

			if (customer.getAddressProofId() != null)
				customer.setAddressProofId(identityProofRepository.save(customer.getAddressProofId()));

			if (customer.getOrganization() != null) {
				Organization org = customer.getOrganization();
				org.setOrgAddress(addressRepository.save(org.getOrgAddress()));
				customer.setOrganization(organizationRepository.save(org));
			}
			savedCustomer = customerRepository.save(customer);

			SMSUtil.sendSMS(Long.parseLong(customer.getPhone()),
					"GoGas - NewCustomer login Password : " + generatedPwd);

		} catch (Exception pe) {
			pe.printStackTrace();
			if (pe.getLocalizedMessage().contains("constraint"))
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
						"Contact number " + customer.getPhone() + " already exists in the system");
		}

		return savedCustomer;
	}

	@Transactional
	public Customer updateCustomer(Customer customer) {

		Customer dbCustomer = Optional.ofNullable(customerRepository.findByPhone(customer.getPhone()))
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
						"Customer id " + customer.getPhone() + " does not exists in the system"));

		Customer savedCustomer = null;
		try {
			customer.setPassword(dbCustomer.getPassword());
			customer.setLastmodifiedAt(LocalDateTime.now());
			customer.setAddress(addressRepository.save(customer.getAddress()));
			if (customer.getIdentityProof() != null)
				customer.setIdentityProof(identityProofRepository.save(customer.getIdentityProof()));

			if (customer.getAddressProofId() != null)
				customer.setAddressProofId(identityProofRepository.save(customer.getAddressProofId()));

			if (customer.getOrganization() != null) {
				Organization org = customer.getOrganization();
				org.setOrgAddress(addressRepository.save(org.getOrgAddress()));
				customer.setOrganization(organizationRepository.save(org));
			}
			savedCustomer = customerRepository.save(customer);

		} catch (Exception pe) {
			if (pe.getLocalizedMessage().contains("Key (contact)"))
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
						"Contact number " + customer.getPhone() + " already exists in the system");
		}

		return savedCustomer;
	}

	@Transactional
	public ConnectGasResponse changePassword(CredentialsDTO credentialsDTO, boolean isReset) {

		String message = "Password changed successfully!";

		Customer customer = Optional.ofNullable(customerRepository.findByPhone(credentialsDTO.getPhone()))
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
						"Customer id " + credentialsDTO.getPhone() + " does not exists in the system"));

		if (isReset) {
			String generatedPwd = PasswordUtil.generateRandomPassword();
			credentialsDTO.setNewPassword(generatedPwd);
			SMSUtil.sendSMS(Long.parseLong(customer.getPhone()), "GoGas - Reset Login Password : " + generatedPwd);
			message = "Password changed successfully!";

		} else if (!bCryptPasswordEncoder.matches(credentialsDTO.getCurrentPassword(), customer.getPassword())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Current password for  " + credentialsDTO.getPhone() + " does not match");
		}

		customer.setPassword(bCryptPasswordEncoder.encode(credentialsDTO.getNewPassword()));
		customer.setLastmodifiedAt(LocalDateTime.now());
		customerRepository.save(customer);

		return new ConnectGasResponse(HttpStatus.OK, message);
	}

	@Transactional
	public Customer findCustomerByPhone(String name) {
		return Optional.ofNullable(customerRepository.findByPhone(name))
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
						"Could not retrive Logged in Customer Infomation"));
	}

	public List<Customer> search(String dealerid) {
		return customerRepository.findByDealerId(dealerid);
	}

}
