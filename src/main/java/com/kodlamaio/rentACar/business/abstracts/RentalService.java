package com.kodlamaio.rentACar.business.abstracts;

import java.util.List;

import com.kodlamaio.rentACar.business.requests.rentals.CreateRentalRequest;
import com.kodlamaio.rentACar.business.requests.rentals.DeleteRentalRequest;
import com.kodlamaio.rentACar.business.requests.rentals.UpdateRentalRequest;
import com.kodlamaio.rentACar.business.responses.rentals.GetAllRentalsResponse;
import com.kodlamaio.rentACar.business.responses.rentals.GetRentalResponse;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;
import com.kodlamaio.rentACar.entities.concretes.Rental;

public interface RentalService {
	Result addIndividualCustormer(CreateRentalRequest createRentalRequest);
	Result addCorporateCustormer(CreateRentalRequest createRentalRequest);
	Result delete(DeleteRentalRequest deleteRentalRequest);
	Result updateIndividualCustomer(UpdateRentalRequest updateRentalRequest);
	Result updateCorporateCustomer(UpdateRentalRequest updateRentalRequest);
	DataResult<GetRentalResponse> getById(int id);
	DataResult<List<GetAllRentalsResponse>> getAll();
	public Rental findRentalById(int rentalId);
}
