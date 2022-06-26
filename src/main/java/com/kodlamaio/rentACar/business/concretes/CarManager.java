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
import com.kodlamaio.rentACar.core.utilities.mapping.ModelMapperService;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.ErrorResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;
import com.kodlamaio.rentACar.core.utilities.results.SuccessDataResult;
import com.kodlamaio.rentACar.core.utilities.results.SuccessResult;
import com.kodlamaio.rentACar.dataAccess.abstracts.CarRepository;
import com.kodlamaio.rentACar.entities.concretes.Car;
@Service
public class CarManager implements CarService{
	
	private CarRepository carRepository;
	private ModelMapperService modelMapperService;
	
	public CarManager(CarRepository carRepository, ModelMapperService modelMapperService) {
		this.carRepository = carRepository;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public Result add(CreateCarRequest createCarRequest) {
		if(!checkIfBrandCount(createCarRequest.getBrandId())) {

			Car car = this.modelMapperService.forRequest().map(createCarRequest, Car.class);
			car.setState(1);

			this.carRepository.save(car);
			return new SuccessResult("CAR.ADDED");
		}
		else {
			return new ErrorResult("CAR.NOT.ADDED");
		}
	}
	
	@Override
	public Result delete(DeleteCarRequest deleteCarRequest) {
		this.carRepository.deleteById(deleteCarRequest.getId());
		return new SuccessResult("CAR.DELETED");
	}

	@Override
	public Result update(UpdateCarRequest updateCarRequest) {
		Car car = this.modelMapperService.forRequest().map(updateCarRequest, Car.class);
		this.carRepository.save(car);
		
		return new SuccessResult("CAR.UPDATED");
	}

	@Override
	public DataResult<GetCarResponse> getById(int id) {
		Car brand = this.carRepository.findById(id).get();
		
		GetCarResponse response = this.modelMapperService.forResponse().map(brand, GetCarResponse.class);
		return new SuccessDataResult<GetCarResponse>(response);
	}
	
	@Override
	public DataResult<List<GetAllCarsResponse>> getAll() {
		List<Car> cars = this.carRepository.findAll();
		
		List<GetAllCarsResponse> response =
				cars.stream().map(car->this.modelMapperService.forResponse()
						.map(car, GetAllCarsResponse.class)).collect(Collectors.toList());
		return new SuccessDataResult<List<GetAllCarsResponse>>(response);
	}
	
	public boolean checkIfBrandCount(int brandId) {	
		List<Car> result = this.carRepository.getByBrandId(brandId);
		if (result.size() < 5) {
			return false;
		}
		return true;
	}

//Brandleri tek tek dolaş, her bir brand için modelMaperService çalıştır


	
	
}
