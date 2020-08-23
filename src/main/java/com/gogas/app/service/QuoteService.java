package com.gogas.app.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import com.gogas.app.exception.EntityNotFoundException;
import com.gogas.app.model.order.GoGasQuote;
import com.gogas.app.repository.QuoteRepository;

@Service
public class QuoteService {

	@Autowired
	private QuoteRepository goGasQuoteRepository;

	public GoGasQuote getGoGasQuote(String uid) {
		return goGasQuoteRepository.findById(uid)
				.orElseThrow(() -> new EntityNotFoundException(GoGasQuote.class, "id", uid));
	}

	@Transactional
	public GoGasQuote addGoGasQuote(GoGasQuote goGasQuote) {

		goGasQuote.setCreatedAt(LocalDateTime.now());
		goGasQuote.setLastmodifiedAt(LocalDateTime.now());

		return goGasQuoteRepository.save(goGasQuote);
	}

	@Transactional
	public GoGasQuote updateGoGasQuote(GoGasQuote goGasQuote) {

		goGasQuoteRepository.findById(goGasQuote.getId())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
						"GoGasQuote id " + goGasQuote.getId() + " does not exists in the system"));

		goGasQuote.setLastmodifiedAt(LocalDateTime.now());

		return goGasQuoteRepository.save(goGasQuote);
	}

	@Transactional
	public List<GoGasQuote> findGoGasQuoteByCutomerId(String customerId) {
		return Optional.ofNullable(goGasQuoteRepository.findByCustomerId(customerId))
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
						"Could not retrive GoGasQuote Infomation"));
	}

	public List<GoGasQuote> search(String customerId, String dealerId) {
		if (!StringUtils.isEmpty(customerId))
			return goGasQuoteRepository.findByCustomerId(customerId);
		else if (!StringUtils.isEmpty(dealerId))
			return goGasQuoteRepository.findByDealerId(dealerId);

		return goGasQuoteRepository.findAll();
	}
}
