package com.example.budget.service;

import com.example.budget.domein.models.service.UserServiceModel;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    UserServiceModel registerUser(UserServiceModel userServiceModel);

    UserServiceModel findUserByUserName(String username);

  //  UserServiceImpl editUserProfile(UserServiceImpl userServiceModel, String oldPassword);

    List<UserServiceModel> findAllUsers();


}