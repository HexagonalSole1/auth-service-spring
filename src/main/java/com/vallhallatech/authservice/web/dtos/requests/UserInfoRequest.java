package com.vallhallatech.authservice.web.dtos.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;


@Getter
public class UserInfoRequest {

    @NotBlank(message = "Email is required")
    @Email(message = "Email is invalid")
    private String email;
}
