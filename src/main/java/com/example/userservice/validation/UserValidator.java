package com.example.userservice.validation;

import com.example.userservice.dto.UserRequestDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import static com.example.userservice.commons.Constants.EMAIL_IS_NOT_VALID;
import static com.example.userservice.commons.Constants.EMAIL_IS_REQUIRED;
import static com.example.userservice.commons.Constants.NAME_IS_REQUIRED;
import static com.example.userservice.commons.Constants.NAME_MIN_LENGTH;
import static com.example.userservice.commons.Constants.PASSWORD_IS_REQUIRED;
import static com.example.userservice.commons.Constants.PASSWORD_MIN_LENGTH;

@Component
public class UserValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return UserRequestDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserRequestDTO user = (UserRequestDTO) target;

        int nameMinLength = 3;
        int passwordMinLength = 8;

        // Validate name
        if (StringUtils.isBlank(user.getName())) {
            errors.rejectValue("name", "user.name.empty", NAME_IS_REQUIRED);
        } else if (user.getName().length() < nameMinLength) {
            errors.rejectValue("name", "user.name.short", String.format(NAME_MIN_LENGTH, nameMinLength));
        }

        // Validate email
        if (StringUtils.isBlank(user.getEmail())) {
            errors.rejectValue("email", "user.email.empty", EMAIL_IS_REQUIRED);
        } else if (!user.getEmail().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            errors.rejectValue("email", "user.email.invalid", EMAIL_IS_NOT_VALID);
        }

        // Validate password
        if (StringUtils.isBlank(user.getPassword())) {
            errors.rejectValue("password", "user.password.empty", PASSWORD_IS_REQUIRED);
        } else if (user.getPassword().length() < passwordMinLength) {
            errors.rejectValue("password", "user.password.short", String.format(PASSWORD_MIN_LENGTH, passwordMinLength));
        }
    }
}
