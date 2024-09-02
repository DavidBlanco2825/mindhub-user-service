package com.example.userservice.controller;

import com.example.userservice.dto.UserRequestDTO;
import com.example.userservice.dto.UserResponseDTO;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@AllArgsConstructor
@RequestMapping("/api/users")
@Tag(name = "UserController", description = "Operations related to user management")
public class UserController {

    private final UserService userService;

    @PostMapping
    @Operation(summary = "Create a new User", description = "Creates a new user and returns the user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User successfully created.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(type = "string", example = "Invalid user data."))),
            @ApiResponse(responseCode = "409", description = "Conflict.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(type = "string", example = "There is a user already created with that email."))),
            @ApiResponse(responseCode = "500", description = "Internal server error.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(type = "string", example = "An error occurred while processing your request.")))
    })
    public Mono<ResponseEntity<UserResponseDTO>> createUser(
            @RequestBody
            @Parameter(description = "User data for the new user", required = true) UserRequestDTO userRequestDTO) {
        return userService.createUser(userRequestDTO)
                .map(createdUser -> ResponseEntity.status(HttpStatus.CREATED).body(createdUser));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get User by ID", description = "Returns user information based on the provided ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User successfully returned.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "User not found.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(type = "string", example = "User not found."))),
            @ApiResponse(responseCode = "500", description = "Internal server error.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(type = "string", example = "An error occurred while processing your request.")))
    })
    public Mono<ResponseEntity<UserResponseDTO>> getUserById(
            @PathVariable("id")
            @Parameter(description = "ID of the user to be retrieved", required = true, example = "1") Long id) {
        return userService.getUserById(id)
                .map(existingUser -> ResponseEntity.status(HttpStatus.OK).body(existingUser));
    }

    @GetMapping
    @Operation(summary = "Get All Users", description = "Returns all users.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users successfully returned.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponseDTO.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(type = "string", example = "An error occurred while processing your request.")))
    })
    public Mono<ResponseEntity<Flux<UserResponseDTO>>> getAllUsers() {
        return userService.getAllUsers()
                .collectList() // Collect to a List first
                .flatMap(users -> {
                    if (users.isEmpty()) {
                        return Mono.just(ResponseEntity.noContent().build());
                    } else {
                        return Mono.just(ResponseEntity.ok(Flux.fromIterable(users)));
                    }
                });
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update User", description = "Updates an existing user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User successfully updated.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "User not found.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(type = "string", example = "User not found."))),
            @ApiResponse(responseCode = "500", description = "Internal server error.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(type = "string", example = "An error occurred while processing your request.")))
    })
    public Mono<ResponseEntity<UserResponseDTO>> updateUser(
            @PathVariable("id")
            @Parameter(description = "ID of the user to be updated", required = true, example = "1") Long id,
            @RequestBody
            @Parameter(description = "Updated user data", required = true) UserRequestDTO userRequestDTO) {
        return userService.updateUser(id, userRequestDTO)
                .map(updateUser -> ResponseEntity.status(HttpStatus.OK).body(updateUser));

    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete User", description = "Deletes an existing user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User successfully deleted."),
            @ApiResponse(responseCode = "404", description = "User not found.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(type = "string", example = "User not found."))),
            @ApiResponse(responseCode = "500", description = "Internal server error.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(type = "string", example = "An error occurred while processing your request.")))
    })
    public Mono<ResponseEntity<Void>> deleteUser(
            @PathVariable("id")
            @Parameter(description = "ID of the user to be deleted", required = true, example = "1") Long id) {
        return userService.deleteUser(id)
                .then(Mono.just(ResponseEntity.status(HttpStatus.NO_CONTENT).build()));
    }
}
