package com.kodlamaio.rentACar.business.concretes;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kodlamaio.rentACar.business.abstracts.MaintenanceService;
import com.kodlamaio.rentACar.business.requests.maintenances.CreateMaintenanceRequest;
import com.kodlamaio.rentACar.business.requests.maintenances.DeleteMaintenanceRequest;
import com.kodlamaio.rentACar.business.requests.maintenances.UpdateMaintenanceRequest;
import com.kodlamaio.rentACar.business.responses.maintenance.GetMaintenanceResponse;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;
import com.kodlamaio.rentACar.core.utilities.results.SuccessDataResult;
import com.kodlamaio.rentACar.core.utilities.results.SuccessResult;
import com.kodlamaio.rentACar.dataAccess.abstracts.CarRepository;
import com.kodlamaio.rentACar.dataAccess.abstracts.MaintenanceRepository;
import com.kodlamaio.rentACar.entities.concretes.Car;
import com.kodlamaio.rentACar.entities.concretes.Maintenance;

@Service
public class MaintenanceManager implements MaintenanceService{
	private MaintenanceRepository maintenanceRepository;
	private CarRepository carRepository;
	
	public MaintenanceManager(MaintenanceRepository maintenanceRepository, CarRepository carRepository) {
		this.maintenanceRepository = maintenanceRepository;
		this.carRepository = carRepository;
	}

	@Override
	public Result add(CreateMaintenanceRequest createMaintenanceRequest) {
		Maintenance maintenance = new Maintenance();
		maintenance.setDateSent(createMaintenanceRequest.getDateSent());
		maintenance.setDateReturned(createMaintenanceRequest.getDateReturned());
		
		Car car = this.carRepository.findById(createMaintenanceRequest.getCarId());
		car.setId(createMaintenanceRequest.getCarId());
		car.setState(2);
		maintenance.setCar(car);
		
		maintenanceRepository.save(maintenance);
		return new SuccessResult("MAINTENANCE.ADDED");
	}

	@Override
	public Result delete(DeleteMaintenanceRequest deleteMaintenanceRequest) {
		return null;
	}

	@Override
	public Result update(UpdateMaintenanceRequest updateMaintenanceRequest) {
		Maintenance maintenance = maintenanceRepository.findById(updateMaintenanceRequest.getId());
		
		maintenance.setDateSent(updateMaintenanceRequest.getDateSent());
		
		maintenance.setDateReturned(updateMaintenanceRequest.getDateReturned());
		
		Car car = this.carRepository.findById(updateMaintenanceRequest.getCarId());
		car.setId(updateMaintenanceRequest.getCarId());
		maintenance.setCar(car);
		
		maintenanceRepository.save(maintenance);
		return new SuccessResult("MAINTENANCE.UPDATED");
	}
	
	public Result updateState(UpdateMaintenanceRequest updateMaintenanceRequest) {
		Car car = carRepository.findById(updateMaintenanceRequest.getCarId());
		if (car.getState() == 1) {
			car.setState(2);
		}
		else {
			car.setState(1);
		}
		
		carRepository.save(car);
		return new SuccessResult("STATE.UPDATED");
		
	}

	@Override
	public DataResult<Maintenance> getById(GetMaintenanceResponse getMaintenanceResponse) {
		Maintenance maintenance = this.maintenanceRepository.findById(getMaintenanceResponse.getId());
		return new SuccessDataResult<Maintenance>(maintenance);
	}

	@Override
	public DataResult<List<Maintenance>> getAll() {
		return new SuccessDataResult<List<Maintenance>>(this.maintenanceRepository.findAll());
	}
	
	

}
