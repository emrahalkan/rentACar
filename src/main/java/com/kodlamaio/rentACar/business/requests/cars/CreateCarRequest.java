package com.kodlamaio.rentACar.business.requests.cars;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCarRequest{
	
	//@NotEmpty
	//@NotBlank
	//@Size(min=2, max = 50)
	private String description;
	
	//@NotEmpty // rakamlarda kullanılamaz
	@Min(10)
	//@Pattern() //regexp
	private double dailyPrice;
	@NotBlank
	private int brandId;
	private int colorId;
	private String numberPlate;
	private int kilometer;
	private String cityId;
}
