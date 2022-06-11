package com.kodlamaio.rentACar.business.responses.colors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetAllColorsResponse {
	private int id;
	private String name;
}
