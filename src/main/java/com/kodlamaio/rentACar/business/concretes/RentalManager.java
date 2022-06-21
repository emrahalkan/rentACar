package com.kodlamaio.rentACar.business.concretes;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kodlamaio.rentACar.business.abstracts.RentalService;
import com.kodlamaio.rentACar.business.requests.rentals.CreateRentalRequest;
import com.kodlamaio.rentACar.business.requests.rentals.DeleteRentalRequest;
import com.kodlamaio.rentACar.business.requests.rentals.UpdateRentalRequest;
import com.kodlamaio.rentACar.business.responses.rentals.GetAllRentalsResponse;
import com.kodlamaio.rentACar.business.responses.rentals.GetRentalResponse;
import com.kodlamaio.rentACar.core.utilities.adapters.abstracts.FindeksService;
import com.kodlamaio.rentACar.core.utilities.exceptions.BusinessException;
import com.kodlamaio.rentACar.core.utilities.mapping.ModelMapperService;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;
import com.kodlamaio.rentACar.core.utilities.results.SuccessDataResult;
import com.kodlamaio.rentACar.core.utilities.results.SuccessResult;
import com.kodlamaio.rentACar.dataAccess.abstracts.CarRepository;
import com.kodlamaio.rentACar.dataAccess.abstracts.RentalRepository;
import com.kodlamaio.rentACar.dataAccess.abstracts.UserRepository;
import com.kodlamaio.rentACar.entities.concretes.Car;
import com.kodlamaio.rentACar.entities.concretes.Rental;
import com.kodlamaio.rentACar.entities.concretes.User;

@Service
public class RentalManager implements RentalService {
	
	@Autowired
	private RentalRepository rentalRepository;
	@Autowired
	private CarRepository carRepository;
	@Autowired
	private ModelMapperService modelMapperService;
	@Autowired
	private FindeksService findeksService;
	@Autowired
	private UserRepository userRepository;

	@Override
	public Result add(CreateRentalRequest createRentalRequest) {
		checkIfCarState(createRentalRequest.getCarId());
		checkDateToRentACar(createRentalRequest.getPickupDate(), createRentalRequest.getReturnDate());
		//checkUserFindexScore(createRentalRequest);
		Rental rental = this.modelMapperService.forRequest().map(createRentalRequest, Rental.class);
		
		int diffDate = (int) ChronoUnit.DAYS.between(rental.getPickupDate(), rental.getReturnDate());
		rental.setTotalDays(diffDate);
		
		Car car = this.carRepository.findById(createRentalRequest.getCarId());
		double totalPrice = calculateTotalPrice(rental, car.getDailyPrice());
	
		car.setState(3);
		car.setCity(rental.getReturnCityId());
		
		rental.setTotalPrice(totalPrice);

		rentalRepository.save(rental);
		return new SuccessResult("RENTAL.ADDED");
	}
	@Override
	public Result delete(DeleteRentalRequest deleteRentalRequest) {
		Rental rental = this.rentalRepository.findById(deleteRentalRequest.getId());
		this.rentalRepository.delete(rental);
		return new SuccessResult("RENTAL.DELETED");
	}

	@Override
	public Result update(UpdateRentalRequest updateRentalRequest) {
		checkIfCarState(updateRentalRequest.getCarId());
		checkDateToRentACar(updateRentalRequest.getPickupDate(), updateRentalRequest.getReturnDate());
		
		Rental rental = this.modelMapperService.forRequest().map(updateRentalRequest, Rental.class);
		
		int diffDate = (int) ChronoUnit.DAYS.between(rental.getPickupDate(), rental.getReturnDate());
		rental.setTotalDays(diffDate);
		
		Car car = this.carRepository.findById(updateRentalRequest.getCarId());
		double totalPrice = calculateTotalPrice(rental, car.getDailyPrice());
	
		rental.setPickupCityId(car.getCity());
		rental.setTotalPrice(totalPrice);

		rentalRepository.save(rental);
		return new SuccessResult("RENTAL.ADDED");
	}

	public Result updateState(UpdateRentalRequest updateRentalRequest) {
		Car car = carRepository.findById(updateRentalRequest.getCarId());
		if (car.getState() == 1) {
			car.setState(3);
		} else {
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

		List<GetAllRentalsResponse> response = rentals.stream()
				.map(rental -> this.modelMapperService.forResponse().map(rental, GetAllRentalsResponse.class))
				.collect(Collectors.toList());
		return new SuccessDataResult<List<GetAllRentalsResponse>>(response);
	}

	private void checkIfCarState(int id) {
		Car car = this.carRepository.findById(id);
		if (car.getState() == 2 || car.getState() == 3) {
			throw new BusinessException("CAR.IS.NOT.AVAIBLE");
		}
	}

	private void checkDateToRentACar(LocalDate pickupDate, LocalDate returnDate) {
		if (!pickupDate.isBefore(returnDate) || pickupDate.isBefore(LocalDate.now())) {
			throw new BusinessException("PICKUPDATE.AND.RETURNDATE.ERROR");
		}
	}

	private double isDiffReturnCityFromPickUpCity(int pickUpCity, int returnCity) {
		if (pickUpCity != returnCity) {
			return 750.0;
		}
		return 0;
	}

	private double calculateTotalPrice(Rental rental, double dailyPrice) {
		double days = rental.getTotalDays();
		double totalDailyPrice =  days * dailyPrice;
		double diffCityPrice =  isDiffReturnCityFromPickUpCity(rental.getPickupCityId().getId(), rental.getReturnCityId().getId());
		double totalPrice = totalDailyPrice + diffCityPrice;
		return totalPrice;
	}
	
	private void checkUserFindexScore(CreateRentalRequest createRentalRequest) {
		Car car = this.carRepository.findById(createRentalRequest.getCarId());
		User user = this.userRepository.findById(createRentalRequest.getUserId());
		if(findeksService.checkPerson(user.getNationality()) > car.getMinFindexScore()) {
			throw new BusinessException("USER.IS.NOT.ENOUGH.FINDEX.SCORE");
		}
	}
}
