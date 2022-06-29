package com.kodlamaio.rentACar.api.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kodlamaio.rentACar.business.abstracts.AddressService;
import com.kodlamaio.rentACar.business.requests.addresses.CreateAddressRequest;
import com.kodlamaio.rentACar.business.requests.addresses.DeleteAddressRequest;
import com.kodlamaio.rentACar.business.requests.addresses.UpdateAddressRequest;
import com.kodlamaio.rentACar.business.responses.addresses.GetAddressResponse;
import com.kodlamaio.rentACar.business.responses.addresses.GetAllAddressesResponse;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/addresses")
public class AddressesController {
	
	private AddressService addressService;
	
	public AddressesController(AddressService addressService) {
		this.addressService = addressService;
	}

	@PostMapping("/addIndividualCustomer")
	public Result addIndividualCustomer(@RequestBody CreateAddressRequest createAddressRequest) {
		return this.addressService.addIndividualCustomer(createAddressRequest);
	}
	@PostMapping("/addCorporateCustomer")
	public Result addCorporateCustomer(@RequestBody CreateAddressRequest createAddressRequest) {
		return this.addressService.addCorporateCustomer(createAddressRequest);
	}
	
	
	@PostMapping("/updateIndividualCustomer")
	public Result updateIndividualCustomer(@RequestBody UpdateAddressRequest updateAddressRequest) {
		return this.addressService.updateIndividualCustomer(updateAddressRequest);
	}
	
	@PostMapping("/updateCorporateCustomer")
	public Result updateCorporateCustomer(@RequestBody UpdateAddressRequest updateAddressRequest) {
		return this.addressService.updateCorporateCustomer(updateAddressRequest);
	}
	
	@PostMapping("/delete")
	public Result delete(@RequestBody DeleteAddressRequest deleteAddressRequest) {
		return this.addressService.delete(deleteAddressRequest);
	}
	
	@GetMapping("getById")
	public DataResult<GetAddressResponse> getById(@RequestParam int id) {
		return this.addressService.getById(id);
	}
	
	@GetMapping("getAll")
	public DataResult<List<GetAllAddressesResponse>> getAll(){
		return this.addressService.getAll();
	}
	
	@GetMapping("getAllByAddressType")
	public DataResult<List<GetAllAddressesResponse>> getAllBillAddress(@RequestParam int userId, int addressType){
		return this.addressService.getAllByAddressType(userId, addressType);
	}
	
}
