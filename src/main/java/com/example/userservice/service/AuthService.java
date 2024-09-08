package com.example.userservice.service;

import com.example.userservice.dto.LoginRequest;
import reactor.core.publisher.Mono;

public interface AuthService {

    Mono<String> authenticateUser(LoginRequest loginRequest);
}
