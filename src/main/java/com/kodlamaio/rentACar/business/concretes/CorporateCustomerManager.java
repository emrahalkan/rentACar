package com.kodlamaio.rentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.kodlamaio.rentACar.business.abstracts.CorporateCustomerService;
import com.kodlamaio.rentACar.business.requests.corporates.CreateCorporateCustomerRequest;
import com.kodlamaio.rentACar.business.requests.corporates.DeleteCorporateCustomerRequest;
import com.kodlamaio.rentACar.business.requests.corporates.UpdateCorporateCustomerRequest;
import com.kodlamaio.rentACar.business.responses.corporates.GetAllCorporateCustomerResponse;
import com.kodlamaio.rentACar.business.responses.corporates.GetCorporateCustomerResponse;
import com.kodlamaio.rentACar.core.utilities.exceptions.BusinessException;
import com.kodlamaio.rentACar.core.utilities.mapping.ModelMapperService;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;
import com.kodlamaio.rentACar.core.utilities.results.SuccessDataResult;
import com.kodlamaio.rentACar.core.utilities.results.SuccessResult;
import com.kodlamaio.rentACar.dataAccess.abstracts.CorporateCustomerRepository;
import com.kodlamaio.rentACar.entities.concretes.CorporateCustomer;

@Service
public class CorporateCustomerManager implements CorporateCustomerService {

	private CorporateCustomerRepository corporateCustomerRepository;
	private ModelMapperService modelMapperService;

	public CorporateCustomerManager(CorporateCustomerRepository corporateCustomerRepository,
			ModelMapperService modelMapperService) {
		this.corporateCustomerRepository = corporateCustomerRepository;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public Result add(CreateCorporateCustomerRequest createCorporateCustomerRequest) {
		checkCorporateExistsTaxNumber(createCorporateCustomerRequest.getTaxNumber());
		CorporateCustomer corporateCustomer = this.modelMapperService.forRequest().map(createCorporateCustomerRequest,
				CorporateCustomer.class);
		this.corporateCustomerRepository.save(corporateCustomer);
		return new SuccessResult("CORPORATE.ADDED");
	}

	@Override
	public Result delete(DeleteCorporateCustomerRequest deleteCorporateCustomerRequest) {
		checkCorporateExists(deleteCorporateCustomerRequest.getCorporateCustomerId());
		this.corporateCustomerRepository.deleteById(deleteCorporateCustomerRequest.getCorporateCustomerId());
		return new SuccessResult("CORPORATE.DELETED");
	}

	@Override
	public Result update(UpdateCorporateCustomerRequest updateCorporateCustomerRequest) {
		checkCorporateExists(updateCorporateCustomerRequest.getCorporateCustomerId());
		CorporateCustomer corporateCustomer = this.modelMapperService.forRequest().map(updateCorporateCustomerRequest,
				CorporateCustomer.class);
		this.corporateCustomerRepository.save(corporateCustomer);
		return new SuccessResult("CORPORATE.UPDATED");
	}

	@Override
	public DataResult<GetCorporateCustomerResponse> getById(int id) {
		checkCorporateExists(id);
		CorporateCustomer corporateCustomer = this.corporateCustomerRepository.findById(id).get();
		
		GetCorporateCustomerResponse response = this.modelMapperService.forResponse().map(corporateCustomer, GetCorporateCustomerResponse.class);
		
		return new SuccessDataResult<GetCorporateCustomerResponse>(response,"CORPORATE.LISTED");
	}

	@Override
	public DataResult<List<GetAllCorporateCustomerResponse>> getAll() {
		List<CorporateCustomer> users = this.corporateCustomerRepository.findAll();

		List<GetAllCorporateCustomerResponse> response = users.stream().map(user -> this.modelMapperService.forResponse()
				.map(user, GetAllCorporateCustomerResponse.class))
				.collect(Collectors.toList());
		return new SuccessDataResult<List<GetAllCorporateCustomerResponse>>(response,"CORPORATE.LISTED");
	}

	private void checkCorporateExistsTaxNumber(String taxNumber) {
		CorporateCustomer corporateCustomer = this.corporateCustomerRepository.findByTaxNumber(taxNumber);
		if (corporateCustomer != null) {
			throw new BusinessException("CORPORATE.ALREADY.ADDED");
		}
	}

	private void checkCorporateExists(int id) {
		CorporateCustomer corporateCustomer = this.corporateCustomerRepository.findById(id).get();
		if (corporateCustomer == null) {
			throw new BusinessException("CORPORATE.WAS.NOT.FOUND");
		}
	}

}
