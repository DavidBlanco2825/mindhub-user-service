package com.example.userservice.service;

import com.example.userservice.dto.UserRequestDTO;
import com.example.userservice.dto.UserResponseDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {

    Mono<UserResponseDTO> createUser(UserRequestDTO userRequestDTO);

    Mono<UserResponseDTO> getUserById(Long id);

    Flux<UserResponseDTO> getAllUsers();

    Mono<UserResponseDTO> updateUser(Long id, UserRequestDTO userRequestDTO);

    Mono<Void> deleteUser(Long id);
}
