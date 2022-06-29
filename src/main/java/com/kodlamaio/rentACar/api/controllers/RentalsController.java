package com.kodlamaio.rentACar.api.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kodlamaio.rentACar.business.abstracts.RentalService;
import com.kodlamaio.rentACar.business.requests.rentals.CreateRentalRequest;
import com.kodlamaio.rentACar.business.requests.rentals.DeleteRentalRequest;
import com.kodlamaio.rentACar.business.requests.rentals.UpdateRentalRequest;
import com.kodlamaio.rentACar.business.responses.rentals.GetAllRentalsResponse;
import com.kodlamaio.rentACar.business.responses.rentals.GetRentalResponse;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;

@RestController
@RequestMapping("api/rentals")
public class RentalsController {
	private RentalService rentalService;

	public RentalsController(RentalService rentalService) {
		this.rentalService = rentalService;
	}
	
	@PostMapping("/addIndividualCustomer")
	public Result addIndividual(@RequestBody CreateRentalRequest createRentalRequest) {
		return this.rentalService.addIndividualCustormer(createRentalRequest);
	}
	
	@PostMapping("/addCorporateCustomer")
	public Result addCorporate(@RequestBody CreateRentalRequest createRentalRequest) {
		return this.rentalService.addCorporateCustormer(createRentalRequest);
	}
	
	@PostMapping("/delete")
	public Result delete(@RequestBody DeleteRentalRequest deleteRentalRequest) {
		return this.rentalService.delete(deleteRentalRequest);
	}
	
	@PostMapping("/updateIndividualCustomer")
	public Result updateIndividual(@RequestBody UpdateRentalRequest updateRentalRequest) {
		return this.rentalService.updateIndividualCustomer(updateRentalRequest);
	}
	
	@PostMapping("/updateCorporateCustomer")
	public Result updateCorporate(@RequestBody UpdateRentalRequest updateRentalRequest) {
		return this.rentalService.updateCorporateCustomer(updateRentalRequest);
	}
	
	@GetMapping("/getById")
	public DataResult<GetRentalResponse> getById(@RequestParam int id) {
		return this.rentalService.getById(id);
	}
	
	@GetMapping("/getAll")
	public DataResult<List<GetAllRentalsResponse>> getAll(){
		return this.rentalService.getAll();
	}
}
