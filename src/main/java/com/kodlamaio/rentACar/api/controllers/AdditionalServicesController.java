package com.kodlamaio.rentACar.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kodlamaio.rentACar.business.abstracts.AdditionalServiceService;
import com.kodlamaio.rentACar.business.requests.additionalServices.CreateAdditionalServiceRequest;
import com.kodlamaio.rentACar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/additionalService")
public class AdditionalServicesController {
	
	@Autowired
	private AdditionalServiceService additionalServiceService;

	@PostMapping("/add")
	public Result add(@RequestBody CreateAdditionalServiceRequest createAdditionalServiceRequest) {
		return this.additionalServiceService.add(createAdditionalServiceRequest);
	}

}
