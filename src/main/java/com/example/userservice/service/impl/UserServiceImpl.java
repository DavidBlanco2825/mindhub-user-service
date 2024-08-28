package com.example.userservice.service.impl;

import com.example.userservice.dto.UserRequestDTO;
import com.example.userservice.dto.UserResponseDTO;
import com.example.userservice.entity.UserEntity;
import com.example.userservice.exception.BadRequestException;
import com.example.userservice.exception.UserNotFoundException;
import com.example.userservice.mapper.UserMapper;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.service.UserService;
import com.example.userservice.validation.UserValidator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.example.userservice.config.Constants.USER_NOT_FOUND_ID;

@Slf4j
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UserValidator userValidator;

    @Override
    public Mono<UserResponseDTO> createUser(UserRequestDTO userRequestDTO) {
        return validateUserRequest(userRequestDTO)
                .then(Mono.defer(() -> {
                    UserEntity user = userMapper.toEntity(userRequestDTO);
                    return userRepository.save(user)
                            .map(userMapper::toResponseDto).log();
                }));
    }

    private Mono<Void> validateUserRequest(UserRequestDTO userRequestDTO) {
        Errors errors = new BeanPropertyBindingResult(userRequestDTO, "userRequestDTO");
        userValidator.validate(userRequestDTO, errors);

        if (errors.hasErrors()) {
            return Mono.error(new BadRequestException(errors.getFieldErrors()));
        }

        return Mono.empty();
    }

    @Override
    public Mono<UserResponseDTO> getUserById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::toResponseDto)
                .switchIfEmpty(Mono.error(new UserNotFoundException(USER_NOT_FOUND_ID + id)));
    }

    @Override
    public Flux<UserResponseDTO> getAllUsers() {
        return userRepository.findAll()
                .map(userMapper::toResponseDto);
    }

    @Override
    public Mono<UserResponseDTO> updateUser(Long id, UserRequestDTO userRequestDTO) {
        return validateUserRequest(userRequestDTO)
                .then(userRepository.findById(id)
                        .switchIfEmpty(Mono.error(new UserNotFoundException(USER_NOT_FOUND_ID + id)))
                        .flatMap(existingUser -> updateExistingUser(existingUser, userRequestDTO))
                        .map(userMapper::toResponseDto));
    }

    private Mono<UserEntity> updateExistingUser(UserEntity existingUser, UserRequestDTO newUserData) {
        existingUser.setName(newUserData.getName());
        existingUser.setEmail(newUserData.getEmail());
        existingUser.setPassword(newUserData.getPassword());
        return userRepository.save(existingUser);
    }

    @Override
    public Mono<Void> deleteUser(Long id) {
        return userRepository.existsById(id)
                .flatMap(exists -> exists
                        ? userRepository.deleteById(id)
                        : Mono.error(new UserNotFoundException(USER_NOT_FOUND_ID + id)));
    }
}