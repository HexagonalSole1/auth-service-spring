package com.vallhallatech.authservice.mappers;

import com.vallhallatech.authservice.persistance.entities.User;
import com.vallhallatech.authservice.web.dtos.responses.CreateUserResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IUserMapper {
    CreateUserResponse toCreateUserResponse(User user);
}
