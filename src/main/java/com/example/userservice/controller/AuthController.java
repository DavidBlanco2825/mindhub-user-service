package com.example.userservice.controller;

import com.example.userservice.dto.LoginRequest;
import com.example.userservice.dto.UserRequestDTO;
import com.example.userservice.service.AuthService;
import com.example.userservice.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "AuthController", description = "Operations related to user authentication and registration")
@AllArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthService authService;

    @PostMapping("/register")
    @Operation(summary = "Register User", description = "Registers a new user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User successfully registered.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(type = "string", example = "User registered successfully"))),
            @ApiResponse(responseCode = "400", description = "Invalid input. Validation errors or missing fields.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(type = "string", example = "Invalid user data."))),
            @ApiResponse(responseCode = "409", description = "Conflict. Username or email already exists.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(type = "string", example = "Username or email already in use."))),
            @ApiResponse(responseCode = "500", description = "Internal server error.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(type = "string", example = "An error occurred while processing your request.")))
    })
    public Mono<ResponseEntity<String>> registerUser(
            @RequestBody
            @Parameter(description = "User data for registration", required = true) UserRequestDTO userRequestDTO) {
        return userService.createUser(userRequestDTO)
                .map(createdUser -> ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully"));
    }

    @PostMapping("/login")
    @Operation(summary = "Authenticate User", description = "Authenticates a user and returns a JWT token.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User authenticated successfully."),
            @ApiResponse(responseCode = "400", description = "Invalid credentials.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(type = "string", example = "Invalid username or password."))),
            @ApiResponse(responseCode = "500", description = "Internal server error.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(type = "string", example = "An error occurred while processing your request.")))
    })
    public Mono<ResponseEntity<String>> login(
            @RequestBody
            @Parameter(description = "Login request with username and password", required = true) LoginRequest loginRequest) {
        return authService.authenticateUser(loginRequest)
                .map(ResponseEntity::ok)
                .onErrorResume(e -> Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()));
    }
}
