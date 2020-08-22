package com.gogas.app.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.gogas.app.model.User;
import com.gogas.app.model.dto.CredentialsDTO;
import com.gogas.app.repository.AddressRepository;
import com.gogas.app.repository.IdentityProofRepository;
import com.gogas.app.repository.UserRepository;
import com.gogas.app.utils.PasswordUtil;
import com.gogas.app.utils.SMSUtil;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AddressRepository addressRepository;
	
	@Autowired
	private IdentityProofRepository identityProofRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public User getUser(String uid) {
		return userRepository.findById(uid).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
				"User id " + uid + " does not exists in the system"));
	}

	@Transactional
	public User addUser(User user) {

		if (userRepository.existsByPhone(user.getPhone()))
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"User id " + user.getId() + " already exists in the system");
		User savedUser = null;
		try {
			String generatedPwd = PasswordUtil.generateRandomPassword();
			user.setPassword(bCryptPasswordEncoder.encode(generatedPwd));
			user.setCreatedAt(LocalDateTime.now());
			user.setLastmodifiedAt(LocalDateTime.now());
			user.setAddress(addressRepository.save(user.getAddress()));
			user.setIdentityProof(identityProofRepository.save(user.getIdentityProof()));
			savedUser = userRepository.save(user);

			SMSUtil.sendSMS(user.getPhone(), "GoGas - NewUser login Password : " + generatedPwd);

		} catch (Exception pe) {
			pe.printStackTrace();
			if (pe.getLocalizedMessage().contains("constraint"))
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
						"Contact number " + user.getPhone() + " already exists in the system");
		}

		return savedUser;
	}

	@Transactional
	public User updateUser(User user) {

		User dbUser = Optional.ofNullable(userRepository.findByPhone(user.getPhone()))
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
						"User id " + user.getPhone() + " does not exists in the system"));

		User savedUser = null;
		try {
			user.setPassword(dbUser.getPassword());
			user.setLastmodifiedAt(LocalDateTime.now());
			user.setAddress(addressRepository.save(user.getAddress()));
			user.setIdentityProof(identityProofRepository.save(user.getIdentityProof()));
			savedUser = userRepository.save(user);

		} catch (Exception pe) {
			if (pe.getLocalizedMessage().contains("Key (contact)"))
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
						"Contact number " + user.getPhone() + " already exists in the system");
		}

		return savedUser;
	}

	@Transactional
	public Map<String, String> changePassword(CredentialsDTO credentialsDTO, boolean isReset) {

		Map<String, String> response = new HashMap<>();
		response.put("status", "Password changed successfully!");

		User user = Optional.ofNullable(userRepository.findByPhone(Long.parseLong(credentialsDTO.getPhone())))
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
						"User id " + credentialsDTO.getPhone() + " does not exists in the system"));

		if (isReset) {
			String generatedPwd = PasswordUtil.generateRandomPassword();
			credentialsDTO.setNewPassword(generatedPwd);
			SMSUtil.sendSMS(user.getPhone(), "GoGas - Reset Login Password : " + generatedPwd);
			response.put("status", "Password reset successfully!");

		} else if (!bCryptPasswordEncoder.matches(credentialsDTO.getCurrentPassword(), user.getPassword())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Current password for  " + credentialsDTO.getPhone() + " does not match");
		}

		user.setPassword(bCryptPasswordEncoder.encode(credentialsDTO.getNewPassword()));
		user.setLastmodifiedAt(LocalDateTime.now());
		userRepository.save(user);

		return response;
	}

	@Transactional
	public User findUserByPhone(String name) {
		return Optional.ofNullable(userRepository.findByPhone(Long.parseLong(name)))
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
						"Could not retrive Logged in User Infomation"));
	}

}
