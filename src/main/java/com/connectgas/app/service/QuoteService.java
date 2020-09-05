package com.connectgas.app.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import com.connectgas.app.exception.EntityNotFoundException;
import com.connectgas.app.model.order.ConnectGasQuote;
import com.connectgas.app.repository.SimpleFirestoreRepository;

@Service
public class QuoteService {

	@Autowired
	private SimpleFirestoreRepository<ConnectGasQuote, String> connectGasQuoteRepository;

	public ConnectGasQuote getConnectGasQuote(String uid) {
		return connectGasQuoteRepository.fetchById(uid, ConnectGasQuote.class)
				.orElseThrow(() -> new EntityNotFoundException(ConnectGasQuote.class, "id", uid));
	}

	private Class<ConnectGasQuote> getCollectionName() {
		return ConnectGasQuote.class;
	}

	public ConnectGasQuote addConnectGasQuote(ConnectGasQuote connectGasQuote) {

		connectGasQuote.setId(UUID.randomUUID().toString());

		connectGasQuote.setCreatedAt(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
		connectGasQuote.setLastmodifiedAt(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));

		return connectGasQuoteRepository.save(connectGasQuote, getCollectionName());
	}

	public ConnectGasQuote updateConnectGasQuote(ConnectGasQuote connectGasQuote) {

		connectGasQuoteRepository.fetchById(connectGasQuote.getId(), getCollectionName())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
						"ConnectGasQuote id " + connectGasQuote.getId() + " does not exists in the system"));

		connectGasQuote.setLastmodifiedAt(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));

		return connectGasQuoteRepository.save(connectGasQuote, getCollectionName());
	}

	public List<ConnectGasQuote> findConnectGasQuoteByCutomerId(String customerId) {
		return connectGasQuoteRepository.findAll(getCollectionName()).stream()
				.filter(q -> q.getCustomerId().equals(customerId)).collect(Collectors.toList());

	}

	public List<ConnectGasQuote> search(String customerId, String dealerId) {
		if (!StringUtils.isEmpty(customerId)) {
			return connectGasQuoteRepository.findAll(getCollectionName()).stream()
					.filter(q -> q.getCustomerId().equals(customerId)).collect(Collectors.toList());
		} else if (!StringUtils.isEmpty(dealerId)) {
			return connectGasQuoteRepository.findAll(getCollectionName()).stream()
					.filter(q -> q.getDealerId().equals(dealerId)).collect(Collectors.toList());
		}
		return connectGasQuoteRepository.findAll(ConnectGasQuote.class);
	}
}
