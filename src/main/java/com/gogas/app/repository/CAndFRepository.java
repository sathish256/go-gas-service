package com.gogas.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gogas.app.model.CAndF;

@Repository
public interface CAndFRepository extends JpaRepository<CAndF, String> {

	List<CAndF> findByName(String name);

	List<CAndF> findAll();
}