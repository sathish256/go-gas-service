package com.connectgas.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.connectgas.app.model.common.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, String> {

}