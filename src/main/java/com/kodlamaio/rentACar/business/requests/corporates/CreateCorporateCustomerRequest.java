package com.kodlamaio.rentACar.business.requests.corporates;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class CreateCorporateCustomerRequest {
	private String corporateName;
	private String taxNumber;
	private int customerNumber;
	private String email;
	private String password;
}
