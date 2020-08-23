package com.gogas.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gogas.app.model.customer.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {

	List<Customer> findByName(String name);

	List<Customer> findAll();

	Customer findByPhone(Long phone);

	boolean existsByPhone(Long phone);
}