package com.kodlamaio.rentACar.dataAccess.abstracts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kodlamaio.rentACar.entities.concretes.AdditionalItem;
import com.kodlamaio.rentACar.entities.concretes.Invoice;

public interface InvoiceRepository extends JpaRepository<Invoice, Integer>{
	List<AdditionalItem> getByRentalId(int id);
	Invoice findById(int id);
}
