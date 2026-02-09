package com.yusufguc.controller.impl;

import com.yusufguc.controller.AuthController;
import com.yusufguc.dto.request.RefreshTokenRequest;
import com.yusufguc.dto.request.RegisterRequest;
import com.yusufguc.dto.response.RegisterResponse;
import com.yusufguc.model.RootEntity;
import com.yusufguc.service.AuthService;
import com.yusufguc.service.RefreshTokenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthControllerImpl extends RestBaseController implements AuthController {

    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/register")
    public RootEntity<RegisterResponse> register(@RequestBody @Valid RegisterRequest request){
        return ok(authService.register(request)) ;
    }

    @PostMapping("/authenticate")
    public RootEntity<RegisterResponse> authenticate(@RequestBody @Valid RegisterRequest request){
        return ok(authService.authenticate(request)) ;
    }

    @PostMapping("/refreshToken")
    @Override
    public RootEntity<RegisterResponse> refreshToken(@RequestBody RefreshTokenRequest request) {
        return ok(refreshTokenService.refreshToken(request));
    }
}
