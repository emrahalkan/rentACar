package com.kodlamaio.rentACar.business.requests.cars;

import javax.validation.constraints.Min;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCarRequest{
	
	//@NotEmpty
	//@NotBlank // sayısal ifadelerde kullanılmaz
	//@Size(min=2, max = 50)
	private String description;
	
	//@NotEmpty // rakamlarda kullanılamaz
	@Min(10)
	//@Pattern() //regexp
	private double dailyPrice;
	private int brandId;
	private int colorId;
	private String numberPlate;
	private int kilometer;
	private int cityId;
	private int minFindexScore;
}
