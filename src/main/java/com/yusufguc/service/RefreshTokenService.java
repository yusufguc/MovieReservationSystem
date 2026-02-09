package com.yusufguc.service;

import com.yusufguc.dto.request.RefreshTokenRequest;
import com.yusufguc.dto.response.RegisterResponse;

public interface RefreshTokenService {
    public RegisterResponse refreshToken(RefreshTokenRequest request);
}
