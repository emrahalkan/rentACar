package com.kodlamaio.rentACar.business.concretes;

import java.rmi.RemoteException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.kodlamaio.rentACar.business.abstracts.UserService;
import com.kodlamaio.rentACar.business.requests.users.CreateUserRequest;
import com.kodlamaio.rentACar.business.requests.users.DeleteUserRequest;
import com.kodlamaio.rentACar.business.requests.users.UpdateUserRequest;
import com.kodlamaio.rentACar.business.responses.users.GetAllUsersResponse;
import com.kodlamaio.rentACar.business.responses.users.GetUserResponse;
import com.kodlamaio.rentACar.core.utilities.adapters.abstracts.PersonCheckService;
import com.kodlamaio.rentACar.core.utilities.exceptions.BusinessException;
import com.kodlamaio.rentACar.core.utilities.mapping.ModelMapperService;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;
import com.kodlamaio.rentACar.core.utilities.results.SuccessDataResult;
import com.kodlamaio.rentACar.core.utilities.results.SuccessResult;
import com.kodlamaio.rentACar.dataAccess.abstracts.UserRepository;
import com.kodlamaio.rentACar.entities.concretes.User;

@Service
public class UserManager implements UserService {

	private UserRepository userRepository;
	private ModelMapperService mapperService;
	private PersonCheckService personCheckService;

	public UserManager(UserRepository userRepository, ModelMapperService mapperService,
			PersonCheckService personCheckService) {
		this.userRepository = userRepository;
		this.mapperService = mapperService;
		this.personCheckService = personCheckService;
	}

	@Override
	public Result add(CreateUserRequest createUserRequest) throws NumberFormatException, RemoteException {
		checkIfUserExistsByNationality(createUserRequest);
		User user = this.mapperService.forRequest().map(createUserRequest, User.class);
		this.userRepository.save(user);
		return new SuccessResult("USER.ADDED");
	}
	

	@Override
	public Result delete(DeleteUserRequest deleteUserRequest) {
		this.userRepository.deleteById(deleteUserRequest.getId());
		return new SuccessResult("USER.DELETED");
	}

	@Override
	public Result update(UpdateUserRequest updateUserRequest) {
		User user = this.mapperService.forRequest().map(updateUserRequest, User.class);
		this.userRepository.save(user);
		return new SuccessResult("USER.UPDATED");
	}

	@Override
	public DataResult<List<GetAllUsersResponse>> getAll() {
		List<User> users = this.userRepository.findAll();
		
		List<GetAllUsersResponse> response = users.stream().map(user->this.mapperService.forResponse()
				.map(user, GetAllUsersResponse.class)).collect(Collectors.toList());
		return new SuccessDataResult<List<GetAllUsersResponse>>(response);
	}

	@Override
	public DataResult<GetUserResponse> getById(int id) {
		User user = this.userRepository.findById(id);
		
		GetUserResponse response = this.mapperService.forResponse().map(user, GetUserResponse.class);
		return new SuccessDataResult<GetUserResponse>(response);
	}

	private void checkIfUserExistsByNationality(CreateUserRequest createUserRequest) throws NumberFormatException, RemoteException {
		if (!personCheckService.checkPerson(createUserRequest)) {
			throw new BusinessException("USER.WASN'T.ADDED");
		}
	}

	@Override
	public DataResult<List<GetAllUsersResponse>> getAll(Integer pageNo, Integer pageSize) {
		Pageable pageable = PageRequest.of(pageNo-1, pageSize);
		List<User> users = this.userRepository.findAll(pageable).getContent();
		
		List<GetAllUsersResponse> response = users.stream().map(user->this.mapperService.forResponse()
				.map(user, GetAllUsersResponse.class)).collect(Collectors.toList());
		return new SuccessDataResult<List<GetAllUsersResponse>>(response);
	}

}
