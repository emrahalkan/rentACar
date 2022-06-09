package com.kodlamaio.rentACar.api.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kodlamaio.rentACar.business.abstracts.MaintenanceService;
import com.kodlamaio.rentACar.business.requests.maintenances.CreateMaintenanceRequest;
import com.kodlamaio.rentACar.business.requests.maintenances.DeleteMaintenanceRequest;
import com.kodlamaio.rentACar.business.requests.maintenances.UpdateMaintenanceRequest;
import com.kodlamaio.rentACar.business.responses.maintenance.GetMaintenanceResponse;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;
import com.kodlamaio.rentACar.entities.concretes.Maintenance;

@RestController
@RequestMapping("api/maintenances")
public class MaintenancesController {
	private MaintenanceService maintenanceService;
	public MaintenancesController(MaintenanceService maintenanceService) {
		this.maintenanceService = maintenanceService;
	}
	
	@PostMapping("/add")
	public Result add(@RequestBody CreateMaintenanceRequest createCarRequest) {
		return this.maintenanceService.add(createCarRequest);
	}
	
	@PostMapping("/delete")
	public Result delete(@RequestBody DeleteMaintenanceRequest createCarRequest) {
		return this.maintenanceService.delete(createCarRequest);
	}
	
	@PostMapping("/update")
	public Result update(@RequestBody UpdateMaintenanceRequest createCarRequest) {
		return this.maintenanceService.update(createCarRequest);
	}
	
	@PostMapping("/updateState")
	public Result updateState(@RequestBody UpdateMaintenanceRequest updateMaintenanceRequest) {
		return this.maintenanceService.updateState(updateMaintenanceRequest);
	}
	
	@GetMapping("/getById")
	public Result getById(@RequestBody GetMaintenanceResponse getMaintenanceResponse) {
		return this.maintenanceService.getById(getMaintenanceResponse);
	}
	
	@GetMapping("/getAll")
	public DataResult<List<Maintenance>> getAll(){
		return this.maintenanceService.getAll();
	}
}
