package com.vallhallatech.authservice.web.controllers;

import com.vallhallatech.authservice.services.IUserService;
import com.vallhallatech.authservice.web.dtos.requests.CreateUserRequest;
import com.vallhallatech.authservice.web.dtos.responses.BaseResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
public class UserController {
    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<BaseResponse> create(@Valid @RequestBody CreateUserRequest request) {
        BaseResponse baseResponse = userService.create(request);

        return baseResponse.buildResponseEntity();
    }
    @GetMapping("/user/email/{email}")
    public ResponseEntity<BaseResponse> findUserInfoByEmail(@PathVariable String email) {
        BaseResponse baseResponse = userService.getUserInfo(email);
        return baseResponse.buildResponseEntity();
    }


}
