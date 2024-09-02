package com.example.userservice.commons;

public class Constants {

    // Validation Messages
    public static final String NAME = "Name";
    public static final String EMAIL = "Email";
    public static final String PASSWORD = "Password";

    public static final String IS_REQUIRED = " is required and cannot be empty or blank.";
    public static final String IS_NOT_VALID = " is not valid.";
    public static final String MIN_LENGTH = " must be at least %d characters long.";

    public static final String NAME_IS_REQUIRED = NAME + IS_REQUIRED;
    public static final String EMAIL_IS_REQUIRED = EMAIL + IS_REQUIRED;
    public static final String PASSWORD_IS_REQUIRED = PASSWORD + IS_REQUIRED;

    public static final String EMAIL_IS_NOT_VALID = EMAIL + IS_NOT_VALID;

    public static final String NAME_MIN_LENGTH = NAME + MIN_LENGTH;
    public static final String PASSWORD_MIN_LENGTH = PASSWORD + MIN_LENGTH;

    public static final String EMAIL_ALREADY_EXISTS = "There is an user already created with that email.";

    // Exception messages
    public static final String USER_NOT_FOUND_ID = "User not found with Id: ";
}
