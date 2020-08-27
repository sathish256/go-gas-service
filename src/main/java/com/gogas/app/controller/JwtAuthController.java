package com.gogas.app.controller;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gogas.app.config.JwtTokenUtil;
import com.gogas.app.model.common.Address;
import com.gogas.app.model.common.IdentityProof;
import com.gogas.app.model.dto.AuthRequest;
import com.gogas.app.model.dto.AuthResponse;
import com.gogas.app.model.user.User;
import com.gogas.app.model.user.UserRole;
import com.gogas.app.service.JwtUserDetailsService;
import com.gogas.app.service.UserService;

@RestController
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

	@GetMapping("/loggedinuser")
	public ResponseEntity<User> getLoggedInUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return ResponseEntity.ok(userService.findUserByPhone(auth.getName()));
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<?> saveUser() throws Exception {

		User user = new User();
		Address address = new Address();
		address.setId(UUID.randomUUID().toString());
		address.setCity("Bengaluru");
		address.setGeoLat("12.3344");
		address.setGeoLong("23.5555");
		address.setDoorNo("No 7");
		address.setLocality("RT Nagar");
		address.setPincode("560069");
		address.setState("Karnataka");
		address.setStreetName("MG Street");
		user.setAddress(address);
		user.setCreatedAt(LocalDateTime.now());
		user.setCreatedBy("ADMIN01");
		user.setFirstName("Sathish");
		user.setLastmodifiedAt(LocalDateTime.now());
		user.setLastmodifiedBy("ADMIN01");
		user.setLastName("K");
		user.setRole(UserRole.ADMIN);
		user.setPhone(9886333900L);
		user.setIdentityProof(new IdentityProof());
		user.setCandfId("cnfid");
		user.setDealershipId("dealerid");
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