package com.example.userservice.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @io.swagger.v3.oas.annotations.info.Info(
                title = "User Service",
                version = "1.0",
                description = "API documentation for the User Service"
        )
)
public class SwaggerConfig {
}
