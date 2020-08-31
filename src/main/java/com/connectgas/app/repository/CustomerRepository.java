package com.connectgas.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.connectgas.app.model.customer.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {

	List<Customer> findByName(String name);

	List<Customer> findAll();

	Customer findByPhone(String phone);

	boolean existsByPhone(String phone);

	List<Customer> findByDealerId(String dealerid);
}