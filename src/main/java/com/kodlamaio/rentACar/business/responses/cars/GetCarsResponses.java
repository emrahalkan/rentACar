package com.kodlamaio.rentACar.business.responses.cars;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetCarsResponses {
	
	private int id;
	private int dailyPrice;
	private String description;
	private int brandId;
	private int colorId;
}
