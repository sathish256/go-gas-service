package com.connectgas.app.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import com.connectgas.app.model.CAndF;
import com.connectgas.app.repository.SimpleFirestoreRepository;

@Service
public class CAndFService {

	@Autowired
	private SimpleFirestoreRepository<CAndF, String> candFRepository;

	public CAndF getCAndF(String uid) {
		return candFRepository.fetchById(uid, CAndF.class)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
						"CAndF id " + uid + " does not exists in the system"));
	}

	private Class<CAndF> getCollectionName() {
		return CAndF.class;
	}

	public CAndF addCAndF(CAndF candF) {

		CAndF savedCAndF = null;
		try {
			candF.setId(UUID.randomUUID().toString());
			candF.setCreatedAt(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
			candF.setLastmodifiedAt(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
			savedCAndF = candFRepository.save(candF, getCollectionName());

		} catch (Exception pe) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, pe.getLocalizedMessage());
		}

		return savedCAndF;
	}

	public CAndF updateCAndF(CAndF candF) {

		if (StringUtils.isEmpty(candF.getId()))
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "CAndF id should not be empty to update");
		CAndF savedCAndF = null;
		try {
			candF.setLastmodifiedAt(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
			savedCAndF = candFRepository.save(candF, getCollectionName());
		} catch (Exception pe) {
			if (pe.getLocalizedMessage().contains("Key (contact)"))
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
						"Contact number " + candF.getId() + " already exists in the system");
		}

		return savedCAndF;
	}

}
