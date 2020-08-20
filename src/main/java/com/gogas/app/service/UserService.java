package com.gogas.app.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.gogas.app.model.User;
import com.gogas.app.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public User getUser(String uid) {
		return userRepository.findById(uid).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
				"User id " + uid + " does not exists in the system"));
	}

	public User addUser(User user) {

		if (userRepository.existsById(user.getUid()))
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"User id " + user.getUid() + " already exists in the system");
		User savedUser = null;
		try {
			user.setCreateat(LocalDateTime.now());
			user.setLastmodifiedat(LocalDateTime.now());
			savedUser = userRepository.save(user);

		} catch (Exception pe) {
			if (pe.getLocalizedMessage().contains("constraint"))
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
						"Contact number " + user.getPhone() + " already exists in the system");
		}

		return savedUser;
	}

	public User updateUser(User user) {

		if (!userRepository.existsById(user.getUid()))
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"User id " + user.getUid() + " does not exists in the system");
		User savedUser = null;
		try {
			user.setLastmodifiedat(LocalDateTime.now());
			savedUser = userRepository.save(user);

		} catch (Exception pe) {
			if (pe.getLocalizedMessage().contains("Key (contact)"))
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
						"Contact number " + user.getPhone() + " already exists in the system");
		}

		return savedUser;
	}

}
