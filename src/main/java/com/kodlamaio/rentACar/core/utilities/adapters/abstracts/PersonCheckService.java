package com.kodlamaio.rentACar.core.utilities.adapters.abstracts;

import java.rmi.RemoteException;

import com.kodlamaio.rentACar.business.requests.individuals.CreateIndividualCustomerRequest;

public interface PersonCheckService {
	boolean checkPerson(CreateIndividualCustomerRequest createUserRequest) throws NumberFormatException, RemoteException;
}
