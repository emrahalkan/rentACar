package com.kodlamaio.rentACar.business.concretes;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kodlamaio.rentACar.business.abstracts.CarService;
import com.kodlamaio.rentACar.business.requests.cars.CreateCarRequest;
import com.kodlamaio.rentACar.business.requests.cars.DeleteCarRequest;
import com.kodlamaio.rentACar.business.requests.cars.UpdateCarRequest;
import com.kodlamaio.rentACar.business.responses.cars.GetCarResponse;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.ErrorResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;
import com.kodlamaio.rentACar.core.utilities.results.SuccessDataResult;
import com.kodlamaio.rentACar.core.utilities.results.SuccessResult;
import com.kodlamaio.rentACar.dataAccess.abstracts.CarRepository;
import com.kodlamaio.rentACar.entities.concretes.Brand;
import com.kodlamaio.rentACar.entities.concretes.Car;
import com.kodlamaio.rentACar.entities.concretes.Color;
@Service
public class CarManager implements CarService{
	
	private CarRepository carRepository;
	
	public CarManager(CarRepository carRepository) {
		this.carRepository = carRepository;
	}

	@Override
	public Result add(CreateCarRequest createCarRequest) {
		if(!checkIfBrandCount(createCarRequest.getBrandId())) {

			Car car = new Car();
			car.setDescription(createCarRequest.getDescription());
			car.setDailyPrice(createCarRequest.getDailyPrice());
			car.setKilometer(createCarRequest.getKilometer());
			car.setNumberPlate(createCarRequest.getNumberPlate());
			car.setState(1);
			
			Brand brand = new Brand();
			brand.setId(createCarRequest.getBrandId());
			car.setBrand(brand);
			
			Color color = new Color();
			color.setId(createCarRequest.getColorId());
			car.setColor(color);

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
		Car car = carRepository.findById(updateCarRequest.getId());
		car.setDescription(updateCarRequest.getDescription());
		car.setDailyPrice(updateCarRequest.getDailyPrice());
		car.setNumberPlate(updateCarRequest.getNumberPlate());
		car.setKilometer(updateCarRequest.getKilometer());
		
		Brand brand = new Brand();
		brand.setId(updateCarRequest.getBrandId());
		car.setBrand(brand);
		
		Color color = new Color();
		color.setId(updateCarRequest.getColorId());
		car.setColor(color);
		
		this.carRepository.save(car);
		
		return new SuccessResult("CAR.UPDATED");
	}

	@Override
	public DataResult<Car> getById(GetCarResponse getCarResponse) {
		return new SuccessDataResult<Car>(this.carRepository.findById(getCarResponse.getId()));
	}
	
	@Override
	public DataResult<List<Car>> getAll() {
		return new SuccessDataResult<List<Car>>(this.carRepository.findAll());
	}
	
	public boolean checkIfBrandCount(int brandId) {	
		List<Car> result = carRepository.getByBrandId(brandId);
		if (result.size() < 5) {
			return false;
		}
		return true;
	}




	
	
}
