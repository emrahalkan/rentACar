package com.kodlamaio.rentACar.business.abstracts;

import java.util.List;

import com.kodlamaio.rentACar.business.requests.addresses.CreateAddressRequest;
import com.kodlamaio.rentACar.business.requests.addresses.DeleteAddressRequest;
import com.kodlamaio.rentACar.business.requests.addresses.UpdateAddressRequest;
import com.kodlamaio.rentACar.business.responses.addresses.GetAddressResponse;
import com.kodlamaio.rentACar.business.responses.addresses.GetAllAddressesResponse;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;

public interface AddressService {
	Result add(CreateAddressRequest createAddressRequest);
	Result delete(DeleteAddressRequest deleteAddressRequest);
	Result update(UpdateAddressRequest updateAddressRequest);
	DataResult<List<GetAllAddressesResponse>> getAll();
	DataResult<GetAddressResponse> getById(int id);
	DataResult<List<GetAllAddressesResponse>> getAllBillAddress(int userId, int addressType);
	DataResult<List<GetAllAddressesResponse>> getAllContactAddress(int userId, int addressType);
}
