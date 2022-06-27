package com.kodlamaio.rentACar.core.utilities.adapters.concretes;

import java.rmi.RemoteException;

import org.springframework.stereotype.Service;

import com.kodlamaio.rentACar.business.requests.individuals.CreateIndividualCustomerRequest;
import com.kodlamaio.rentACar.business.requests.individuals.UpdateIndividualCustomerRequest;
import com.kodlamaio.rentACar.core.utilities.adapters.abstracts.PersonCheckService;

import tr.gov.nvi.tckimlik.WS.KPSPublicSoapProxy;

@Service
public class MernisKpsAdapter implements PersonCheckService {

	@Override
	public boolean checkPerson(CreateIndividualCustomerRequest user) throws NumberFormatException, RemoteException {
		
		KPSPublicSoapProxy kpsPublicSoapProxy = new KPSPublicSoapProxy();
		boolean result = kpsPublicSoapProxy.TCKimlikNoDogrula
				(Long.parseLong(user.getNationality()), user.getFirstName(), user.getLastName(), user.getBirthDate().getYear());
		return result;
	}

	@Override
	public boolean checkPerson(UpdateIndividualCustomerRequest updateUserRequest)
			throws NumberFormatException, RemoteException {
		return false;
	}

}
