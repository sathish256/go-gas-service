package com.connectgas.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.connectgas.app.model.CAndF;

@Repository
public interface CAndFRepository extends JpaRepository<CAndF, String> {

	List<CAndF> findByName(String name);

	List<CAndF> findAll();
}