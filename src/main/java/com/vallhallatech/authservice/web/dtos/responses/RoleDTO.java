package com.vallhallatech.authservice.web.dtos.responses;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleDTO {

    private String name;

    public RoleDTO( String name) {
        this.name = name;
    }
}
