package com.yusufguc.config;

import com.yusufguc.jwt.AuthEntryPoint;
import com.yusufguc.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    public static final String AUTHENTICATE = "/auth/authenticate";
    public static final String REGISTER = "/auth/register";
    public static final String REFRESH_TOKEN="/auth/refreshToken";
    public static final String[] SWAGGER_PATHS={
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html"};


    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthEntryPoint authEntryPoint;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers(AUTHENTICATE, REGISTER,REFRESH_TOKEN).permitAll()
                                .requestMatchers(SWAGGER_PATHS).permitAll()
                                .anyRequest().authenticated()
                )
                .exceptionHandling(ex ->ex
                        .authenticationEntryPoint(authEntryPoint)
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}