package com.kodlamaio.rentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.kodlamaio.rentACar.business.abstracts.CarService;
import com.kodlamaio.rentACar.business.requests.cars.CreateCarRequest;
import com.kodlamaio.rentACar.business.requests.cars.DeleteCarRequest;
import com.kodlamaio.rentACar.business.requests.cars.UpdateCarRequest;
import com.kodlamaio.rentACar.business.responses.cars.GetAllCarsResponse;
import com.kodlamaio.rentACar.business.responses.cars.GetCarResponse;
import com.kodlamaio.rentACar.core.utilities.exceptions.BusinessException;
import com.kodlamaio.rentACar.core.utilities.mapping.ModelMapperService;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;
import com.kodlamaio.rentACar.core.utilities.results.SuccessDataResult;
import com.kodlamaio.rentACar.core.utilities.results.SuccessResult;
import com.kodlamaio.rentACar.dataAccess.abstracts.CarRepository;
import com.kodlamaio.rentACar.entities.concretes.Car;

@Service
public class CarManager implements CarService {

	private CarRepository carRepository;
	private ModelMapperService modelMapperService;

	public CarManager(CarRepository carRepository, ModelMapperService modelMapperService) {
		this.carRepository = carRepository;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public Result add(CreateCarRequest createCarRequest) {
		checkBrandCount(createCarRequest.getBrandId());
		Car car = this.modelMapperService.forRequest().map(createCarRequest, Car.class);
		car.setState(1);
		this.carRepository.save(car);
		return new SuccessResult("CAR.ADDED");

	}

	@Override
	public Result delete(DeleteCarRequest deleteCarRequest) {
		checkIfCarExists(deleteCarRequest.getId());
		this.carRepository.deleteById(deleteCarRequest.getId());
		return new SuccessResult("CAR.DELETED");
	}

	@Override
	public Result update(UpdateCarRequest updateCarRequest) {
		checkIfCarExists(updateCarRequest.getId());
		Car car = this.carRepository.findById(updateCarRequest.getId()).get();
		checkBrandNameFromUpdate(car);
		car = this.modelMapperService.forRequest().map(updateCarRequest, Car.class);
		this.carRepository.save(car);

		return new SuccessResult("CAR.UPDATED");
	}

	@Override
	public DataResult<GetCarResponse> getById(int id) {
		checkIfCarExists(id);
		Car car = this.carRepository.findById(id).get();

		GetCarResponse response = this.modelMapperService.forResponse().map(car, GetCarResponse.class);
		return new SuccessDataResult<GetCarResponse>(response);
	}

	@Override
	public DataResult<List<GetAllCarsResponse>> getAll() {
		List<Car> cars = this.carRepository.findAll();

		List<GetAllCarsResponse> response = cars.stream()
				.map(car -> this.modelMapperService.forResponse().map(car, GetAllCarsResponse.class))
				.collect(Collectors.toList());
		return new SuccessDataResult<List<GetAllCarsResponse>>(response);
	}
	
	private void checkBrandNameFromUpdate(Car newCar) {
		Car oldCar = this.carRepository.findById(newCar.getId()).get();
		if (oldCar.getBrand().getId() != newCar.getBrand().getId()) {
			checkBrandCount(newCar.getBrand().getId());
		}
	}

	private void checkBrandCount(int brandId) {
		List<Car> cars = this.carRepository.getByBrandId(brandId);
		if (cars.size() >= 5) {
			throw new BusinessException("THERE.CANNOT.BE.MORE.THAN.5.CAR.THE.SAME.BRAND");
		}
	}
	
	private void checkIfCarExists(int carId) {
		Car car = this.carRepository.findById(carId).get();
		if (car == null) {
			throw new BusinessException("THERE.IS.NOT.CAR");
		}
	}
}
