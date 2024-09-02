package com.example.userservice.service.impl;

import com.example.userservice.dto.UserRequestDTO;
import com.example.userservice.dto.UserResponseDTO;
import com.example.userservice.entity.UserEntity;
import com.example.userservice.exception.BadRequestException;
import com.example.userservice.exception.DataAlreadyExistsException;
import com.example.userservice.exception.UserNotFoundException;
import com.example.userservice.mapper.UserMapper;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.validation.UserValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static com.example.userservice.commons.Constants.EMAIL_ALREADY_EXISTS;
import static com.example.userservice.commons.Constants.USER_NOT_FOUND_ID;
import static org.mockito.Mockito.when;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private UserValidator userValidator;

    @InjectMocks
    private UserServiceImpl userService;

    private UserEntity userEntity;
    private UserRequestDTO userRequestDTO;
    private UserResponseDTO userResponseDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setName("John Doe");
        userEntity.setEmail("john.doe@example.com");
        userEntity.setPassword("password123");

        userRequestDTO = new UserRequestDTO("John Doe", "john.doe@example.com", "password123");
        userResponseDTO = new UserResponseDTO(1L, "John Doe", "john.doe@example.com");
    }

    @Test
    void createUser_ShouldReturnUserResponseDTO_WhenEmailDoesNotExist() {
        when(userRepository.existsByEmail(userRequestDTO.getEmail())).thenReturn(Mono.just(false));
        when(userMapper.toEntity(userRequestDTO)).thenReturn(userEntity);
        when(userRepository.save(userEntity)).thenReturn(Mono.just(userEntity));
        when(userMapper.toResponseDto(userEntity)).thenReturn(userResponseDTO);

        Errors errors = new BeanPropertyBindingResult(userRequestDTO, "userRequestDTO");
        userValidator.validate(userRequestDTO, errors);
        if (errors.hasErrors()) {
            throw new BadRequestException(errors.getFieldErrors());
        }

        Mono<UserResponseDTO> result = userService.createUser(userRequestDTO);

        StepVerifier.create(result)
                .expectNext(userResponseDTO)
                .verifyComplete();
    }

    @Test
    void createUser_ShouldReturnError_WhenEmailAlreadyExists() {
        when(userRepository.existsByEmail(userRequestDTO.getEmail())).thenReturn(Mono.just(true));

        Mono<UserResponseDTO> result = userService.createUser(userRequestDTO);

        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable instanceof DataAlreadyExistsException &&
                        throwable.getMessage().equals(EMAIL_ALREADY_EXISTS))
                .verify();
    }

    @Test
    void getUserById_ShouldReturnUserResponseDTO_WhenUserExists() {
        when(userRepository.findById(1L)).thenReturn(Mono.just(userEntity));
        when(userMapper.toResponseDto(userEntity)).thenReturn(userResponseDTO);

        Mono<UserResponseDTO> result = userService.getUserById(1L);

        StepVerifier.create(result)
                .expectNext(userResponseDTO)
                .verifyComplete();
    }

    @Test
    void getUserById_ShouldReturnError_WhenUserDoesNotExist() {
        when(userRepository.findById(1L)).thenReturn(Mono.empty());

        Mono<UserResponseDTO> result = userService.getUserById(1L);

        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable instanceof UserNotFoundException &&
                        throwable.getMessage().equals(USER_NOT_FOUND_ID + 1))
                .verify();
    }

    @Test
    void getAllUsers_ShouldReturnUserResponseDTOs() {
        when(userRepository.findAll()).thenReturn(Flux.just(userEntity));
        when(userMapper.toResponseDto(userEntity)).thenReturn(userResponseDTO);

        Flux<UserResponseDTO> result = userService.getAllUsers();

        StepVerifier.create(result)
                .expectNext(userResponseDTO)
                .verifyComplete();
    }

    @Test
    void updateUser_ShouldReturnUpdatedUserResponseDTO_WhenUserExists() {
        when(userRepository.findById(1L)).thenReturn(Mono.just(userEntity));
        when(userRepository.save(userEntity)).thenReturn(Mono.just(userEntity));
        when(userMapper.toResponseDto(userEntity)).thenReturn(userResponseDTO);

        Errors errors = new BeanPropertyBindingResult(userRequestDTO, "userRequestDTO");
        userValidator.validate(userRequestDTO, errors);
        if (errors.hasErrors()) {
            throw new BadRequestException(errors.getFieldErrors());
        }

        Mono<UserResponseDTO> result = userService.updateUser(1L, userRequestDTO);

        StepVerifier.create(result)
                .expectNext(userResponseDTO)
                .verifyComplete();
    }

    @Test
    void updateUser_ShouldReturnError_WhenUserDoesNotExist() {
        when(userRepository.findById(1L)).thenReturn(Mono.empty());

        Mono<UserResponseDTO> result = userService.updateUser(1L, userRequestDTO);

        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable instanceof UserNotFoundException &&
                        throwable.getMessage().equals(USER_NOT_FOUND_ID + 1))
                .verify();
    }

    @Test
    void deleteUser_ShouldDeleteUser_WhenUserExists() {
        when(userRepository.existsById(1L)).thenReturn(Mono.just(true));
        when(userRepository.deleteById(1L)).thenReturn(Mono.empty());

        Mono<Void> result = userService.deleteUser(1L);

        StepVerifier.create(result)
                .verifyComplete();
    }

    @Test
    void deleteUser_ShouldReturnError_WhenUserDoesNotExist() {
        when(userRepository.existsById(1L)).thenReturn(Mono.just(false));

        Mono<Void> result = userService.deleteUser(1L);

        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable instanceof UserNotFoundException &&
                        throwable.getMessage().equals(USER_NOT_FOUND_ID + 1))
                .verify();
    }

}