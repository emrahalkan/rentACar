package com.kodlamaio.rentACar.business.responses.cars;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetCarResponse {
	private String description;
	private double dailyPrice;
	private int brandId;
	private int colorId;
	private String numberPlate;
	private int kilometer;
	private String cityId;
}
