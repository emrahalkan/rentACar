package com.kodlamaio.rentACar.business.concretes;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

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
import com.kodlamaio.rentACar.dataAccess.abstracts.CityRepository;
import com.kodlamaio.rentACar.dataAccess.abstracts.CorporateCustomerRepository;
import com.kodlamaio.rentACar.dataAccess.abstracts.IndividualCustomerRepository;
import com.kodlamaio.rentACar.dataAccess.abstracts.RentalRepository;
import com.kodlamaio.rentACar.entities.concretes.Car;
import com.kodlamaio.rentACar.entities.concretes.City;
import com.kodlamaio.rentACar.entities.concretes.CorporateCustomer;
import com.kodlamaio.rentACar.entities.concretes.IndividualCustomer;
import com.kodlamaio.rentACar.entities.concretes.Rental;

@Service
public class RentalManager implements RentalService {

	private RentalRepository rentalRepository;
	private CarRepository carRepository;
	private ModelMapperService modelMapperService;
	private FindeksService findeksService;
	private IndividualCustomerRepository individualCustomerRepository;
	private CorporateCustomerRepository corporateCustomerRepository;
	private CityRepository cityRepository;
	

	public RentalManager(RentalRepository rentalRepository, CarRepository carRepository,
			ModelMapperService modelMapperService, CorporateCustomerRepository corporateCustomerRepository, FindeksService findeksService,
			IndividualCustomerRepository individualCustomerRepository, CityRepository cityRepository) {
		this.rentalRepository = rentalRepository;
		this.carRepository = carRepository;
		this.modelMapperService = modelMapperService;
		this.findeksService = findeksService;
		this.individualCustomerRepository = individualCustomerRepository;
		this.corporateCustomerRepository = corporateCustomerRepository;
		this.cityRepository = cityRepository;
	}

	@Override
	public Result addIndividualCustormer(CreateRentalRequest createRentalRequest) {
		checkIfCarState(createRentalRequest.getCarId());
		checkDateToRentACar(createRentalRequest.getPickupDate(), createRentalRequest.getReturnDate());
		checkUserFindexScore(createRentalRequest.getCarId(), createRentalRequest.getCustomerId());
		checkIndividualCustomerExists(createRentalRequest.getCustomerId());
		checkCityExists(createRentalRequest.getPickupCityId(), createRentalRequest.getReturnCityId());
		Rental rental = this.modelMapperService.forRequest().map(createRentalRequest, Rental.class);
		rental.setTotalDays(calculateTotalDays(rental));
		rental.setTotalPrice(calculateTotalPrice(rental));
		rental.getCar().setState(3);

		rentalRepository.save(rental);
		return new SuccessResult("RENTAL.ADDED");
	}

	@Override
	public Result addCorporateCustormer(CreateRentalRequest createRentalRequest) {
		checkIfCarState(createRentalRequest.getCarId());
		checkDateToRentACar(createRentalRequest.getPickupDate(), createRentalRequest.getReturnDate());
		checkCorporateCustomerExists(createRentalRequest.getCustomerId());
		checkCityExists(createRentalRequest.getPickupCityId(), createRentalRequest.getReturnCityId());
		Rental rental = this.modelMapperService.forRequest().map(createRentalRequest, Rental.class);
		rental.setTotalDays(calculateTotalDays(rental));
		rental.setTotalPrice(calculateTotalPrice(rental));
		rental.getCar().setState(3);

		rentalRepository.save(rental);
		return new SuccessResult("RENTAL.ADDED");
	}

	@Override
	public Result delete(DeleteRentalRequest deleteRentalRequest) {
		checkIfRentalExists(deleteRentalRequest.getId());
		Rental rental = this.rentalRepository.findById(deleteRentalRequest.getId());
		this.rentalRepository.delete(rental);
		return new SuccessResult("RENTAL.DELETED");
	}

	@Override
	public Result updateIndividualCustomer(UpdateRentalRequest updateRentalRequest) {
		checkIfRentalExists(updateRentalRequest.getId());
		checkDateToRentACar(updateRentalRequest.getPickupDate(), updateRentalRequest.getReturnDate());
		checkUserFindexScore(updateRentalRequest.getCarId(), updateRentalRequest.getCustomerId());
		checkIndividualCustomerExists(updateRentalRequest.getCustomerId());
		Car car = this.carRepository.findById(updateRentalRequest.getCarId());
		checkRentalStateFromUpdate(car);
		car.setState(3);
		Rental rental = this.modelMapperService.forRequest().map(updateRentalRequest, Rental.class);
		rental.setTotalDays(calculateTotalDays(rental));
		rental.setTotalPrice(calculateTotalPrice(rental));

		rentalRepository.save(rental);
		return new SuccessResult("RENTAL.ADDED");
	}

	@Override
	public Result updateCorporateCustomer(UpdateRentalRequest updateRentalRequest) {		
		checkIfRentalExists(updateRentalRequest.getId());
		checkDateToRentACar(updateRentalRequest.getPickupDate(), updateRentalRequest.getReturnDate());
		checkUserFindexScore(updateRentalRequest.getCarId(), updateRentalRequest.getCustomerId());
		checkCorporateCustomerExists(updateRentalRequest.getCustomerId());
		Car car = this.carRepository.findById(updateRentalRequest.getCarId());
		checkRentalStateFromUpdate(car);
		Rental rental = this.modelMapperService.forRequest().map(updateRentalRequest, Rental.class);
		rental.setTotalDays(calculateTotalDays(rental));
		rental.setTotalPrice(calculateTotalPrice(rental));

		rentalRepository.save(rental);
		return new SuccessResult("RENTAL.ADDED");
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

		if (car == null) {
			throw new BusinessException("THERE.IS.NOT.THIS.CAR");
		}

		if (car.getState() == 2 || car.getState() == 3) {
			throw new BusinessException("CAR.IS.NOT.AVAIBLE");
		}

	}

	private void checkIndividualCustomerExists(int individualId) {
		IndividualCustomer individualCustomer = this.individualCustomerRepository.findById(individualId);
		if (individualCustomer == null) {
			throw new BusinessException("THERE.IS.NOT.INDIVIDUAL.USER");
		}
	}
	
	private void checkCorporateCustomerExists(int individualId) {
		CorporateCustomer individualCustomer = this.corporateCustomerRepository.findById(individualId);
		if (individualCustomer == null) {
			throw new BusinessException("THERE.IS.NOT.INDIVIDUAL.USER");
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

	private double calculateTotalPrice(Rental rental) {
		double days = rental.getTotalDays();
		Car car = this.carRepository.findById(rental.getCar().getId());
		double totalDailyPrice = days * car.getDailyPrice();
		double diffCityPrice = isDiffReturnCityFromPickUpCity(rental.getPickupCityId().getId(),
				rental.getReturnCityId().getId());
		double totalPrice = totalDailyPrice + diffCityPrice;
		return totalPrice;
	}

	private void checkUserFindexScore(int carId, int individualId) {
		Car car = this.carRepository.findById(carId);
		IndividualCustomer user = this.individualCustomerRepository.findById(individualId);
		if (findeksService.checkPerson(user.getNationality()) < car.getMinFindexScore()) {
			throw new BusinessException("USER.IS.NOT.ENOUGH.FINDEX.SCORE - " + findeksService.checkPerson(user.getNationality()));
		}
	}

	private int calculateTotalDays(Rental rental) {
		int totalDays = (int) ChronoUnit.DAYS.between(rental.getPickupDate(), rental.getReturnDate());
		return totalDays;
	}
	
	private void checkIfRentalExists(int rentalId) {
		Rental rental = this.rentalRepository.findById(rentalId);	
		if (rental == null) {
			throw new BusinessException("THERE.IS.NOT.THIS.RENTAL");
		}
	}
	
	private void checkCityExists(int pickUpCityId, int returnCityId) {
		City pickUpCity = this.cityRepository.findById(pickUpCityId);
		City returnCity = this.cityRepository.findById(pickUpCityId);
		if (pickUpCity == null && returnCity == null) {
			throw new BusinessException("THERE.IS.NOT.THIS.CITY");
		}
	}
	
	private void checkRentalStateFromUpdate(Car newCar) {
		Car oldCar = this.carRepository.findById(newCar.getId());
		if (newCar.getState() != oldCar.getState()) {
			checkIfCarState(newCar.getId());
			oldCar.setState(1);		
		}
	}
}
