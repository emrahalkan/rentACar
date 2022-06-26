package com.kodlamaio.rentACar.business.abstracts;

import java.util.List;

import com.kodlamaio.rentACar.business.requests.individuals.CreateIndividualCustomerRequest;
import com.kodlamaio.rentACar.business.requests.individuals.DeleteIndividualCustomerRequest;
import com.kodlamaio.rentACar.business.requests.individuals.UpdateIndividualCustomerRequest;
import com.kodlamaio.rentACar.business.responses.individuals.GetAllIndividualCustomerResponse;
import com.kodlamaio.rentACar.business.responses.individuals.GetIndividualCustomerResponse;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;

public interface IndividualCustomerService {
	Result add(CreateIndividualCustomerRequest createCorporateCustomerRequest);
	Result delete(DeleteIndividualCustomerRequest deleteCorporateCustomerRequest);
	Result update(UpdateIndividualCustomerRequest updateCorporateCustomerRequest);
	DataResult<GetIndividualCustomerResponse> getById(int id);
	DataResult<List<GetAllIndividualCustomerResponse>> getAll();
}