package com.vallhallatech.authservice.services.impls;

import com.vallhallatech.authservice.mappers.IRoleMapper;
import com.vallhallatech.authservice.persistance.repositories.IUserRoleRepository;
import com.vallhallatech.authservice.services.IUserRoleService;
import com.vallhallatech.authservice.web.dtos.responses.GetRoleResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserRoleServiceImpl implements IUserRoleService {
    private final IUserRoleRepository repository;
    private final IRoleMapper roleMapper;

    public UserRoleServiceImpl(IUserRoleRepository userRoleRepository, IRoleMapper roleMapper) {
        this.repository = userRoleRepository;
        this.roleMapper = roleMapper;
    }

    public List<GetRoleResponse> getRolesByUserId(Long userId) {

        return repository.getRolesByUserId(userId)
                .stream()
                .map(roleMapper::toGetRoleResponse)
                .collect(Collectors.toList());
    }
}
