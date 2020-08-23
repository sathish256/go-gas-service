package com.gogas.app.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import com.gogas.app.model.Dealership;
import com.gogas.app.repository.AddressRepository;
import com.gogas.app.repository.DealershipRepository;

@Service
public class DealershipService {

	@Autowired
	private DealershipRepository dealershipRepository;

	@Autowired
	private AddressRepository addressRepository;

	public Dealership getDealership(String uid) {
		return dealershipRepository.findById(uid).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
				"Dealership id " + uid + " does not exists in the system"));
	}

	public Dealership addDealership(Dealership dealership) {

		if (!StringUtils.isEmpty(dealership.getId()) && dealershipRepository.existsById(dealership.getId()))
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Dealership id " + dealership.getId() + " already exists in the system");
		Dealership savedDealership = null;
		try {
			dealership.setCreatedAt(LocalDateTime.now());
			dealership.setLastmodifiedAt(LocalDateTime.now());
			dealership.setAddress(addressRepository.save(dealership.getAddress()));
			savedDealership = dealershipRepository.save(dealership);

		} catch (Exception pe) {
			if (pe.getLocalizedMessage().contains("constraint"))
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
						"Contact number " + dealership.getPhone() + " already exists in the system");
		}

		return savedDealership;
	}

	public Dealership updateDealership(Dealership dealership) {

		if (!dealershipRepository.existsById(dealership.getId()))
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Dealership id " + dealership.getId() + " does not exists in the system");
		Dealership savedDealership = null;
		try {
			dealership.setLastmodifiedAt(LocalDateTime.now());
			dealership.setAddress(addressRepository.save(dealership.getAddress()));
			savedDealership = dealershipRepository.save(dealership);

		} catch (Exception pe) {
			if (pe.getLocalizedMessage().contains("Key (contact)"))
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
						"Contact number " + dealership.getPhone() + " already exists in the system");
		}

		return savedDealership;
	}

	public List<Dealership> search(String candfId, String phone) {
		if (!StringUtils.isEmpty(phone))
			return dealershipRepository.findByPhone(phone);
		else if (!StringUtils.isEmpty(candfId))
			return dealershipRepository.findByCandfId(candfId);

		return dealershipRepository.findAll();
	}

}
