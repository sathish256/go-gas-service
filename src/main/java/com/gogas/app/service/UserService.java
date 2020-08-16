package com.gogas.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gogas.app.model.User;
import com.gogas.app.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public User getUser(String uid) {
		return userRepository.findById(uid).orElseThrow(() -> new RuntimeException("UserNotFound"));
	}

	public User addUser(User user) {
		return userRepository.save(user);
	}

}
