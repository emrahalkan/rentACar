package com.kodlamaio.rentACar.business.concretes;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.kodlamaio.rentACar.business.abstracts.RentalService;
import com.kodlamaio.rentACar.business.requests.rentals.CreateRentalRequest;
import com.kodlamaio.rentACar.business.requests.rentals.DeleteRentalRequest;
import com.kodlamaio.rentACar.business.requests.rentals.UpdateRentalRequest;
import com.kodlamaio.rentACar.business.responses.rentals.GetAllRentalsResponse;
import com.kodlamaio.rentACar.business.responses.rentals.GetRentalResponse;
import com.kodlamaio.rentACar.core.utilities.mapping.ModelMapperService;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.ErrorResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;
import com.kodlamaio.rentACar.core.utilities.results.SuccessDataResult;
import com.kodlamaio.rentACar.core.utilities.results.SuccessResult;
import com.kodlamaio.rentACar.dataAccess.abstracts.CarRepository;
import com.kodlamaio.rentACar.dataAccess.abstracts.RentalRepository;
import com.kodlamaio.rentACar.entities.concretes.Car;
import com.kodlamaio.rentACar.entities.concretes.Rental;

@Service
public class RentalManager implements RentalService{
	
	private RentalRepository rentalRepository;
	private CarRepository carRepository;
	private ModelMapperService modelMapperService;
	
	public RentalManager(RentalRepository rentalRepository, CarRepository carRepository, ModelMapperService modelMapperService) {
		this.rentalRepository = rentalRepository;
		this.carRepository = carRepository;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public Result add(CreateRentalRequest createRentalRequest) {
		if (checkIfCarState(createRentalRequest.getCarId())) {
			Rental rental = this.modelMapperService.forRequest().map(createRentalRequest, Rental.class);
			long diffDate = (createRentalRequest.getReturnDate().getTime() - (createRentalRequest.getPickupDate().getTime()));
			diffDate = (TimeUnit.DAYS.convert(diffDate, TimeUnit.MILLISECONDS));
			if (checkDateToRentACar(createRentalRequest.getPickupDate(), createRentalRequest.getReturnDate())) {
				rental.setTotalDays((int)diffDate);
				
				Car car = this.carRepository.findById(createRentalRequest.getCarId());
				car.setState(3);
				double totalPrice = car.getDailyPrice() * diffDate;
				rental.setTotalPrice(totalPrice);
				
				rentalRepository.save(rental);
				return new SuccessResult("RENTAL.ADDED");
			}
			else {
				return new ErrorResult("Toplam gün sayısı 0 dan düşük olamaz");
			}
		}
		else {
			return new ErrorResult("Araba avaible olmalıdır");
		}
	}

	@Override
	public Result delete(DeleteRentalRequest deleteRentalRequest) {
		Rental rental = this.rentalRepository.findById(deleteRentalRequest.getId());
		this.rentalRepository.delete(rental);
		return new SuccessResult("RENTAL.DELETED");
	}

	@Override
	public Result update(UpdateRentalRequest updateRentalRequest) {
		Rental rental = this.modelMapperService.forRequest().map(updateRentalRequest, Rental.class);
		long diffDate = (updateRentalRequest.getReturnDate().getTime() - (updateRentalRequest.getPickupDate().getTime()));
		diffDate = (TimeUnit.DAYS.convert(diffDate, TimeUnit.MILLISECONDS));
		if (checkDateToRentACar(updateRentalRequest.getPickupDate(), updateRentalRequest.getReturnDate())) {
			rental.setTotalDays((int)diffDate);
			
			Car car = this.carRepository.findById(updateRentalRequest.getCarId());
			car.setState(3);
			double totalPrice = car.getDailyPrice() * diffDate;
			rental.setTotalPrice(totalPrice);
			
			rentalRepository.save(rental);
			return new SuccessResult("RENTAL.UPDATED");
		}
		else {
			return new ErrorResult("Toplam gün sayısı 0 dan düşük olamaz");
		}
	}
	public Result updateState(UpdateRentalRequest updateRentalRequest) {
		Car car = carRepository.findById(updateRentalRequest.getCarId());
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
	public DataResult<GetRentalResponse> getById(int id) {
		Rental rental = this.rentalRepository.findById(id);
		GetRentalResponse response = this.modelMapperService.forResponse().map(rental, GetRentalResponse.class);
		return new SuccessDataResult<GetRentalResponse>(response);
	}

	@Override
	public DataResult<List<GetAllRentalsResponse>> getAll() {
		List<Rental> rentals = this.rentalRepository.findAll();
		
		List<GetAllRentalsResponse> response = rentals.stream().map(rental->this.modelMapperService.forResponse()
				.map(rental, GetAllRentalsResponse.class)).collect(Collectors.toList());
		return new SuccessDataResult<List<GetAllRentalsResponse>>(response);
	}
	
	private boolean checkIfCarState(int id) {
		Car car = this.carRepository.findById(id);
		if (car.getState() == 1) {
			return true;
		}
		else {
			return false;
		}
	}
	private boolean checkDateToRentACar(Date pickupDate, Date returnDate) {
        
		if (!pickupDate.before(returnDate) || pickupDate.before(new Date())) {
			return false;
		}
		else {
			return true;
		}
	}
}
