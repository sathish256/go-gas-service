package com.connectgas.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.connectgas.app.model.customer.Organization;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, String> {

}