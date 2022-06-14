package com.kodlamaio.rentACar.business.requests.users;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRequest {
	private String firstName;
	private String lastName;
	private String nationality;
	private LocalDate birtDate;
	private String email;
	private String password;
}
