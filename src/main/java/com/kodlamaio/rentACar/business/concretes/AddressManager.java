package com.kodlamaio.rentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.kodlamaio.rentACar.business.abstracts.AddressService;
import com.kodlamaio.rentACar.business.abstracts.IndividualCustomerService;
import com.kodlamaio.rentACar.business.requests.addresses.CreateAddressRequest;
import com.kodlamaio.rentACar.business.requests.addresses.DeleteAddressRequest;
import com.kodlamaio.rentACar.business.requests.addresses.UpdateAddressRequest;
import com.kodlamaio.rentACar.business.responses.addresses.GetAddressResponse;
import com.kodlamaio.rentACar.business.responses.addresses.GetAllAddressesResponse;
import com.kodlamaio.rentACar.core.utilities.exceptions.BusinessException;
import com.kodlamaio.rentACar.core.utilities.mapping.ModelMapperService;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;
import com.kodlamaio.rentACar.core.utilities.results.SuccessDataResult;
import com.kodlamaio.rentACar.core.utilities.results.SuccessResult;
import com.kodlamaio.rentACar.dataAccess.abstracts.AddressRepository;
import com.kodlamaio.rentACar.dataAccess.abstracts.CorporateCustomerRepository;
import com.kodlamaio.rentACar.dataAccess.abstracts.CustomerRepository;
import com.kodlamaio.rentACar.entities.concretes.Address;
import com.kodlamaio.rentACar.entities.concretes.CorporateCustomer;
import com.kodlamaio.rentACar.entities.concretes.Customer;

@Service
public class AddressManager implements AddressService {
	private AddressRepository addressRepository;
	private ModelMapperService modelMapperService;
	private IndividualCustomerService individualCustomerService;
	private CorporateCustomerRepository corporateCustomerRepository;
//	private CustomerRepository customerRepository;

	public AddressManager(AddressRepository addressRepository, ModelMapperService modelMapperService, IndividualCustomerService individualCustomerService,
			CorporateCustomerRepository corporateCustomerRepository, CustomerRepository customerRepository	) {
		this.addressRepository = addressRepository;
		this.modelMapperService = modelMapperService;
		this.individualCustomerService = individualCustomerService;
		this.corporateCustomerRepository = corporateCustomerRepository;
		//this.customerRepository = customerRepository;
	}

	@Override
	public Result addIndividualCustomer(CreateAddressRequest createAddressRequest) {
		checkIndividualExists(createAddressRequest.getUserId());
		Address address = this.modelMapperService.forRequest().map(createAddressRequest, Address.class);
		this.addressRepository.save(address);
		return new SuccessResult("ADDRESS.ADDED");
	}
	
	@Override
	public Result addCorporateCustomer(CreateAddressRequest createAddressRequest) {
		checkCorporateExists(createAddressRequest.getUserId());
		Address address = this.modelMapperService.forRequest().map(createAddressRequest, Address.class);
		this.addressRepository.save(address);
		return new SuccessResult("ADDRESS.ADDED");
	}

	@Override
	public Result delete(DeleteAddressRequest deleteAddressRequest) {
		checkCustomerExists(deleteAddressRequest.getId());
		this.addressRepository.deleteById(deleteAddressRequest.getId());
		return new SuccessResult("ADDRESS.DELETED");
	}

	@Override
	public Result updateIndividualCustomer(UpdateAddressRequest updateAddressRequest) {
		checkIndividualExists(updateAddressRequest.getUserId());
		Address address = this.modelMapperService.forRequest().map(updateAddressRequest, Address.class);
		this.addressRepository.save(address);
		return new SuccessResult("ADDRESS.UPDATED");
	}
	
	@Override
	public Result updateCorporateCustomer(UpdateAddressRequest updateAddressRequest) {
		checkCorporateExists(updateAddressRequest.getUserId());
		Address address = this.modelMapperService.forRequest().map(updateAddressRequest, Address.class);
		this.addressRepository.save(address);
		return new SuccessResult("ADDRESS.UPDATED");
	}

	@Override
	public DataResult<List<GetAllAddressesResponse>> getAll() {
		List<Address> getAllAddressesResponses = this.addressRepository.findAll();

		List<GetAllAddressesResponse> response = getAllAddressesResponses.stream()
				.map(address -> this.modelMapperService.forResponse().map(address, GetAllAddressesResponse.class))
				.collect(Collectors.toList());
		return new SuccessDataResult<List<GetAllAddressesResponse>>(response);
	}

	@Override
	public DataResult<GetAddressResponse> getById(int id) {
		checkCustomerExists(id);
		Address address = this.addressRepository.findById(id);

		GetAddressResponse getAddressResponse = this.modelMapperService.forResponse().map(address,
				GetAddressResponse.class);

		return new SuccessDataResult<GetAddressResponse>(getAddressResponse, "GET.ADDRESS");
	}

	@Override
	public DataResult<List<GetAllAddressesResponse>> getAllByAddressType(int userId, int addressType) {
		List<Address> addresses = this.addressRepository.getByCustomerIdAndAddressType(userId, addressType);
		List<GetAllAddressesResponse> response = addresses.stream()
				.map(address -> this.modelMapperService.forResponse().map(address, GetAllAddressesResponse.class))
				.collect(Collectors.toList());
		return new SuccessDataResult<List<GetAllAddressesResponse>>(response);
	}
	
	private void checkIndividualExists(int individualId) {
		this.individualCustomerService.findIndividualById(individualId);
	}
	
	private void checkCorporateExists(int corporateId) {
		CorporateCustomer corporateCustomer = this.corporateCustomerRepository.findById(corporateId);
		if (corporateCustomer == null) {
			throw new BusinessException("THERE.IS.NOT.THIS.CORPORATECUSTOMER");
		}
	}
	
	private void checkCustomerExists(int customerId) {
	//	Customer customer = this.customerRepository.findById(customerId);
//		if (customer == null) {
//			throw new BusinessException("THERE.IS.NOT.THIS.CORPORATECUSTOMER");
//		}
	}

}
