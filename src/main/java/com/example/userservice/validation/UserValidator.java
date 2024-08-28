package com.example.userservice.validation;

import com.example.userservice.dto.UserRequestDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return UserRequestDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserRequestDTO user = (UserRequestDTO) target;

        // Validate name
        if (StringUtils.isBlank(user.getName())) {
            errors.rejectValue("name", "user.name.empty", "Name is required and cannot be empty or blank.");
        } else if (user.getName().length() < 3) {
            errors.rejectValue("name", "user.name.short", "Name must be at least 3 characters long.");
        }

        // Validate email
        if (StringUtils.isBlank(user.getEmail())) {
            errors.rejectValue("email", "user.email.empty", "Email is required and cannot be empty or blank.");
        } else if (!user.getEmail().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            errors.rejectValue("email", "user.email.invalid", "Email is not valid.");
        }

        // Validate password
        if (StringUtils.isBlank(user.getPassword())) {
            errors.rejectValue("password", "user.password.empty", "Password is required and cannot be empty or blank.");
        } else if (user.getPassword().length() < 8) {
            errors.rejectValue("password", "user.password.short", "Password must be at least 8 characters long.");
        }
    }
}
