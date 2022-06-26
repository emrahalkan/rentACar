package com.kodlamaio.rentACar.business.requests.individuals;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteIndividualCustomerRequest {
	private int individualCustomerId;
}
