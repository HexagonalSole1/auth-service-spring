package com.vallhallatech.authservice.services;

import com.vallhallatech.authservice.web.dtos.responses.GetRoleResponse;

import java.util.List;

public interface IUserRoleService {
    List<GetRoleResponse> getRolesByUserId(Long userId);
}
