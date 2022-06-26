package com.kodlamaio.rentACar.business.concretes;

import java.rmi.RemoteException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.kodlamaio.rentACar.business.abstracts.UserService;
import com.kodlamaio.rentACar.business.requests.individuals.CreateIndividualCustomerRequest;
import com.kodlamaio.rentACar.business.requests.individuals.DeleteIndividualCustomerRequest;
import com.kodlamaio.rentACar.business.requests.individuals.UpdateIndividualCustomerRequest;
import com.kodlamaio.rentACar.business.responses.individuals.GetAllIndividualCustomerResponse;
import com.kodlamaio.rentACar.business.responses.individuals.GetIndividualCustomerResponse;
import com.kodlamaio.rentACar.core.utilities.adapters.abstracts.FindeksService;
import com.kodlamaio.rentACar.core.utilities.adapters.abstracts.PersonCheckService;
import com.kodlamaio.rentACar.core.utilities.exceptions.BusinessException;
import com.kodlamaio.rentACar.core.utilities.mapping.ModelMapperService;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;
import com.kodlamaio.rentACar.core.utilities.results.SuccessDataResult;
import com.kodlamaio.rentACar.core.utilities.results.SuccessResult;
import com.kodlamaio.rentACar.dataAccess.abstracts.IndividualCustomerRepository;
import com.kodlamaio.rentACar.entities.concretes.IndividualCustomer;

@Service
public class IndividualCustomerManager implements UserService {

	private IndividualCustomerRepository individualCustomerRepository;
	private ModelMapperService mapperService;
	private PersonCheckService personCheckService;

	public IndividualCustomerManager(IndividualCustomerRepository individualCustomerRepository, ModelMapperService mapperService,
			PersonCheckService personCheckService, FindeksService findeksService) {
		this.individualCustomerRepository = individualCustomerRepository;
		this.mapperService = mapperService;
		this.personCheckService = personCheckService;
	}

	@Override
	public Result add(CreateIndividualCustomerRequest createIndividualRequest) throws NumberFormatException, RemoteException {
		checkUserNationalityFromRepository(createIndividualRequest.getNationality());
		checkIfUserExistsByNationalityFromMernis(createIndividualRequest);
		IndividualCustomer individualCustomer = this.mapperService.forRequest().map(createIndividualRequest, IndividualCustomer.class);
		this.individualCustomerRepository.save(individualCustomer);
		return new SuccessResult("USER.ADDED");
	}

	@Override
	public Result delete(DeleteIndividualCustomerRequest deleteIndividualRequest) {
		this.individualCustomerRepository.deleteById(deleteIndividualRequest.getIndividualCustomerId());
		return new SuccessResult("USER.DELETED");
	}

	@Override
	public Result update(UpdateIndividualCustomerRequest updateIndividualRequest) {
		IndividualCustomer individualCustomer = this.mapperService.forRequest().map(updateIndividualRequest, IndividualCustomer.class);
		this.individualCustomerRepository.save(individualCustomer);
		return new SuccessResult("USER.UPDATED");
	}

	@Override
	public DataResult<List<GetAllIndividualCustomerResponse>> getAll() {
		List<IndividualCustomer> users = this.individualCustomerRepository.findAll();
		
		List<GetAllIndividualCustomerResponse> response = users.stream().map(user->this.mapperService.forResponse()
				.map(user, GetAllIndividualCustomerResponse.class)).collect(Collectors.toList());
		return new SuccessDataResult<List<GetAllIndividualCustomerResponse>>(response);
	}

	@Override
	public DataResult<GetIndividualCustomerResponse> getById(int id) {
		IndividualCustomer user = this.individualCustomerRepository.findById(id).get();
		
		GetIndividualCustomerResponse response = this.mapperService.forResponse().map(user, GetIndividualCustomerResponse.class);
		return new SuccessDataResult<GetIndividualCustomerResponse>(response);
	}

	private void checkIfUserExistsByNationalityFromMernis(CreateIndividualCustomerRequest createIndividualRequest) throws NumberFormatException, RemoteException {
		if (!personCheckService.checkPerson(createIndividualRequest)) {
			throw new BusinessException("USER.IS.NOT.EXISTS.MERNIS");
		}
	}

	@Override
	public DataResult<List<GetAllIndividualCustomerResponse>> getAll(Integer pageNo, Integer pageSize) {
		Pageable pageable = PageRequest.of(pageNo-1, pageSize);
		List<IndividualCustomer> users = this.individualCustomerRepository.findAll(pageable).getContent();
		
		List<GetAllIndividualCustomerResponse> response = users.stream().map(user->this.mapperService.forResponse()
				.map(user, GetAllIndividualCustomerResponse.class)).collect(Collectors.toList());
		return new SuccessDataResult<List<GetAllIndividualCustomerResponse>>(response);
	}
	
	private void checkUserNationalityFromRepository(String nationality) {
		IndividualCustomer user = this.individualCustomerRepository.findByNationality(nationality);
		if (user != null) {
			throw new BusinessException("USER.EXISTS.REPOSITORY");
		}
	}
}
