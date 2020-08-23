package com.gogas.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gogas.app.model.product.Product;
import com.gogas.app.repository.ProductRepository;
import com.gogas.app.service.ProductService;

@RestController
@RequestMapping("/v1/gogas/product")
public class ProductController {

	@Autowired
	private ProductService productService;

	@Autowired
	private ProductRepository productRepository;

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
		return productRepository.findAll();
	}
}
