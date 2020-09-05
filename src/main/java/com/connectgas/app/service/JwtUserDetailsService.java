package com.connectgas.app.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.connectgas.app.model.user.User;
import com.connectgas.app.repository.SimpleFirestoreRepository;

@Service("jwtUserDetailsService")
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
	private SimpleFirestoreRepository<User, String> userRepository;

	@Override
	public UserDetails loadUserByUsername(String phone) {
		
		List<User> users = userRepository.findAll(User.class);
		User user = userRepository.findAll(User.class).stream().filter(u -> u.getPhone().equals(phone)).findFirst()
				.orElseThrow(() -> new UsernameNotFoundException(phone));

		user.setLastLoginTimestamp(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
		userRepository.save(user, User.class);
		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
		grantedAuthorities.add(new SimpleGrantedAuthority(user.getRole().toString()));

		return new org.springframework.security.core.userdetails.User(user.getPhone().toString(), user.getPassword(),
				grantedAuthorities);
	}

}