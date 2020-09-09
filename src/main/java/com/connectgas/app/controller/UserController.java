package com.connectgas.app.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.connectgas.app.model.dto.ConnectGasResponse;
import com.connectgas.app.model.dto.CredentialsDTO;
import com.connectgas.app.model.user.User;
import com.connectgas.app.service.UserService;

@RestController
@RequestMapping("/v1/user")
public class UserController {

	@Autowired
	private UserService userService;

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
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String phone = auth.getName().substring(0, auth.getName().indexOf('|'));
		return userService.search(phone);
	}

	@PostMapping("/change-password")
	public ResponseEntity<ConnectGasResponse> changePassword(@RequestBody @Valid CredentialsDTO credentialsDTO) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String phone = auth.getName().substring(0, auth.getName().indexOf('|'));
		if (auth != null && !phone.equalsIgnoreCase(credentialsDTO.getPhone()))
			throw new AuthorizationServiceException("Cannot change other user password!");

		return ResponseEntity.ok(userService.changePassword(credentialsDTO, false));
	}

	@PostMapping("/reset-password")
	public ResponseEntity<ConnectGasResponse> resetPassword(@RequestBody @Valid CredentialsDTO credentialsDTO) {
		return ResponseEntity.ok(userService.changePassword(credentialsDTO, true));
	}

	@DeleteMapping("/delete/{phone}")
	public ResponseEntity<User> deleteUser(@PathVariable String phone) {
		return ResponseEntity.ok(userService.deleteUser(phone));
	}
	
	@GetMapping("/search")
	public List<User> search(@RequestParam(value = "candfId") String candfId,
			@RequestParam(value = "dealerId") String dealerId) {
		return userService.search(candfId, dealerId);
	}
}
