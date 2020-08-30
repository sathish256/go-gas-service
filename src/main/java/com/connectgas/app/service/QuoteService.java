package com.connectgas.app.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import com.connectgas.app.exception.EntityNotFoundException;
import com.connectgas.app.model.order.ConnectGasQuote;
import com.connectgas.app.repository.QuoteRepository;

@Service
public class QuoteService {

	@Autowired
	private QuoteRepository ConnectGasQuoteRepository;

	public ConnectGasQuote getConnectGasQuote(String uid) {
		return ConnectGasQuoteRepository.findById(uid)
				.orElseThrow(() -> new EntityNotFoundException(ConnectGasQuote.class, "id", uid));
	}

	@Transactional
	public ConnectGasQuote addConnectGasQuote(ConnectGasQuote ConnectGasQuote) {

		ConnectGasQuote.setCreatedAt(LocalDateTime.now());
		ConnectGasQuote.setLastmodifiedAt(LocalDateTime.now());

		return ConnectGasQuoteRepository.save(ConnectGasQuote);
	}

	@Transactional
	public ConnectGasQuote updateConnectGasQuote(ConnectGasQuote ConnectGasQuote) {

		ConnectGasQuoteRepository.findById(ConnectGasQuote.getId())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
						"ConnectGasQuote id " + ConnectGasQuote.getId() + " does not exists in the system"));

		ConnectGasQuote.setLastmodifiedAt(LocalDateTime.now());

		return ConnectGasQuoteRepository.save(ConnectGasQuote);
	}

	@Transactional
	public List<ConnectGasQuote> findConnectGasQuoteByCutomerId(String customerId) {
		return Optional.ofNullable(ConnectGasQuoteRepository.findByCustomerId(customerId))
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
						"Could not retrive ConnectGasQuote Infomation"));
	}

	public List<ConnectGasQuote> search(String customerId, String dealerId) {
		if (!StringUtils.isEmpty(customerId))
			return ConnectGasQuoteRepository.findByCustomerId(customerId);
		else if (!StringUtils.isEmpty(dealerId))
			return ConnectGasQuoteRepository.findByDealerId(dealerId);

		return ConnectGasQuoteRepository.findAll();
	}
}
