package com.connectgas.app.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import com.connectgas.app.model.product.Product;
import com.connectgas.app.repository.SimpleFirestoreRepository;

@Service
public class ProductService {

	@Autowired
	private SimpleFirestoreRepository<Product, String> productRepository;

	public Product getProduct(String uid) {
		if (StringUtils.isEmpty(uid))
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product id should not be empty");
		return productRepository.fetchById(uid, Product.class)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
						"Product id " + uid + " does not exists in the system"));
	}

	private Class<Product> getCollectionName() {
		return Product.class;
	}

	public Product addProduct(Product product) {

		Product savedProduct = null;
		try {
			product.setId(UUID.randomUUID().toString());
			product.setCreatedAt(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
			product.setLastmodifiedAt(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
			savedProduct = productRepository.save(product, getCollectionName());

		} catch (Exception pe) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"Error while creating product: " + pe.getLocalizedMessage());
		}

		return savedProduct;
	}

	public Product updateProduct(Product product) {

		if (StringUtils.isEmpty(product.getId()))
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product id should not be null");
		Product savedProduct = null;
		try {
			product.setLastmodifiedAt(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
			savedProduct = productRepository.save(product, getCollectionName());

		} catch (Exception pe) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"Error while creating product: " + pe.getLocalizedMessage());
		}

		return savedProduct;
	}

}
