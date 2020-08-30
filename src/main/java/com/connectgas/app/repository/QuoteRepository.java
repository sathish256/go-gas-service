package com.connectgas.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.connectgas.app.model.order.ConnectGasQuote;

@Repository
public interface QuoteRepository extends JpaRepository<ConnectGasQuote, String> {

	List<ConnectGasQuote> findByCustomerId(String customerId);

	List<ConnectGasQuote> findAll();

	List<ConnectGasQuote> findByDealerId(String dealerId);

}