package com.kodlamaio.rentACar.business.concretes;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.kodlamaio.rentACar.business.abstracts.MaintenanceService;
import com.kodlamaio.rentACar.business.requests.maintenances.CreateMaintenanceRequest;
import com.kodlamaio.rentACar.business.requests.maintenances.DeleteMaintenanceRequest;
import com.kodlamaio.rentACar.business.requests.maintenances.UpdateMaintenanceRequest;
import com.kodlamaio.rentACar.business.responses.maintenances.GetAllMaintenancesResponse;
import com.kodlamaio.rentACar.business.responses.maintenances.GetMaintenanceResponse;
import com.kodlamaio.rentACar.core.utilities.exceptions.BusinessException;
import com.kodlamaio.rentACar.core.utilities.mapping.ModelMapperService;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;
import com.kodlamaio.rentACar.core.utilities.results.SuccessDataResult;
import com.kodlamaio.rentACar.core.utilities.results.SuccessResult;
import com.kodlamaio.rentACar.dataAccess.abstracts.CarRepository;
import com.kodlamaio.rentACar.dataAccess.abstracts.MaintenanceRepository;
import com.kodlamaio.rentACar.entities.concretes.Car;
import com.kodlamaio.rentACar.entities.concretes.Maintenance;

@Service
public class MaintenanceManager implements MaintenanceService {
	private MaintenanceRepository maintenanceRepository;
	private CarRepository carRepository;
	private ModelMapperService modelMapperService;

	public MaintenanceManager(MaintenanceRepository maintenanceRepository, ModelMapperService modelMapperService,
			CarRepository carRepository) {
		this.maintenanceRepository = maintenanceRepository;
		this.carRepository = carRepository;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public Result add(CreateMaintenanceRequest createMaintenanceRequest) {
		checkCarUnderMaintenance(createMaintenanceRequest.getCarId());
		checkDateToMaintenance(createMaintenanceRequest.getSentDate(), createMaintenanceRequest.getReturnedDate());
		checkCarIdFromMaintenance(createMaintenanceRequest.getCarId());
		Maintenance maintenance = this.modelMapperService.forRequest().map(createMaintenanceRequest, Maintenance.class);

		Car car = this.carRepository.findById(createMaintenanceRequest.getCarId());
		car.setState(2);

		maintenanceRepository.save(maintenance);
		return new SuccessResult("MAINTENANCE.ADDED");
	}

	@Override
	public Result delete(DeleteMaintenanceRequest deleteMaintenanceRequest) {
		checkIsMaintenanceExists(deleteMaintenanceRequest.getId());
		Maintenance maintenance = this.maintenanceRepository.findById(deleteMaintenanceRequest.getId());
		this.maintenanceRepository.delete(maintenance);
		return new SuccessResult("MAINTENANCE.DELETED");
	}

	@Override
	public Result update(UpdateMaintenanceRequest updateMaintenanceRequest) {
		checkIsMaintenanceExists(updateMaintenanceRequest.getId());
		Maintenance oldMaintenance = this.maintenanceRepository.findById(updateMaintenanceRequest.getId());
		Car car = this.carRepository.findById(updateMaintenanceRequest.getCarId());
		car.setState(2);
		checkCarChangeInUpdate(updateMaintenanceRequest.getId(), oldMaintenance.getId());
		Maintenance maintenance = this.modelMapperService.forRequest().map(updateMaintenanceRequest, Maintenance.class);
		maintenanceRepository.save(maintenance);
		return new SuccessResult("MAINTENANCE.UPDATED");
	}

	@Override
	public Result updateState(int carId) {
		Car car = carRepository.findById(carId);
		if (car.getState() == 1) {
			car.setState(2);
		} else {
			car.setState(1);
		}
		carRepository.save(car);
		return new SuccessResult("STATE.UPDATED");
	}

	@Override
	public DataResult<GetMaintenanceResponse> getById(int id) {
		checkIsMaintenanceExists(id);
		Maintenance maintenance = this.maintenanceRepository.findById(id);
		GetMaintenanceResponse response = this.modelMapperService.forResponse().map(maintenance,
				GetMaintenanceResponse.class);
		return new SuccessDataResult<GetMaintenanceResponse>(response);
	}

	@Override
	public DataResult<List<GetAllMaintenancesResponse>> getAll() {
		List<Maintenance> maintenances = this.maintenanceRepository.findAll();

		List<GetAllMaintenancesResponse> response = maintenances.stream().map(
				maintenance -> this.modelMapperService.forResponse().map(maintenance, GetAllMaintenancesResponse.class))
				.collect(Collectors.toList());
		return new SuccessDataResult<List<GetAllMaintenancesResponse>>(response);
	}

	private void checkCarUnderMaintenance(int carId) {
		Car car = this.carRepository.findById(carId);
		if (car.getState() == 2) {
			throw new BusinessException("CAR.IS.ALREADY.MAINTENANCE.NOW");
		}
	}

	private void checkIsMaintenanceExists(int id) {
		Maintenance maintenance = this.maintenanceRepository.findById(id);
		if (maintenance == null) {
			throw new BusinessException("THERE.IS.NOT.MAINTENANCE");
		}
	}

	private void checkDateToMaintenance(LocalDate pickupDate, LocalDate returnDate) {
		if (!pickupDate.isBefore(returnDate) || pickupDate.isBefore(LocalDate.now())) {
			throw new BusinessException("PICKUPDATE.AND.RETURNDATE.ERROR");
		}
	}

	private void checkCarIdFromMaintenance(int carId) {
		Car car = this.carRepository.findById(carId);
		if (car == null) {
			throw new BusinessException("THIS.CAR.IS.NOT.IN.CAR.REPOSITORY");
		}
	}

	private void checkCarChangeInUpdate(int newMaintenanceId, int oldMaintenanceId) {
		Maintenance newMaintenance = this.maintenanceRepository.findById(newMaintenanceId);
		Maintenance oldMaintenance = this.maintenanceRepository.findById(oldMaintenanceId);

		if (newMaintenance.getCar().getId() != oldMaintenance.getCar().getId()) {
			Car car = this.carRepository.findById(oldMaintenance.getCar().getId());
			car.setState(1);
			this.carRepository.save(car);
		}
	}

	@Override
	public Maintenance findMaintenanceById(int id) {
		Maintenance maintenance = this.maintenanceRepository.findById(id);
		if (maintenance == null) {
			throw new BusinessException("THERE.IS.NOT.THIS.MAINTENANCE");
		}
		return maintenance;
	}
}
