package com.kodlamaio.rentACar.business.responses.individuals;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetIndividualCustomerResponse {
	private String firstName;
	private String lastName;
	private String nationality;
	private LocalDate birthDate;
	private String email;
	private String password;
}
