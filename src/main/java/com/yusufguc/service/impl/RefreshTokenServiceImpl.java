package com.yusufguc.service.impl;

import com.yusufguc.dto.request.RefreshTokenRequest;
import com.yusufguc.dto.response.RegisterResponse;
import com.yusufguc.exception.base.BaseException;
import com.yusufguc.exception.message.ErrorMessage;
import com.yusufguc.exception.message.MessageType;
import com.yusufguc.jwt.JwtService;
import com.yusufguc.model.RefreshToken;
import com.yusufguc.model.User;
import com.yusufguc.repository.RefreshTokenRepository;
import com.yusufguc.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtService jwtService;

    private boolean isRefreshTokenExpired(Date expireDate){
        return new  Date().after(expireDate);
    }

    private RefreshToken createRefreshToken(User user){

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setRefreshToken(UUID.randomUUID().toString());
        refreshToken.setExpireDate(new Date(System.currentTimeMillis() + 1000*60*60*4));
        refreshToken.setUser(user);
        return refreshTokenRepository.save(refreshToken);
    }

    @Override
    public RegisterResponse refreshToken(RefreshTokenRequest request){

        RefreshToken refreshToken = refreshTokenRepository
                .findByRefreshToken(request.getRefreshToken())
                .orElseThrow(() ->
                        new BaseException(new ErrorMessage(
                                MessageType.REFRESH_TOKEN_INVALID,
                                request.getRefreshToken()
                        )));

        if (isRefreshTokenExpired(refreshToken.getExpireDate())){
            throw new BaseException(new ErrorMessage(
                    MessageType.REFRESH_TOKEN_EXPIRED,
                    request.getRefreshToken()
            ));
        }
        String accessToken =
                jwtService.generateToken(refreshToken.getUser());
        RefreshToken newRefreshToken =
                createRefreshToken(refreshToken.getUser());
        return new RegisterResponse(
                accessToken,
                newRefreshToken.getRefreshToken()
        );
    }
}


