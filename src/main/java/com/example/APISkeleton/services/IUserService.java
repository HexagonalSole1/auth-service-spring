package com.example.APISkeleton.services;

import com.example.APISkeleton.persistance.entities.User;
import com.example.APISkeleton.web.dtos.requests.CreateUserRequest;
import com.example.APISkeleton.web.dtos.requests.UserInfoRequest;
import com.example.APISkeleton.web.dtos.responses.BaseResponse;

import java.util.Optional;

public interface IUserService {
    BaseResponse create(CreateUserRequest request);
    BaseResponse getUserInfo(String email);

    User getByEmail(String email);
    Optional<User> getOptionalUserByEmail(String email);


}
