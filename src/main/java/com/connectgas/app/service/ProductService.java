package com.connectgas.app.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import com.connectgas.app.model.product.Product;
import com.connectgas.app.repository.ProductRepository;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;

	public Product getProduct(String uid) {
		return productRepository.findById(uid).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
				"Product id " + uid + " does not exists in the system"));
	}

	public Product addProduct(Product product) {

		if (!StringUtils.isEmpty(product.getId()) && productRepository.existsById(product.getId()))
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Product id " + product.getId() + " already exists in the system");
		Product savedProduct = null;
		try {
			product.setCreatedAt(LocalDateTime.now());
			product.setLastmodifiedAt(LocalDateTime.now());
			savedProduct = productRepository.save(product);

		} catch (Exception pe) {
			if (pe.getLocalizedMessage().contains("constraint"))
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
						"Contact number " + product.getSpecification() + " already exists in the system");
		}

		return savedProduct;
	}

	public Product updateProduct(Product product) {

		if (!productRepository.existsById(product.getId()))
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Product id " + product.getId() + " does not exists in the system");
		Product savedProduct = null;
		try {
			product.setLastmodifiedAt(LocalDateTime.now());
			savedProduct = productRepository.save(product);

		} catch (Exception pe) {
			if (pe.getLocalizedMessage().contains("Key (contact)"))
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
						"Contact number " + product.getSpecification() + " already exists in the system");
		}

		return savedProduct;
	}

}
