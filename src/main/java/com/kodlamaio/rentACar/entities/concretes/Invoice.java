package com.kodlamaio.rentACar.entities.concretes;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="invoices")

public class Invoice {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	
	@Column(name ="invoice_number")
	private int invoiceNumber;
	
	@Column(name="currently_date")
	private LocalDate currentDate;
	
	@Column(name = "total_price")
	private double totalPrice;
	
	@Column(name = "deleted")
	private int deleted;
	
	@ManyToOne
	@JoinColumn(name ="rental_id")
	private Rental rental;
	
}
