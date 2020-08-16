package com.gogas.app.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gogas.app.model.User;
import com.gogas.app.repository.UserRepository;
import com.gogas.app.service.UserService;

@RestController
@RequestMapping("/v1/gogas/user")
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

	@GetMapping("/bulkcreate")
	public String bulkcreate() {
		// save a single User
		userRepository.save(new User("Rajesh", "Bhojwani"));
		userRepository.saveAll(Arrays.asList(new User("Salim", "Khan"), new User("Rajesh", "Parihar"),
				new User("Rahul", "Dravid"), new User("Dharmendra", "Bhojwani")));
		return "Users are created";
	}

	@GetMapping("/findall")
	public List<User> findAll() {
		return userRepository.findAll();
	}
}
