package com.kodlamaio.rentACar.business.responses.additionalServices;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetAdditionalServiceResponse {
	private int totalDays;
	private double totalPrice;
	private int additionalItemId;
	private int rentalId;
}
