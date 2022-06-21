package com.kodlamaio.rentACar.entities.concretes;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer","handler","invoices"})
@Table(name="rentals")

public class Rental {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name= "id")
	private int id;
	
	@Column(name= "pickupDate")
	private LocalDate pickupDate;
	
	@Column(name="returnDate")
	private LocalDate returnDate;
	
	@Column(name="totalDays")
	private int totalDays;
	
	@Column(name="totalPrice")
	private double totalPrice;
	
	@ManyToOne
	@JoinColumn(name="car_id")
	private Car car;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;
	
	@ManyToOne
	@JoinColumn(name="pickup_city_id", referencedColumnName = "id")
	private City pickupCityId;
	
	@ManyToOne
	@JoinColumn(name="return_city_id", referencedColumnName = "id")
	private City returnCityId;
	
	@OneToMany(mappedBy ="rental")
	List<Invoice> invoices;
}
