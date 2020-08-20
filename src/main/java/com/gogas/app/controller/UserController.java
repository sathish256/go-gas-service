package com.gogas.app.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

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

import com.gogas.app.model.Address;
import com.gogas.app.model.User;
import com.gogas.app.model.UserRole;
import com.gogas.app.repository.UserRepository;
import com.gogas.app.service.UserService;

@RestController
@RequestMapping("/v1/gogas/user")
@CrossOrigin
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private UserRepository userRepository;

	@GetMapping("/{uid}")
	public ResponseEntity<User> getUser(@PathVariable String uid) {
		return ResponseEntity.ok(userService.getUser(uid));
	}

	@PostMapping
	public ResponseEntity<User> addUser(@RequestBody User user) {
		return ResponseEntity.ok(userService.addUser(user));
	}

	@PutMapping
	public ResponseEntity<User> updateUser(@RequestBody User user) {
		return ResponseEntity.ok(userService.updateUser(user));
	}

	@GetMapping("/dummyuser")
	public User bulkcreate() {

		User user = new User();
		user.setUid("ADMIN01");
		Address address = new Address();
		address.setId(UUID.randomUUID().toString());
		address.setCity("Bengaluru");
		address.setGeolat("12.3344");
		address.setGeolong("23.5555");
		address.setHouseno("No 7");
		address.setLocality("RT Nagar");
		address.setPincode("560069");
		address.setState("Karnataka");
		address.setStreetname("MG Street");
		user.setAddress(address);
		user.setCreateat(LocalDateTime.now());
		user.setCreatedby("ADMIN01");
		user.setFirstName("Naseer");
		user.setLastmodifiedat(LocalDateTime.now());
		user.setLastmodifiedby("ADMIN01");
		user.setLastName("PA");
		user.setRole(UserRole.ADMIN);

		return userRepository.save(user);
	}

	@GetMapping("/findall")
	public List<User> findAll() {
		return userRepository.findAll();
	}
}
