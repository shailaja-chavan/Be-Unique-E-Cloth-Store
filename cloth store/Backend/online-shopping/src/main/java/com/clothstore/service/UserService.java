package com.clothstore.service;

import java.util.List;

import com.clothstore.dto.AddUserRequest;
import com.clothstore.dto.UserLoginRequest;
import com.clothstore.model.User;

public interface UserService {
	
	User registerUser(AddUserRequest userRequest);

    void deleteUserById(int userId);

    User loginUser(UserLoginRequest loginRequest);

    List<User> getAllUsers();

    List<User> getAllDeliveryPersons();

}
