package com.connectgas.app.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import com.connectgas.app.exception.EntityNotFoundException;
import com.connectgas.app.model.dto.ConnectGasResponse;
import com.connectgas.app.model.dto.CredentialsDTO;
import com.connectgas.app.model.user.User;
import com.connectgas.app.model.user.UserRole;
import com.connectgas.app.repository.SimpleFirestoreRepository;
import com.connectgas.app.utils.PasswordUtil;
import com.connectgas.app.utils.SMSUtil;

@Service
public class UserService {

	@Autowired
	private SimpleFirestoreRepository<User, String> userRepository;

	@Autowired
	@Qualifier("bCryptPasswordEncoder")
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public User getUser(String uid) {
		return userRepository.fetchById(uid, getCollectionName())
				.orElseThrow(() -> new EntityNotFoundException(User.class, "id", uid));
	}

	private Class<User> getCollectionName() {
		return User.class;
	}

	public User addUser(User user) {

		if (userRepository.findAll(User.class).stream().anyMatch(u -> u.getPhone().equals(user.getPhone())))
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"User id " + user.getId() + " already exists in the system");
		User savedUser = null;
		try {
			user.setId(UUID.randomUUID().toString());
			String generatedPwd = PasswordUtil.generateRandomPassword();
			user.setPassword(bCryptPasswordEncoder.encode(generatedPwd));
			user.setCreatedAt(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
			user.setLastmodifiedAt(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
			savedUser = userRepository.save(user, getCollectionName());

			SMSUtil.sendSMS(Long.parseLong(user.getPhone()), "ConnectGas - NewUser login Password : " + generatedPwd);

		} catch (Exception pe) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, pe.getLocalizedMessage());
		}

		return savedUser;
	}

	public User updateUser(User user) {

		User dbUser = userRepository.findAll(User.class).stream().filter(u -> u.getPhone().equals(user.getPhone()))
				.findFirst().orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
						"User id " + user.getPhone() + " does not exists in the system"));

		User savedUser = null;
		try {
			user.setPassword(dbUser.getPassword());
			user.setLastmodifiedAt(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
			savedUser = userRepository.save(user, getCollectionName());

		} catch (Exception pe) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, pe.getLocalizedMessage());
		}

		return savedUser;
	}

	public ConnectGasResponse changePassword(CredentialsDTO credentialsDTO, boolean isReset) {

		String message = "Password changed successfully!";

		User user = userRepository.findAll(User.class).stream()
				.filter(u -> u.getPhone().equals(credentialsDTO.getPhone())).findFirst()
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
		user.setLastmodifiedAt(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
		userRepository.save(user, getCollectionName());

		return new ConnectGasResponse(HttpStatus.OK, message);
	}

	public User findUserByPhone(String phone) {
		return userRepository.findAll(User.class).stream().filter(u -> u.getPhone().equals(phone)).findFirst()
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
						"User id " + phone + " does not exists in the system"));
	}

	public List<User> findAll() {
		return userRepository.findAll(User.class);
	}

	public User deleteUser(String phone) {
		User user = findUserByPhone(phone);
		userRepository.deleteById(user.getId(), getCollectionName());
		return user;
	}

	public List<User> search(String phone) {

		User user = findUserByPhone(phone);
		if (user.getRole().equals(UserRole.ADMIN)) {
			userRepository.findAll(getCollectionName());
		} else if (user.getRole().equals(UserRole.CANDF))
			return userRepository.findAll(getCollectionName()).stream()
					.filter(u -> u.getCandfId().equals(user.getCandfId())).collect(Collectors.toList());
		else if (user.getRole().equals(UserRole.DEALER))
			return userRepository.findAll(getCollectionName()).stream().filter(
					u -> StringUtils.hasText(u.getDealershipId()) && u.getDealershipId().equals(user.getDealershipId()))
					.collect(Collectors.toList());

		return userRepository.findAll(getCollectionName());
	}

	public List<User> search(String candfId, String dealerId) {

		if (StringUtils.hasText(candfId))
			return userRepository.findAll(getCollectionName()).stream()
					.filter(u -> StringUtils.hasText(u.getCandfId()) && u.getCandfId().equals(candfId))
					.collect(Collectors.toList());
		else if (StringUtils.hasText(dealerId))
			return userRepository.findAll(getCollectionName()).stream()
					.filter(u -> StringUtils.hasText(u.getDealershipId()) && u.getDealershipId().equals(dealerId))
					.collect(Collectors.toList());

		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User Search requires either candfId or dealerId");
	}

}
