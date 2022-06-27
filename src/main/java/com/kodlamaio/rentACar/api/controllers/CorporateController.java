package com.kodlamaio.rentACar.api.controllers;

import java.rmi.RemoteException;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kodlamaio.rentACar.business.abstracts.CorporateCustomerService;
import com.kodlamaio.rentACar.business.requests.corporates.CreateCorporateCustomerRequest;
import com.kodlamaio.rentACar.business.requests.corporates.DeleteCorporateCustomerRequest;
import com.kodlamaio.rentACar.business.requests.corporates.UpdateCorporateCustomerRequest;
import com.kodlamaio.rentACar.business.responses.corporates.GetAllCorporateCustomerResponse;
import com.kodlamaio.rentACar.business.responses.corporates.GetCorporateCustomerResponse;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/corporates")
public class CorporateController {
private CorporateCustomerService userService;
	
	public CorporateController(CorporateCustomerService userService) {
		this.userService = userService;
	}

	@PostMapping("/add")
	public Result add(@RequestBody CreateCorporateCustomerRequest createUserRequest) throws NumberFormatException, RemoteException  {
		return this.userService.add(createUserRequest);
	}
	
	@GetMapping("/getAll")
	public DataResult<List<GetAllCorporateCustomerResponse>> getAll(){
		return userService.getAll();
	}
	
	@GetMapping("/getAllByPage")
	public DataResult<List<GetAllCorporateCustomerResponse>> getAll(@RequestParam int pageNo, int pageSize){
		return userService.getAll(pageNo, pageSize);
	}
	
	@GetMapping("/getById")
	public DataResult<GetCorporateCustomerResponse> getById(@RequestParam int id){
		return this.userService.getById(id);
	}
	
	@PostMapping("/delete")
	public Result delete(@RequestBody DeleteCorporateCustomerRequest deleteUserRequest) {
		return this.userService.delete(deleteUserRequest);
	}
	
	@PostMapping("/update")
	public Result update(@RequestBody UpdateCorporateCustomerRequest updateUserRequest) {
		return this.userService.update(updateUserRequest);
	}
}
