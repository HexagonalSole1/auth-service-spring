package com.vallhallatech.authservice.web.dtos.responses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RefreshTokenResponse {
    private String accessToken;
    private String refreshToken;
}
