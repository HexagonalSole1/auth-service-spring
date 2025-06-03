package com.vallhallatech.authservice.services;

import com.vallhallatech.authservice.web.dtos.requests.AuthenticateRequest;
import com.vallhallatech.authservice.web.dtos.requests.RefreshTokenRequest;
import com.vallhallatech.authservice.web.dtos.responses.BaseResponse;

public interface IAuthService {
    BaseResponse authenticate(AuthenticateRequest request);
    BaseResponse refreshToken(RefreshTokenRequest request);
}
