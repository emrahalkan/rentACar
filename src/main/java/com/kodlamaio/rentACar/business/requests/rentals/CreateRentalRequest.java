package com.kodlamaio.rentACar.business.requests.rentals;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateRentalRequest {
	private Date pickupDate;
	private Date returnDate;
	private int carId;
}
