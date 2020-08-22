package com.gogas.app.controller;

import java.util.List;

import javax.validation.Valid;

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

import com.gogas.app.model.User;
import com.gogas.app.model.dto.CredentialsDTO;
import com.gogas.app.model.dto.GoGasResponse;
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

	@GetMapping("/findall")
	public List<User> findAll() {
		return userRepository.findAll();
	}

	@PostMapping("/change-password")
	public ResponseEntity<GoGasResponse> changePassword(@RequestBody @Valid CredentialsDTO credentialsDTO) {
		return ResponseEntity.ok(userService.changePassword(credentialsDTO, false));
	}

	@PostMapping("/reset-password")
	public ResponseEntity<GoGasResponse> resetPassword(@RequestBody @Valid CredentialsDTO credentialsDTO) {
		return ResponseEntity.ok(userService.changePassword(credentialsDTO, true));
	}
}
