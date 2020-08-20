package com.gogas.app.controller;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gogas.app.config.JwtTokenUtil;
import com.gogas.app.model.Address;
import com.gogas.app.model.User;
import com.gogas.app.model.UserRole;
import com.gogas.app.model.dto.AuthRequest;
import com.gogas.app.model.dto.AuthResponse;
import com.gogas.app.service.JwtUserDetailsService;
import com.gogas.app.service.UserService;
import com.gogas.app.utils.PasswordUtil;

@RestController
@CrossOrigin
public class JwtAuthController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JwtUserDetailsService userDetailsService;

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthRequest authenticationRequest)
			throws Exception {

		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

		final String token = jwtTokenUtil.generateToken(userDetails);

		return ResponseEntity.ok(new AuthResponse(token));
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<?> saveUser() throws Exception {

		User user = new User();
		user.setUid("ADMIN01");
		Address address = new Address();
		address.setId(UUID.randomUUID().toString());
		address.setCity("Bengaluru");
		address.setGeolat("12.3344");
		address.setGeolong("23.5555");
		address.setDoorNo("No 7");
		address.setLocality("RT Nagar");
		address.setPincode("560069");
		address.setState("Karnataka");
		address.setStreetName("MG Street");
		user.setAddress(address);
		user.setCreateat(LocalDateTime.now());
		user.setCreatedby("ADMIN01");
		user.setFirstName("Naseer");
		user.setLastmodifiedat(LocalDateTime.now());
		user.setLastmodifiedby("ADMIN01");
		user.setLastName("PA");
		user.setRole(UserRole.ADMIN);
		user.setPhone(9886333900L);
		user.setPassword(PasswordUtil.generateRandomPassword());
		System.out.println(user.getPassword());
		return ResponseEntity.ok(userService.addUser(user));
	}

	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
}