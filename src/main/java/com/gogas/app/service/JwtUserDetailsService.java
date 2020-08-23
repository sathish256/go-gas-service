package com.gogas.app.service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gogas.app.model.user.User;
import com.gogas.app.repository.UserRepository;

@Service("jwtUserDetailsService")
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String phone) {
		User user = userRepository.findByPhone(Long.parseLong(phone));
		if (user == null)
			throw new UsernameNotFoundException(phone);
		user.setLastLoginTimestamp(LocalDateTime.now());
		userRepository.save(user);
		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
		grantedAuthorities.add(new SimpleGrantedAuthority(user.getRole().toString()));

		return new org.springframework.security.core.userdetails.User(user.getPhone().toString(), user.getPassword(),
				grantedAuthorities);
	}

}