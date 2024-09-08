package com.example.userservice.config;

import com.example.userservice.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements ReactiveUserDetailsService {

    private final UserService userService;

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return userService.getUserByEmail(username)
                .map(userEntity -> new User(userEntity.getEmail(), userEntity.getPassword(), new ArrayList<>()));
    }
}
