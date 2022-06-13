package com.kodlamaio.rentACar.business.concretes;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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
import com.kodlamaio.rentACar.core.utilities.exceptions.BusinessException;
import com.kodlamaio.rentACar.core.utilities.mapping.ModelMapperService;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.ErrorResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;
import com.kodlamaio.rentACar.core.utilities.results.SuccessDataResult;
import com.kodlamaio.rentACar.core.utilities.results.SuccessResult;
import com.kodlamaio.rentACar.dataAccess.abstracts.AdditionalServiceRepository;
import com.kodlamaio.rentACar.dataAccess.abstracts.CarRepository;
import com.kodlamaio.rentACar.dataAccess.abstracts.RentalRepository;
import com.kodlamaio.rentACar.entities.concretes.AdditionalService;
import com.kodlamaio.rentACar.entities.concretes.Car;
import com.kodlamaio.rentACar.entities.concretes.Rental;

@Service
public class RentalManager implements RentalService {

	private RentalRepository rentalRepository;
	private CarRepository carRepository;
	private ModelMapperService modelMapperService;
	private AdditionalServiceRepository additionalServiceRepository;

	public RentalManager(RentalRepository rentalRepository, CarRepository carRepository,
			ModelMapperService modelMapperService) {
		this.rentalRepository = rentalRepository;
		this.carRepository = carRepository;
		this.modelMapperService = modelMapperService,
		this.additionalServiceRepository = additionalServiceRepository;
	}

	@Override
	public Result add(CreateRentalRequest createRentalRequest) {
		checkIfCarState(createRentalRequest.getCarId());
		checkDateToRentACar(createRentalRequest.getPickupDate(), createRentalRequest.getReturnDate());
		
		Rental rental = this.modelMapperService.forRequest().map(createRentalRequest, Rental.class);
		
		int diffDate = (int) ChronoUnit.DAYS.between(rental.getPickupDate(), rental.getReturnDate());
		rental.setTotalDays((int) diffDate);
		
		Car car = this.carRepository.findById(createRentalRequest.getCarId());
		calculateTotalPrice(rental, car.getDailyPrice());
		
		car.setState(3);
		rental.setPickupCityId(car.getCity());
		car.setCity(rental.getReturnCityId());
		double totalPrice = car.getDailyPrice() * diffDate;
		rental.setTotalPrice(totalPrice);

		rentalRepository.save(rental);
		return new SuccessResult("RENTAL.ADDED");

//			else {
//				return new ErrorResult("Toplam gün sayısı 0 dan düşük olamaz");
//			}
	}

//			return new ErrorResult("Araba avaible olmalıdır");
//		}

	@Override
	public Result delete(DeleteRentalRequest deleteRentalRequest) {
		Rental rental = this.rentalRepository.findById(deleteRentalRequest.getId());
		this.rentalRepository.delete(rental);
		return new SuccessResult("RENTAL.DELETED");
	}

	@Override
	public Result update(UpdateRentalRequest updateRentalRequest) {
		Rental rental = this.modelMapperService.forRequest().map(updateRentalRequest, Rental.class);

		long diffDate = (updateRentalRequest.getReturnDate().getTime()
				- (updateRentalRequest.getPickupDate().getTime()));
		if (checkDateToRentACar(updateRentalRequest.getPickupDate(), updateRentalRequest.getReturnDate())) {
			rental.setTotalDays((int) diffDate);

			Car car = this.carRepository.findById(updateRentalRequest.getCarId());
			car.setState(3);
			double totalPrice = car.getDailyPrice() * diffDate;
			rental.setTotalPrice(totalPrice);

			rentalRepository.save(rental);
			return new SuccessResult("RENTAL.UPDATED");
		} else {
			return new ErrorResult("Toplam gün sayısı 0 dan düşük olamaz");
		}
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
		if (car.getState() == 2) {
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
		double totalAdditionalService = 0;
		
		List<AdditionalService> additionalServices = this.additionalServiceRepository.getByRentalId(rental.getId());
		
		for (AdditionalService additionalService : additionalServices) {
			totalAdditionalService += additionalService.getTotalPrice();
		}
		double totalPrice = totalDailyPrice + diffCityPrice + totalAdditionalService;
		
		return totalPrice;
	}
}
