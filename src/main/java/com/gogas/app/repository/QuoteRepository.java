package com.gogas.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gogas.app.model.order.GoGasQuote;

@Repository
public interface QuoteRepository extends JpaRepository<GoGasQuote, String> {

	List<GoGasQuote> findByCustomerId(String customerId);

	List<GoGasQuote> findAll();

	List<GoGasQuote> findByDealerId(String dealerId);

}