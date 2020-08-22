package com.gogas.app.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import com.gogas.app.model.CAndF;
import com.gogas.app.repository.AddressRepository;
import com.gogas.app.repository.CAndFRepository;

@Service
public class CAndFService {

	@Autowired
	private CAndFRepository candFRepository;

	@Autowired
	private AddressRepository addressRepository;

	public CAndF getCAndF(String uid) {
		return candFRepository.findById(uid).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
				"CAndF id " + uid + " does not exists in the system"));
	}

	public CAndF addCAndF(CAndF candF) {

		if (!StringUtils.isEmpty(candF.getId()) && candFRepository.existsById(candF.getId()))
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"CAndF id " + candF.getId() + " already exists in the system");
		CAndF savedCAndF = null;
		try {
			candF.setCreatedAt(LocalDateTime.now());
			candF.setLastmodifiedAt(LocalDateTime.now());
			candF.setAddress(addressRepository.save(candF.getAddress()));
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
			candF.setLastmodifiedAt(LocalDateTime.now());
			candF.setAddress(addressRepository.save(candF.getAddress()));
			savedCAndF = candFRepository.save(candF);

		} catch (Exception pe) {
			if (pe.getLocalizedMessage().contains("Key (contact)"))
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
						"Contact number " + candF.getId() + " already exists in the system");
		}

		return savedCAndF;
	}

}
