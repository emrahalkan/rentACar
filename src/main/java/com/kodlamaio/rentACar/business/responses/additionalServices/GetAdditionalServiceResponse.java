package com.kodlamaio.rentACar.business.responses.additionalServices;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetAdditionalServiceResponse {
	private int additionalItemId;
	private int rentalId;
	private LocalDate pickupDate;
	private LocalDate returnDate;
}
