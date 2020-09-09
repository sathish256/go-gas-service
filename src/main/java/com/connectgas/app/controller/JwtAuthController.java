package com.connectgas.app.controller;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.connectgas.app.config.JwtTokenUtil;
import com.connectgas.app.model.common.AccessLog;
import com.connectgas.app.model.customer.Customer;
import com.connectgas.app.model.dto.AuthRequest;
import com.connectgas.app.model.dto.AuthResponse;
import com.connectgas.app.repository.FirebaseRealtimeDatabase;
import com.connectgas.app.service.CustomerService;
import com.connectgas.app.service.JwtUserDetailsService;
import com.connectgas.app.service.UserService;

@RestController
public class JwtAuthController {

	private static final Logger log = LoggerFactory.getLogger(JwtAuthController.class);

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JwtUserDetailsService userDetailsService;

	@Autowired
	private UserService userService;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private FirebaseRealtimeDatabase firebaseRealtimeDatabase;

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthRequest authRequest) throws Exception {

		authenticate(authRequest.getPhone() + "|" + authRequest.getAuthScope().toString(), authRequest.getPassword());

		final UserDetails userDetails = userDetailsService
				.loadUserByUsername(authRequest.getPhone() + "|" + authRequest.getAuthScope().toString());

		final String token = jwtTokenUtil.generateToken(userDetails);

		firebaseRealtimeDatabase.save(new AccessLog(userDetails.getUsername(), authRequest.getAuthScope()),
				AccessLog.class);

		return ResponseEntity.ok(new AuthResponse(token));
	}

	@GetMapping("/loggedinuser")
	public ResponseEntity<?> getLoggedInUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Object b = auth.getPrincipal();
		Collection<? extends GrantedAuthority> a = auth.getAuthorities();
		log.info("Authentication Name in LoggedInUser {} - {} - {}", auth.getName(), b, a);
		String phone = auth.getName().substring(0, auth.getName().indexOf('|'));
		if (auth.getName().contains("APP_USER"))
			return ResponseEntity.ok(userService.findUserByPhone(phone));
		else
			return ResponseEntity.ok(customerService.findCustomerByPhone(phone));
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<Customer> registerCustomer() throws Exception {

		// method to register customer

		return null;

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