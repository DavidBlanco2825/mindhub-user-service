package com.example.userservice.service;

import com.example.userservice.dto.UserRequestDTO;
import com.example.userservice.dto.UserResponseDTO;
import com.example.userservice.entity.UserEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {

    Mono<UserResponseDTO> createUser(UserRequestDTO userRequestDTO);

    Mono<UserResponseDTO> getUserById(Long id);

    Mono<UserEntity> getUserByEmail(String email);

    Flux<UserResponseDTO> getAllUsers();

    Mono<UserResponseDTO> updateUser(Long id, UserRequestDTO userRequestDTO);

    Mono<Void> deleteUser(Long id);
}
