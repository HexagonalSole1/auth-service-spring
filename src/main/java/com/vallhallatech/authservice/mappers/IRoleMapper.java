package com.vallhallatech.authservice.mappers;

import com.vallhallatech.authservice.persistance.entities.projections.IRoleProjection;
import com.vallhallatech.authservice.web.dtos.responses.GetRoleResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IRoleMapper {
    GetRoleResponse toGetRoleResponse(IRoleProjection roleProjection);
}
