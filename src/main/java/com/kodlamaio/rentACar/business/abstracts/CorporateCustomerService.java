package com.kodlamaio.rentACar.business.abstracts;

import java.util.List;

import com.kodlamaio.rentACar.business.requests.corporates.CreateCorporateCustomerRequest;
import com.kodlamaio.rentACar.business.requests.corporates.DeleteCorporateCustomerRequest;
import com.kodlamaio.rentACar.business.requests.corporates.UpdateCorporateCustomerRequest;
import com.kodlamaio.rentACar.business.responses.corporates.GetAllCorporateCustomerResponse;
import com.kodlamaio.rentACar.business.responses.corporates.GetCorporateCustomerResponse;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;

public interface CorporateCustomerService {
	Result add(CreateCorporateCustomerRequest createCorporateCustomerRequest);
	Result delete(DeleteCorporateCustomerRequest deleteCorporateCustomerRequest);
	Result update(UpdateCorporateCustomerRequest updateCorporateCustomerRequest);
	DataResult<GetCorporateCustomerResponse> getById(int id);
	DataResult<List<GetAllCorporateCustomerResponse>> getAll();
	DataResult<List<GetAllCorporateCustomerResponse>> getAll(Integer pageNo, Integer pageSize);
}
