package com.connectgas.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.connectgas.app.model.user.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

	List<User> findByFirstName(String FirstName);

	List<User> findAll();

	List<User> findByCandfId(String candfId);

	List<User> findByDealershipId(String dealershipId);

	User findByPhone(String phone);

	boolean existsByPhone(String phone);
}