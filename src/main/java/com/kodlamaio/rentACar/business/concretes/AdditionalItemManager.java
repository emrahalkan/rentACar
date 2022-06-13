package com.kodlamaio.rentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.kodlamaio.rentACar.business.abstracts.AdditionalItemService;
import com.kodlamaio.rentACar.business.requests.additionalItems.CreateAdditionalItemRequest;
import com.kodlamaio.rentACar.business.requests.additionalItems.DeleteAdditionalItemRequest;
import com.kodlamaio.rentACar.business.requests.additionalItems.UpdateAdditionalItemRequest;
import com.kodlamaio.rentACar.business.responses.additionalItems.GetAdditionalItemResponse;
import com.kodlamaio.rentACar.business.responses.additionalItems.GetAllAdditionalItemsResponse;
import com.kodlamaio.rentACar.core.utilities.exceptions.BusinessException;
import com.kodlamaio.rentACar.core.utilities.mapping.ModelMapperService;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;
import com.kodlamaio.rentACar.core.utilities.results.SuccessDataResult;
import com.kodlamaio.rentACar.core.utilities.results.SuccessResult;
import com.kodlamaio.rentACar.dataAccess.abstracts.AdditionalItemRepository;
import com.kodlamaio.rentACar.entities.concretes.AdditionalItem;

@Service
public class AdditionalItemManager implements AdditionalItemService{
	
	private AdditionalItemRepository additionalItemRepository;
	private ModelMapperService modelMapperService;
	

	public AdditionalItemManager(AdditionalItemRepository additionalItemRepository,
			ModelMapperService modelMapperService) {
		this.additionalItemRepository = additionalItemRepository;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public Result add(CreateAdditionalItemRequest createAdditionalItemRequest) {
		checkIfItemExistsByName(createAdditionalItemRequest.getName());
		AdditionalItem additionalItem = this.modelMapperService.forRequest().map(createAdditionalItemRequest, AdditionalItem.class);
		this.additionalItemRepository.save(additionalItem);
		return new SuccessResult("ITEM.ADDED");
	}

	@Override
	public Result delete(DeleteAdditionalItemRequest deleteAdditionalItemRequest) {
		additionalItemRepository.deleteById(deleteAdditionalItemRequest.getId());
		return new SuccessResult("ITEM.DELETED");
	}

	@Override
	public Result update(UpdateAdditionalItemRequest updateAdditionalItemRequest) {
		checkIfItemExistsByName(updateAdditionalItemRequest.getName());
		AdditionalItem additionalItem = this.modelMapperService.forRequest().map(updateAdditionalItemRequest, AdditionalItem.class);
		this.additionalItemRepository.save(additionalItem);
		return new SuccessResult("ITEM.ADDED");
	}

	@Override
	public DataResult<GetAdditionalItemResponse> getById(int id) {
		AdditionalItem additionalItem = this.additionalItemRepository.findById(id);
		
		GetAdditionalItemResponse response = this.modelMapperService.forResponse().map(additionalItem, GetAdditionalItemResponse.class);
		return new SuccessDataResult<GetAdditionalItemResponse>(response);
	}

	@Override
	public DataResult<List<GetAllAdditionalItemsResponse>> getAll() {
		List<AdditionalItem> additionalItems = this.additionalItemRepository.findAll();
		
		List<GetAllAdditionalItemsResponse> response = additionalItems.stream().map(additionalItem->this.modelMapperService.forResponse()
						.map(additionalItem, GetAllAdditionalItemsResponse.class)).collect(Collectors.toList());
		return new SuccessDataResult<List<GetAllAdditionalItemsResponse>>(response);
	}
	
	private void checkIfItemExistsByName(String name) {
		AdditionalItem additionalItem = this.additionalItemRepository.findByName(name);
		if (additionalItem != null) {
			throw new BusinessException("ITEM.EXISTS");
		}
	}
}
