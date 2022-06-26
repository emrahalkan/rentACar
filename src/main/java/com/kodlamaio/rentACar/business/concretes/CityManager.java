package com.kodlamaio.rentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.kodlamaio.rentACar.business.abstracts.CityService;
import com.kodlamaio.rentACar.business.requests.cities.CreateCityRequest;
import com.kodlamaio.rentACar.business.requests.cities.DeleteCityRequest;
import com.kodlamaio.rentACar.business.requests.cities.UpdateCityRequest;
import com.kodlamaio.rentACar.business.responses.cities.GetAllCitiesResponse;
import com.kodlamaio.rentACar.business.responses.cities.GetCityResponse;
import com.kodlamaio.rentACar.core.utilities.exceptions.BusinessException;
import com.kodlamaio.rentACar.core.utilities.mapping.ModelMapperService;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;
import com.kodlamaio.rentACar.core.utilities.results.SuccessDataResult;
import com.kodlamaio.rentACar.core.utilities.results.SuccessResult;
import com.kodlamaio.rentACar.dataAccess.abstracts.CityRepository;
import com.kodlamaio.rentACar.entities.concretes.City;

@Service
public class CityManager implements CityService {
	
	private CityRepository cityRepository;
	private ModelMapperService modelMapperService;

	public CityManager(CityRepository cityRepository, ModelMapperService modelMapperService) {
		this.cityRepository = cityRepository;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public Result add(CreateCityRequest createCityRequest) {
		checkIfBrandExistsByName(createCityRequest.getName());
		City city = this.modelMapperService.forRequest().map(createCityRequest, City.class);
		this.cityRepository.save(city);
		return new SuccessResult("CITY.ADDED");
	}

	@Override
	public Result delete(DeleteCityRequest deleteCityRequest) {
		checkIsCityNull(deleteCityRequest.getId());
		this.cityRepository.deleteById(deleteCityRequest.getId());
		return new SuccessResult("CITY.DELETED");
	}

	@Override
	public Result update(UpdateCityRequest updateCityRequest) {
		checkIsCityNull(updateCityRequest.getId());
		checkIfBrandExistsByName(updateCityRequest.getName());
		City city = this.modelMapperService.forRequest().map(updateCityRequest, City.class);
		this.cityRepository.save(city);
		return new SuccessResult("CITY.UPDATED");
	}

	@Override
	public DataResult<List<GetAllCitiesResponse>> getAll() {
		List<City> cities = this.cityRepository.findAll();
		
		List<GetAllCitiesResponse> response = cities.stream().map(city->this.modelMapperService.forResponse()
				.map(city, GetAllCitiesResponse.class)).collect(Collectors.toList());
		return new SuccessDataResult<List<GetAllCitiesResponse>>(response);
	}

	@Override
	public DataResult<GetCityResponse> getById(int id) {
		checkIsCityNull(id);
		City city = this.cityRepository.findById(id).get();
		GetCityResponse response = this.modelMapperService.forResponse().map(city, GetCityResponse.class);
		return new SuccessDataResult<GetCityResponse>(response);
	}
	
	private void checkIfBrandExistsByName(String name) {
		City currentCity = this.cityRepository.findByName(name);
		if (currentCity != null) {
			throw new BusinessException("CITY.EXISTS");
		}
	}
	
	private void checkIsCityNull(int cityId) {
		City city = this.cityRepository.findById(cityId).get();
		if (city == null) {
			throw new BusinessException("THERE.IS.NOT.CITY");
		}
	}

}
