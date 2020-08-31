package com.connectgas.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.connectgas.app.model.order.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {

	List<Order> findByCustomerId(String customerId);

	List<Order> findAll();

	List<Order> findByDealerId(String dealerId);

	List<Order> findByDeliveryPersonId(String dpId);

}