package com.kodlamaio.rentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.kodlamaio.rentACar.business.abstracts.AddressService;
import com.kodlamaio.rentACar.business.requests.addresses.CreateAddressRequest;
import com.kodlamaio.rentACar.business.requests.addresses.DeleteAddressRequest;
import com.kodlamaio.rentACar.business.requests.addresses.UpdateAddressRequest;
import com.kodlamaio.rentACar.business.responses.addresses.GetAddressResponse;
import com.kodlamaio.rentACar.business.responses.addresses.GetAllAddressesResponse;
import com.kodlamaio.rentACar.core.utilities.mapping.ModelMapperService;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;
import com.kodlamaio.rentACar.core.utilities.results.SuccessDataResult;
import com.kodlamaio.rentACar.core.utilities.results.SuccessResult;
import com.kodlamaio.rentACar.dataAccess.abstracts.AddressRepository;
import com.kodlamaio.rentACar.entities.concretes.Address;

@Service
public class AddressManager implements AddressService {
	private AddressRepository addressRepository;
	private ModelMapperService modelMapperService;
	// private UserRepository userRepository;

	public AddressManager(AddressRepository addressRepository, ModelMapperService modelMapperService) {
		this.addressRepository = addressRepository;
		this.modelMapperService = modelMapperService;
		// this.userRepository = userRepository;
	}

	@Override
	public Result add(CreateAddressRequest createAddressRequest) {
		Address address = this.modelMapperService.forRequest().map(createAddressRequest, Address.class);
		this.addressRepository.save(address);
		return new SuccessResult("ADDRESS.ADDED");
	}

	@Override
	public Result delete(DeleteAddressRequest deleteAddressRequest) {
		this.addressRepository.deleteById(deleteAddressRequest.getId());
		return new SuccessResult("ADDRESS.DELETED");
	}

	@Override
	public Result update(UpdateAddressRequest updateAddressRequest) {
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
		Address address = this.addressRepository.findById(id);

		GetAddressResponse getAddressResponse = this.modelMapperService.forResponse().map(address,
				GetAddressResponse.class);

		return new SuccessDataResult<GetAddressResponse>(getAddressResponse, "GET.ADDRESS");
	}

	@Override
	public DataResult<List<GetAllAddressesResponse>> getAllBillAddress(int userId, int addressType) {
		List<Address> addresses = this.addressRepository.getByCustomerIdAndAddressType(userId, addressType);
		List<GetAllAddressesResponse> response = addresses.stream()
				.map(address -> this.modelMapperService.forResponse().map(address, GetAllAddressesResponse.class))
				.collect(Collectors.toList());
		return new SuccessDataResult<List<GetAllAddressesResponse>>(response);
	}

	@Override
	public DataResult<List<GetAllAddressesResponse>> getAllContactAddress(int userId, int addressType) {
		List<Address> addresses = this.addressRepository.getByCustomerIdAndAddressType(userId, addressType);
		List<GetAllAddressesResponse> response = addresses.stream()
				.map(address -> this.modelMapperService.forResponse().map(address, GetAllAddressesResponse.class))
				.collect(Collectors.toList());
		return new SuccessDataResult<List<GetAllAddressesResponse>>(response);
	}
}
