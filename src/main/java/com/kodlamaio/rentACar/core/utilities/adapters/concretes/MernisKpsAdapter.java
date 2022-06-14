package com.kodlamaio.rentACar.core.utilities.adapters.concretes;

import java.rmi.RemoteException;

import org.springframework.stereotype.Service;

import com.kodlamaio.rentACar.core.utilities.adapters.abstracts.PersonCheckService;
import com.kodlamaio.rentACar.entities.concretes.User;

import tr.gov.nvi.tckimlik.WS.KPSPublicSoapProxy;

@Service
public class MernisKpsAdapter implements PersonCheckService {

	@Override
	public boolean checkPerson(User user) throws NumberFormatException, RemoteException {
		
		KPSPublicSoapProxy kpsPublicSoapProxy = new KPSPublicSoapProxy();
		boolean result = kpsPublicSoapProxy.TCKimlikNoDogrula
				(Long.parseLong(user.getNationality()), user.getFirstName(), user.getLastName(), 1994);
		return result;
	}
}
