package com.gogas.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gogas.app.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

	List<User> findByFirstName(String FirstName);

	List<User> findAll();
}