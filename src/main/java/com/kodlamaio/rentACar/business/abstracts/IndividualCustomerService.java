package com.kodlamaio.rentACar.business.abstracts;

import java.rmi.RemoteException;
import java.util.List;

import com.kodlamaio.rentACar.business.requests.individuals.CreateIndividualCustomerRequest;
import com.kodlamaio.rentACar.business.requests.individuals.DeleteIndividualCustomerRequest;
import com.kodlamaio.rentACar.business.requests.individuals.UpdateIndividualCustomerRequest;
import com.kodlamaio.rentACar.business.responses.individuals.GetAllIndividualCustomerResponse;
import com.kodlamaio.rentACar.business.responses.individuals.GetIndividualCustomerResponse;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;
import com.kodlamaio.rentACar.entities.concretes.IndividualCustomer;

public interface IndividualCustomerService {
	Result add(CreateIndividualCustomerRequest createUserRequest) throws NumberFormatException, RemoteException;
	Result delete(DeleteIndividualCustomerRequest deleteUserRequest);
	Result update(UpdateIndividualCustomerRequest updateUserRequest);
	DataResult<List<GetAllIndividualCustomerResponse>> getAll();
	DataResult<GetIndividualCustomerResponse> getById(int id);
	DataResult<List<GetAllIndividualCustomerResponse>> getAll(Integer pageNo, Integer pageSize);
	
	IndividualCustomer findIndividualById(int id);
	IndividualCustomer findIndividualByNationality(String nationality);
	IndividualCustomer findIndividualByEmail(String email);
}

