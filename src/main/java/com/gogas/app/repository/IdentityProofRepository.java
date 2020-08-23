package com.gogas.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gogas.app.model.common.IdentityProof;

@Repository
public interface IdentityProofRepository extends JpaRepository<IdentityProof, String> {

}