package com.kodlamaio.rentACar.dataAccess.abstracts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kodlamaio.rentACar.entities.concretes.AdditionalService;

public interface AdditionalServiceRepository extends JpaRepository<AdditionalService, Integer> {
	List<AdditionalService> getByRentalId(int id);
}
