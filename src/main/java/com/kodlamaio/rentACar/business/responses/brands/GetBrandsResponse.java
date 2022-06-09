package com.kodlamaio.rentACar.business.responses.brands;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetBrandsResponse {
	private int id;
	private String name;
}
