package com.kodlamaio.rentACar.api.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kodlamaio.rentACar.business.abstracts.ColorService;
import com.kodlamaio.rentACar.business.requests.colors.CreateColorRequest;
import com.kodlamaio.rentACar.business.requests.colors.DeleteColorRequest;
import com.kodlamaio.rentACar.business.requests.colors.UpdateColorRequest;
import com.kodlamaio.rentACar.business.responses.colors.GetColorResponse;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;
import com.kodlamaio.rentACar.core.utilities.results.SuccessResult;
import com.kodlamaio.rentACar.entities.concretes.Color;

@RestController
@RequestMapping("/api/colors")
public class ColorsController {
	
	private ColorService colorService;
	
	public ColorsController(ColorService colorService) {
		this.colorService = colorService;
	}

	@GetMapping("/saycolor") //endpoint
	public String sayColor() {
		return "Hello Color";
	}
	
	@PostMapping("/add")
	public Result add(@RequestBody CreateColorRequest createColorRequest) {
		this.colorService.add(createColorRequest);
		return new SuccessResult("COLOR.ADDEDD");
	}
	
	@PostMapping("delete")
	public Result delete(@RequestBody DeleteColorRequest deleteColorRequest) {
		this.colorService.delete(deleteColorRequest);
		return new SuccessResult("COLOR.DELETED");

	}
	
	@PostMapping("/update")
	public Result update(@RequestBody UpdateColorRequest updateColorRequest) {
		this.colorService.update(updateColorRequest);
		return new SuccessResult("COLOR.UPDATED");
	}
	
	@GetMapping("/getById")
	public DataResult<Color> getById(@RequestBody GetColorResponse getColorResponse){
		return this.colorService.getById(getColorResponse);
	}
	
	@GetMapping("/getAll")
	public DataResult<List<Color>> getAll(){
		return colorService.getAll();
	}

}
