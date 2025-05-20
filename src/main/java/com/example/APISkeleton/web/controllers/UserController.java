package com.example.APISkeleton.web.controllers;

import com.example.APISkeleton.services.IUserService;
import com.example.APISkeleton.web.dtos.requests.CreateUserRequest;
import com.example.APISkeleton.web.dtos.requests.UserInfoRequest;
import com.example.APISkeleton.web.dtos.responses.BaseResponse;
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
