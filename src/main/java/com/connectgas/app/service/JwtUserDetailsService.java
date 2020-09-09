package com.connectgas.app.service;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.connectgas.app.model.customer.Customer;
import com.connectgas.app.model.user.User;
import com.connectgas.app.repository.SimpleFirestoreRepository;

@Service("jwtUserDetailsService")
public class JwtUserDetailsService implements UserDetailsService {

	private static final Logger log = LoggerFactory.getLogger(JwtUserDetailsService.class);

	@Autowired
	private SimpleFirestoreRepository<User, String> userRepository;

	@Autowired
	private SimpleFirestoreRepository<Customer, String> customerRepository;

	@Override
	public UserDetails loadUserByUsername(String userNameAndAuthScope) {
		org.springframework.security.core.userdetails.User authenticatedUser = null;
		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();

		log.info("JwtUserDetailsService::loadUserByUsername::{}", userNameAndAuthScope);
		if (userNameAndAuthScope.contains("APP_USER")) {
			String phone = userNameAndAuthScope.substring(0, userNameAndAuthScope.indexOf('|'));
			User user = userRepository.findAll(User.class).stream().filter(u -> u.getPhone().equals(phone)).findFirst()
					.orElseThrow(() -> new UsernameNotFoundException(phone));

			grantedAuthorities.add(new SimpleGrantedAuthority(user.getRole().toString()));

			authenticatedUser = new org.springframework.security.core.userdetails.User(userNameAndAuthScope,
					user.getPassword(), grantedAuthorities);
		} else if (userNameAndAuthScope.contains("CUSTOMER")) {
			String phone = userNameAndAuthScope.substring(0, userNameAndAuthScope.indexOf('|'));
			Customer customer = customerRepository.findAll(Customer.class).stream()
					.filter(u -> u.getPhone().equals(phone)).findFirst()
					.orElseThrow(() -> new UsernameNotFoundException(phone));

			grantedAuthorities.add(new SimpleGrantedAuthority("CUSTOMER"));

			authenticatedUser = new org.springframework.security.core.userdetails.User(userNameAndAuthScope,
					customer.getPassword(), grantedAuthorities);

		}
		return authenticatedUser;
	}

}