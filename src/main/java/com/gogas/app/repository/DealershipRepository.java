package com.gogas.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gogas.app.model.Dealership;

@Repository
public interface DealershipRepository extends JpaRepository<Dealership, String> {

	List<Dealership> findByName(String name);

	List<Dealership> findAll();

	List<Dealership> findByPhone(String phone);

	List<Dealership> findByCandfId(String candfId);
}