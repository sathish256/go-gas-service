package com.connectgas.app.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import com.connectgas.app.exception.EntityNotFoundException;
import com.connectgas.app.model.dto.ConnectGasResponse;
import com.connectgas.app.model.dto.CredentialsDTO;
import com.connectgas.app.model.user.User;
import com.connectgas.app.model.user.UserRole;
import com.connectgas.app.repository.AddressRepository;
import com.connectgas.app.repository.IdentityProofRepository;
import com.connectgas.app.repository.UserRepository;
import com.connectgas.app.utils.PasswordUtil;
import com.connectgas.app.utils.SMSUtil;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AddressRepository addressRepository;

	@Autowired
	private IdentityProofRepository identityProofRepository;

	@Autowired
	@Qualifier("bCryptPasswordEncoder")
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public User getUser(String uid) {
		return userRepository.findById(uid).orElseThrow(() -> new EntityNotFoundException(User.class, "id", uid));
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
			if (user.getIdentityProof() != null)
				user.setIdentityProof(identityProofRepository.save(user.getIdentityProof()));
			savedUser = userRepository.save(user);

			SMSUtil.sendSMS(Long.parseLong(user.getPhone()), "GoGas - NewUser login Password : " + generatedPwd);

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
			if (user.getIdentityProof() != null)
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
	public ConnectGasResponse changePassword(CredentialsDTO credentialsDTO, boolean isReset) {

		String message = "Password changed successfully!";

		User user = Optional.ofNullable(userRepository.findByPhone(credentialsDTO.getPhone()))
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
						"User id " + credentialsDTO.getPhone() + " does not exists in the system"));

		if (isReset) {
			String generatedPwd = PasswordUtil.generateRandomPassword();
			credentialsDTO.setNewPassword(generatedPwd);
			SMSUtil.sendSMS(Long.parseLong(user.getPhone()), "GoGas - Reset Login Password : " + generatedPwd);
			message = "Password changed successfully!";

		} else if (!bCryptPasswordEncoder.matches(credentialsDTO.getCurrentPassword(), user.getPassword())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Current password for  " + credentialsDTO.getPhone() + " does not match");
		}

		user.setPassword(bCryptPasswordEncoder.encode(credentialsDTO.getNewPassword()));
		user.setLastmodifiedAt(LocalDateTime.now());
		userRepository.save(user);

		return new ConnectGasResponse(HttpStatus.OK, message);
	}

	@Transactional
	public User findUserByPhone(String phone) {
		return Optional.ofNullable(userRepository.findByPhone(phone))
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
						"Could not retrive Logged in User Infomation"));
	}

	public List<User> findAll() {
		return userRepository.findAll();
	}

	public User deleteUser(String phone) {

		User user = Optional.ofNullable(userRepository.findByPhone(phone))
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
						"Could not find the User Infomation in the system"));

		userRepository.deleteById(user.getId());
		return user;
	}

	public List<User> search(String phone) {

		User user = userRepository.findByPhone(phone);
		if (user.getRole().equals(UserRole.ADMIN)) {
			userRepository.findAll();
		} else if (user.getRole().equals(UserRole.CANDF))
			return userRepository.findByCandfId(user.getCandfId());
		else if (user.getRole().equals(UserRole.DEALER))
			return userRepository.findByDealershipId(user.getDealershipId());

		return userRepository.findAll();
	}

	public List<User> search(String candfId, String dealerId) {

		if (StringUtils.hasText(candfId))
			return userRepository.findByCandfId(candfId);
		else if (StringUtils.hasText(dealerId))
			return userRepository.findByDealershipId(dealerId);

		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User Search requires either candfId or dealerId");
	}

}
