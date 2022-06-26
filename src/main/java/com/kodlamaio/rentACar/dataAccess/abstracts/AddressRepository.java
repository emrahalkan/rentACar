package com.kodlamaio.rentACar.dataAccess.abstracts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kodlamaio.rentACar.entities.concretes.Address;

public interface AddressRepository extends JpaRepository<Address, Integer> {
	List<Address> getByUserIdAndAddressType(int userId, int addressType);
}
