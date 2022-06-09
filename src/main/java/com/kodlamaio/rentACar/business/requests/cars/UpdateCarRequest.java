package com.kodlamaio.rentACar.business.requests.cars;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCarRequest {
	private int id;
	private String description;
	private int dailyPrice;
	private int brandId;
	private int colorId;
	private int kilometer;
	private String numberPlate;
}
