package com.kodlamaio.rentACar.api.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kodlamaio.rentACar.business.abstracts.BrandService;
import com.kodlamaio.rentACar.business.requests.brands.CreateBrandRequest;
import com.kodlamaio.rentACar.business.requests.brands.DeleteBrandRequest;
import com.kodlamaio.rentACar.business.requests.brands.UpdateBrandRequest;
import com.kodlamaio.rentACar.business.responses.brands.GetBrandResponse;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;
import com.kodlamaio.rentACar.entities.concretes.Brand;

//localhost:8080/api/brands/

@RestController  // bu classın controller yani api olduğunu anlıyor.
@RequestMapping("/api/brands")
public class BrandsController {
	
	private BrandService brandService;
	
	public BrandsController(BrandService brandService) {
		this.brandService = brandService;
	}

	@GetMapping("/sayhello") //endpoint
	public String sayHello() {
		return "Hello Spring";
	}
	
	@PostMapping("/add")
	public Result add(@RequestBody CreateBrandRequest createBrandRequest) {
		return this.brandService.add(createBrandRequest);
	}
	
	@GetMapping("/getAll")
	public DataResult<List<Brand>> getAll(){
		return brandService.getAll();
	}
	
	@GetMapping("/getById")
	public DataResult<Brand> getById(@RequestBody GetBrandResponse readBrandResponse){
		return this.brandService.getById(readBrandResponse);
	}
	
	@PostMapping("/delete")
	public Result delete(@RequestBody DeleteBrandRequest deleteBrandRequest) {
		return this.brandService.delete(deleteBrandRequest);
	}
	
	@PostMapping("/update")
	public Result update(@RequestBody UpdateBrandRequest updateBrandRequest) {
		return this.brandService.update(updateBrandRequest);
	}

}
