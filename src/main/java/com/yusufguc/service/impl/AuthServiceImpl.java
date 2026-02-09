package com.yusufguc.service.impl;

import com.yusufguc.dto.request.RegisterRequest;
import com.yusufguc.dto.response.RegisterResponse;
import com.yusufguc.exception.base.BaseException;
import com.yusufguc.exception.message.ErrorMessage;
import com.yusufguc.exception.message.MessageType;
import com.yusufguc.jwt.JwtService;
import com.yusufguc.model.RefreshToken;
import com.yusufguc.model.User;
import com.yusufguc.model.enums.Role;
import com.yusufguc.repository.RefreshTokenRepository;
import com.yusufguc.repository.UserRepository;
import com.yusufguc.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public RegisterResponse register(RegisterRequest registerRequest) {

        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            throw new BaseException(
                    new ErrorMessage(MessageType.USERNAME_ALREADY_EXISTS, registerRequest.getUsername())
            );
        }

        User user=new User();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setRole(Role.USER);

        userRepository.save(user);

        String token = jwtService.generateToken(user);

        RefreshToken refreshToken = createRefreshToken(user);
        refreshTokenRepository.save(refreshToken);

        return  new RegisterResponse(token,refreshToken.getRefreshToken());
    }

    @Override
    public RegisterResponse authenticate(RegisterRequest request) {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
        ));

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String accessToken = jwtService.generateToken(user);

        RefreshToken refreshToken = createRefreshToken(user);
        refreshTokenRepository.save(refreshToken);

        return new RegisterResponse(accessToken,refreshToken.getRefreshToken());
    }

    private RefreshToken createRefreshToken(User user){
        RefreshToken refreshToken=new RefreshToken();
        refreshToken.setRefreshToken(UUID.randomUUID().toString());
        refreshToken.setExpireDate(new Date(System.currentTimeMillis() +1000*60*60*4));
        refreshToken.setUser(user);

        return  refreshToken;
    }
}
