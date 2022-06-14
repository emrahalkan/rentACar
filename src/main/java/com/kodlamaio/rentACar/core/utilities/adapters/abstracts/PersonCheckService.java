package com.kodlamaio.rentACar.core.utilities.adapters.abstracts;

import java.rmi.RemoteException;

import com.kodlamaio.rentACar.business.requests.users.CreateUserRequest;

public interface PersonCheckService {
	boolean checkPerson(CreateUserRequest user) throws NumberFormatException, RemoteException;
}
