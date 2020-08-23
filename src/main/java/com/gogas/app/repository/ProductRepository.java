package com.gogas.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gogas.app.model.product.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {

	List<Product> findByName(String name);

	List<Product> findAll();
}