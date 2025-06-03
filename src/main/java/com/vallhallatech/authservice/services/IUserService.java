package com.vallhallatech.authservice.services;

import com.vallhallatech.authservice.persistance.entities.User;
import com.vallhallatech.authservice.web.dtos.requests.CreateUserRequest;
import com.vallhallatech.authservice.web.dtos.responses.BaseResponse;

import java.util.Optional;

public interface IUserService {
    BaseResponse create(CreateUserRequest request);
    BaseResponse getUserInfo(String email);

    User getByEmail(String email);
    Optional<User> getOptionalUserByEmail(String email);


}
