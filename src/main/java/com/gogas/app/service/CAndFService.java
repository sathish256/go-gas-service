package com.gogas.app.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.gogas.app.model.CAndF;
import com.gogas.app.repository.CAndFRepository;

@Service
public class CAndFService {

	@Autowired
	private CAndFRepository candFRepository;

	public CAndF getCAndF(String uid) {
		return candFRepository.findById(uid).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
				"CAndF id " + uid + " does not exists in the system"));
	}

	public CAndF addCAndF(CAndF candF) {

		if (candFRepository.existsById(candF.getId()))
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"CAndF id " + candF.getId() + " already exists in the system");
		CAndF savedCAndF = null;
		try {
			candF.setCreateat(LocalDateTime.now());
			candF.setLastmodifiedat(LocalDateTime.now());
			savedCAndF = candFRepository.save(candF);

		} catch (Exception pe) {
			if (pe.getLocalizedMessage().contains("constraint"))
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
						"Contact number " + candF.getId() + " already exists in the system");
		}

		return savedCAndF;
	}

	public CAndF updateCAndF(CAndF candF) {

		if (!candFRepository.existsById(candF.getId()))
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"CAndF id " + candF.getId() + " does not exists in the system");
		CAndF savedCAndF = null;
		try {
			candF.setLastmodifiedat(LocalDateTime.now());
			savedCAndF = candFRepository.save(candF);

		} catch (Exception pe) {
			if (pe.getLocalizedMessage().contains("Key (contact)"))
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
						"Contact number " + candF.getId() + " already exists in the system");
		}

		return savedCAndF;
	}

}
