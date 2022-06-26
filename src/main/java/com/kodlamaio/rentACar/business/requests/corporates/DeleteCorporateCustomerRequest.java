package com.kodlamaio.rentACar.business.requests.corporates;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteCorporateCustomerRequest {
	private int corporateCustomerId;
}