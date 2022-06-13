package com.kodlamaio.rentACar.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kodlamaio.rentACar.business.abstracts.AdditionalItemService;
import com.kodlamaio.rentACar.business.requests.additionalItems.CreateAdditionalItemRequest;
import com.kodlamaio.rentACar.business.requests.additionalItems.DeleteAdditionalItemRequest;
import com.kodlamaio.rentACar.business.requests.additionalItems.UpdateAdditionalItemRequest;
import com.kodlamaio.rentACar.business.responses.additionalItems.GetAdditionalItemResponse;
import com.kodlamaio.rentACar.business.responses.additionalItems.GetAllAdditionalItemsResponse;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/additionalItems")
public class AdditionalItemsController {
	
	@Autowired
	private AdditionalItemService additionalItemService;
	
	@PostMapping("/add")
	public Result add(@RequestBody CreateAdditionalItemRequest creAdditionalItemRequest) {
		return this.additionalItemService.add(creAdditionalItemRequest);
	}
	
	@PostMapping("/delete")
	public Result delete(@RequestBody DeleteAdditionalItemRequest deleteAdditionalItemRequest) {
		return this.additionalItemService.delete(deleteAdditionalItemRequest);
	}
	
	@PostMapping("/update")
	public Result update(@RequestBody UpdateAdditionalItemRequest updateAdditionalItemRequest) {
		return this.additionalItemService.update(updateAdditionalItemRequest);
	}
	
	@GetMapping("/getById")
	public DataResult<GetAdditionalItemResponse> getById(int id) {
		return this.additionalItemService.getById(id);
	}
	
	@GetMapping("/getAll")
	public DataResult<List<GetAllAdditionalItemsResponse>> getAll(){
		return this.additionalItemService.getAll();
	}
	
}
