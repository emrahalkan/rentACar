package com.kodlamaio.rentACar.business.abstracts;

import java.rmi.RemoteException;
import java.util.List;

import com.kodlamaio.rentACar.business.requests.users.CreateUserRequest;
import com.kodlamaio.rentACar.business.requests.users.DeleteUserRequest;
import com.kodlamaio.rentACar.business.requests.users.UpdateUserRequest;
import com.kodlamaio.rentACar.business.responses.users.GetAllUsersResponse;
import com.kodlamaio.rentACar.business.responses.users.GetUserResponse;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;

public interface UserService {
	Result add(CreateUserRequest createUserRequest) throws NumberFormatException, RemoteException;
	Result delete(DeleteUserRequest deleteUserRequest);
	Result update(UpdateUserRequest updateUserRequest);
	DataResult<List<GetAllUsersResponse>> getAll();
	DataResult<GetUserResponse> getById(int id);
	DataResult<List<GetAllUsersResponse>> getAll(Integer pageNo, Integer pageSize);
}
