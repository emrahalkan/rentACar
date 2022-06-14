package com.kodlamaio.rentACar.api.controllers;

import java.rmi.RemoteException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kodlamaio.rentACar.business.abstracts.UserService;
import com.kodlamaio.rentACar.business.requests.users.CreateUserRequest;
import com.kodlamaio.rentACar.business.requests.users.DeleteUserRequest;
import com.kodlamaio.rentACar.business.requests.users.UpdateUserRequest;
import com.kodlamaio.rentACar.business.responses.users.GetAllUsersResponse;
import com.kodlamaio.rentACar.business.responses.users.GetUserResponse;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/users")
public class UsersController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/add")
	public Result add(@RequestBody CreateUserRequest createUserRequest) throws NumberFormatException, RemoteException  {
		return this.userService.add(createUserRequest);
	}
	
	@GetMapping("/getAll")
	public DataResult<List<GetAllUsersResponse>> getAll(){
		return userService.getAll();
	}
	
	@GetMapping("/getAllByPage")
	public DataResult<List<GetAllUsersResponse>> getAll(@RequestParam int pageNo, int pageSize){
		return userService.getAll(pageNo, pageSize);
	}
	
	@GetMapping("/getById")
	public DataResult<GetUserResponse> getById(@RequestParam int id){
		return this.userService.getById(id);
	}
	
	@PostMapping("/delete")
	public Result delete(@RequestBody DeleteUserRequest deleteUserRequest) {
		return this.userService.delete(deleteUserRequest);
	}
	
	@PostMapping("/update")
	public Result update(@RequestBody UpdateUserRequest updateUserRequest) {
		return this.userService.update(updateUserRequest);
	}

}
