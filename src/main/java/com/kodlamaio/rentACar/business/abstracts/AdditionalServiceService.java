package com.kodlamaio.rentACar.business.abstracts;

import java.util.List;

import com.kodlamaio.rentACar.business.requests.additionalServices.CreateAdditionalServiceRequest;
import com.kodlamaio.rentACar.business.requests.additionalServices.DeleteAdditionalServiceRequest;
import com.kodlamaio.rentACar.business.requests.additionalServices.UpdateAdditionalServiceRequest;
import com.kodlamaio.rentACar.business.responses.additionalServices.GetAdditionalServiceResponse;
import com.kodlamaio.rentACar.business.responses.additionalServices.GetAllAdditionalServicesResponse;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;

public interface AdditionalServiceService {
	Result add(CreateAdditionalServiceRequest createAdditionalServiceRequest);
	Result delete(DeleteAdditionalServiceRequest deleteAdditionalServiceRequest);
	Result update(UpdateAdditionalServiceRequest updateAdditionalServiceRequest);
	DataResult<GetAdditionalServiceResponse> getById(int id);
	DataResult<List<GetAllAdditionalServicesResponse>> getAll();
}
