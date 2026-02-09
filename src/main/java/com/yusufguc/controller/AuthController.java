package com.yusufguc.controller;

import com.yusufguc.dto.request.RefreshTokenRequest;
import com.yusufguc.dto.request.RegisterRequest;
import com.yusufguc.dto.response.RegisterResponse;
import com.yusufguc.model.RootEntity;

public interface AuthController {
    public RootEntity<RegisterResponse> register( RegisterRequest request);

    public RootEntity<RegisterResponse> authenticate( RegisterRequest request);

    public RootEntity<RegisterResponse> refreshToken(RefreshTokenRequest request);

}
