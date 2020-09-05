package com.connectgas.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.connectgas.app.model.product.Product;
import com.connectgas.app.repository.SimpleFirestoreRepository;
import com.connectgas.app.service.ProductService;

@RestController
@RequestMapping("/v1/product")
public class ProductController {

	@Autowired
	private ProductService productService;

	@Autowired
	private SimpleFirestoreRepository<Product, String> productRepository;

	@GetMapping("/{pid}")
	public ResponseEntity<Product> getProduct(@PathVariable String pid) {
		return ResponseEntity.ok(productService.getProduct(pid));
	}

	@PostMapping
	public ResponseEntity<Product> addProduct(@RequestBody Product product) {
		return ResponseEntity.ok(productService.addProduct(product));
	}

	@PutMapping
	public ResponseEntity<Product> updateProduct(@RequestBody Product product) {
		return ResponseEntity.ok(productService.updateProduct(product));
	}

	@GetMapping("/findall")
	public List<Product> findAll() {
		return productRepository.findAll(Product.class);
	}
}
