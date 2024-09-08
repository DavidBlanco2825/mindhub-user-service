package com.example.userservice.service.impl;

import com.example.userservice.config.JwtUtils;
import com.example.userservice.dto.LoginRequest;
import com.example.userservice.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final ReactiveAuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @Override
    public Mono<String> authenticateUser(LoginRequest loginRequest) {
        return authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()))
                .map(auth -> {
                    UserDetails userDetails = (UserDetails) auth.getPrincipal();
                    return jwtUtils.generateToken(userDetails.getUsername());
                });
    }
}
