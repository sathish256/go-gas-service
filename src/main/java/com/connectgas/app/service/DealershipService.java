package com.connectgas.app.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import com.connectgas.app.model.Dealership;
import com.connectgas.app.repository.SimpleFirestoreRepository;

@Service
public class DealershipService {

	@Autowired
	private SimpleFirestoreRepository<Dealership, String> dealershipRepository;

	public Dealership getDealership(String uid) {
		if (StringUtils.isEmpty(uid))
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dealership id should not be empty");
		return dealershipRepository.fetchById(uid, getCollectionName(), Dealership.class)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
						"Dealership id " + uid + " does not exists in the system"));
	}

	private String getCollectionName() {
		return Dealership.class.getSimpleName().toLowerCase();
	}

	public Dealership addDealership(Dealership dealership) {


		Dealership savedDealership = null;
		try {
			dealership.setId(UUID.randomUUID().toString());
			dealership.setCreatedAt(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
			dealership.setLastmodifiedAt(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
			savedDealership = dealershipRepository.save(dealership, getCollectionName());

		} catch (Exception pe) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, pe.getLocalizedMessage());
		}

		return savedDealership;
	}

	public Dealership updateDealership(Dealership dealership) {

		getDealership(dealership.getId());

		Dealership savedDealership = null;
		try {
			dealership.setLastmodifiedAt(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
			savedDealership = dealershipRepository.save(dealership, getCollectionName());

		} catch (Exception pe) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, pe.getLocalizedMessage());
		}

		return savedDealership;
	}

	public List<Dealership> search(String candfId) {
		if (!StringUtils.isEmpty(candfId))
			return dealershipRepository.findByPathAndValue("candfId", candfId, getCollectionName(), Dealership.class);

		return dealershipRepository.findAll(getCollectionName(), Dealership.class);
	}

}
