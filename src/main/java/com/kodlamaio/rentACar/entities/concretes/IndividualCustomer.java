package com.kodlamaio.rentACar.entities.concretes;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode(callSuper = false)
@Table(name = "individual_customers")
@PrimaryKeyJoinColumn(name = "individual_customer_id", referencedColumnName = "customer_id")

public class IndividualCustomer extends Customer {
	
	@Column(name ="individual_customer_id", insertable = false, updatable = false)
	private int individualCustomerId;
	
	@Column(name="first_name")
	private String firstName;
	
	@Column(name="last_name")
	private String lastName;
	
	@Column(name ="nationality")
	private String nationality;
	
	@Column(name = "birth_date")
	private LocalDate birthDate;
}
