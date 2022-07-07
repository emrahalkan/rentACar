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
import com.kodlamaio.rentACar.dataAccess.abstracts.BrandRepository;
import com.kodlamaio.rentACar.dataAccess.abstracts.CarRepository;
import com.kodlamaio.rentACar.dataAccess.abstracts.CityRepository;
import com.kodlamaio.rentACar.dataAccess.abstracts.ColorRepository;
import com.kodlamaio.rentACar.entities.concretes.Brand;
import com.kodlamaio.rentACar.entities.concretes.Car;
import com.kodlamaio.rentACar.entities.concretes.City;
import com.kodlamaio.rentACar.entities.concretes.Color;

@Service
public class CarManager implements CarService {

	private CarRepository carRepository;
	private ModelMapperService modelMapperService;
	private CityRepository cityRepository;
	private BrandRepository brandRepository;
	private ColorRepository colorRepository;

	

	public CarManager(CarRepository carRepository, ModelMapperService modelMapperService, CityRepository cityRepository,
			BrandRepository brandRepository, ColorRepository colorRepository) {
		this.carRepository = carRepository;
		this.modelMapperService = modelMapperService;
		this.cityRepository = cityRepository;
		this.brandRepository = brandRepository;
		this.colorRepository = colorRepository;
	}

	@Override
	public Result add(CreateCarRequest createCarRequest) {
		checkBrandCount(createCarRequest.getBrandId());
		checkCarNumberPlate(createCarRequest.getNumberPlate());
		checkColorExists(createCarRequest.getColorId());
		checkCityExists(createCarRequest.getCityId());
		checkBrandExists(createCarRequest.getBrandId());
		//Car car = this.modelMapperService.forRequest().map(createCarRequest, Car.class);
		Color color = Color.builder().id(createCarRequest.getColorId()).build();
		Brand brand = Brand.builder().id(createCarRequest.getBrandId()).build();
		City city = City.builder().id(createCarRequest.getCityId()).build();
		Car car = Car.builder().dailyPrice(createCarRequest.getDailyPrice())
				.kilometer(createCarRequest.getKilometer())
				.description(createCarRequest.getDescription())
				.numberPlate(createCarRequest.getNumberPlate())
				.minFindexScore(createCarRequest.getMinFindexScore())
				.color(color)
				.brand(brand)
				.city(city)
				.build();
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
		checkColorExists(updateCarRequest.getColorId());
		checkCityExists(updateCarRequest.getCityId());
		
		Car car = this.carRepository.findById(updateCarRequest.getId());
		checkBrandNameFromUpdate(car);
		checkCarNumberPlateFromUpdate(car);
		car = this.modelMapperService.forRequest().map(updateCarRequest, Car.class);
		this.carRepository.save(car);

		return new SuccessResult("CAR.UPDATED");
	}

	@Override
	public DataResult<GetCarResponse> getById(int id) {
		checkIfCarExists(id);
		Car car = this.carRepository.findById(id);

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
		Car oldCar = this.carRepository.findById(newCar.getId());
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
		Car car = this.carRepository.findById(carId);
		if (car == null) {
			throw new BusinessException("THERE.IS.NOT.CAR");
		}
	}
	
	private void checkColorExists(int colorId) {
		Color color = this.colorRepository.findById(colorId);
		if (color == null) {
			throw new BusinessException("THERE.IS.NOT.COLOR");
		}
	}
	
	private void checkCityExists(int cityId) {
		City city = this.cityRepository.findById(cityId);
		if (city == null) {
			throw new BusinessException("THERE.IS.NOT.CITY");
		}
	}
	
	private void checkBrandExists(int brandId) {
		Brand brand = this.brandRepository.findById(brandId);
		if (brand == null) {
			throw new BusinessException("THERE.IS.NOT.BRAND");
		}
	}
	
	private void checkCarNumberPlate(String numberPlate) {
		Car car = this.carRepository.findByNumberPlate(numberPlate);
		if (car != null) {
			throw new BusinessException("CAR.ALREADY.EXISTS");
		}
	}
	
	private void checkCarNumberPlateFromUpdate(Car newCar) {
		Car oldCar = this.carRepository.findById(newCar.getId());
		if (oldCar.getNumberPlate() != (newCar.getNumberPlate())) {
			checkCarNumberPlate(newCar.getNumberPlate());
		}
	}
}
