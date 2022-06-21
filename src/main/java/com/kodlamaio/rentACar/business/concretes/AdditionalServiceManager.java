package com.kodlamaio.rentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kodlamaio.rentACar.business.abstracts.AdditionalServiceService;
import com.kodlamaio.rentACar.business.requests.additionalServices.CreateAdditionalServiceRequest;
import com.kodlamaio.rentACar.business.requests.additionalServices.DeleteAdditionalServiceRequest;
import com.kodlamaio.rentACar.business.requests.additionalServices.UpdateAdditionalServiceRequest;
import com.kodlamaio.rentACar.business.responses.additionalServices.GetAdditionalServiceResponse;
import com.kodlamaio.rentACar.business.responses.additionalServices.GetAllAdditionalServicesResponse;
import com.kodlamaio.rentACar.core.utilities.mapping.ModelMapperService;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;
import com.kodlamaio.rentACar.core.utilities.results.SuccessDataResult;
import com.kodlamaio.rentACar.core.utilities.results.SuccessResult;
import com.kodlamaio.rentACar.dataAccess.abstracts.AdditionalItemRepository;
import com.kodlamaio.rentACar.dataAccess.abstracts.AdditionalServiceRepository;
import com.kodlamaio.rentACar.dataAccess.abstracts.RentalRepository;
import com.kodlamaio.rentACar.entities.concretes.AdditionalService;
import com.kodlamaio.rentACar.entities.concretes.Rental;

@Service
public class AdditionalServiceManager implements AdditionalServiceService{
	@Autowired
	private AdditionalServiceRepository additionalServiceRepository;
	@Autowired
	private AdditionalItemRepository additionalItemRepository;
	@Autowired
	private ModelMapperService modelMapperService;
	@Autowired
	private RentalRepository rentalRepository;


	@Override
	public Result add(CreateAdditionalServiceRequest createAdditionalServiceRequest) {
		AdditionalService additionalService = this.modelMapperService.forRequest().map(createAdditionalServiceRequest, AdditionalService.class);
		
		int rentalTotalDays = this.rentalRepository.findById(createAdditionalServiceRequest.getRentalId()).getTotalDays();
		additionalService.setTotalDays(rentalTotalDays);
		
		double additionalItemPrice = this.additionalItemRepository.findById(createAdditionalServiceRequest.getAdditionalItemId()).getPrice();
		double totalPrice = calculateTotalPriceAdditionalService(rentalTotalDays, additionalItemPrice);
		additionalService.setTotalPrice(totalPrice);
		
		this.additionalServiceRepository.save(additionalService);
		return new SuccessResult("ADDITIONALSERVICE.ADDED");
	}

	@Override
	public Result delete(DeleteAdditionalServiceRequest deleteAdditionalServiceRequest) {
		this.additionalServiceRepository.deleteById(deleteAdditionalServiceRequest.getId());
		
		return new SuccessResult("ADDITIONALSERVICE.DELETED");
	}

	@Override
	public Result update(UpdateAdditionalServiceRequest updateAdditionalServiceRequest) {
		AdditionalService additionalService = this.modelMapperService.forRequest()
				.map(updateAdditionalServiceRequest, AdditionalService.class);
		
		Rental rental = this.rentalRepository.findById(updateAdditionalServiceRequest.getRentalId());
		int rentalTotalDays = rental.getTotalDays();
		additionalService.setTotalDays(rentalTotalDays);
		
		double additionalItemPrice = this.additionalItemRepository.findById(updateAdditionalServiceRequest.getAdditionalItemId()).getPrice();
		double totalPrice = calculateTotalPriceAdditionalService(rentalTotalDays, additionalItemPrice);
		additionalService.setTotalPrice(totalPrice);
		
		this.additionalServiceRepository.save(additionalService);		
		return new SuccessResult("ADDITIONALSERVICE.UPDATED");
	}

	@Override
	public DataResult<GetAdditionalServiceResponse> getById(int id) {
		AdditionalService additionalService = this.additionalServiceRepository.findById(id);
		
		GetAdditionalServiceResponse response = this.modelMapperService.forResponse()
				.map(additionalService, GetAdditionalServiceResponse.class);
		return new SuccessDataResult<GetAdditionalServiceResponse>(response, "GET.ADDITIONALSERVICE");
	}

	@Override
	public DataResult<List<GetAllAdditionalServicesResponse>> getAll() {
		List<AdditionalService> getAllAdditionalServices = this.additionalServiceRepository.findAll();
		
		List<GetAllAdditionalServicesResponse> response = getAllAdditionalServices.stream()
				.map(additionalService->this.modelMapperService.forResponse()
						.map(getAllAdditionalServices, GetAllAdditionalServicesResponse.class)).collect(Collectors.toList());
		return new SuccessDataResult<List<GetAllAdditionalServicesResponse>>(response,"GET.ALL.ADDITIONALSERVICE");
	}
	
	private double calculateTotalPriceAdditionalService(int days, double price) {
		return days*price;
	}	
}
