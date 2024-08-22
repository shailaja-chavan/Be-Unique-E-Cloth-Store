package com.clothstore.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.clothstore.model.Address;

@Repository
public interface AddressDao extends JpaRepository<Address, Integer> {

}
