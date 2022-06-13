package com.kodlamaio.rentACar.business.requests.additionalServices;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateAdditionalServiceRequest {
	private int id;
	private int rentalId;
	private int additionalItemId;
}
