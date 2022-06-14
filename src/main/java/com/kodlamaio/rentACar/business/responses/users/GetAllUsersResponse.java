package com.kodlamaio.rentACar.business.responses.users;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetAllUsersResponse {
	private int id;
	private String firstName;
	private String lastName;
	private String nationality;
	private String email;
	private String password;
}
