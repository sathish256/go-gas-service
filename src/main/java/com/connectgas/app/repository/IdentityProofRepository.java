package com.connectgas.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.connectgas.app.model.common.IdentityProof;

@Repository
public interface IdentityProofRepository extends JpaRepository<IdentityProof, String> {

}