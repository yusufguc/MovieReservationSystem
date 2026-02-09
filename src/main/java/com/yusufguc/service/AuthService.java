package com.yusufguc.service;

import com.yusufguc.dto.request.RegisterRequest;
import com.yusufguc.dto.response.RegisterResponse;

public interface AuthService {

    public RegisterResponse register(RegisterRequest registerRequest);

    public RegisterResponse authenticate(RegisterRequest request);
}
