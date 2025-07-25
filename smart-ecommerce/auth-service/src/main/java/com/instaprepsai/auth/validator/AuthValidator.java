package com.instaprepsai.auth.validator;

import com.instaprepsai.auth.data.repository.UserRepository;
import com.instaprepsai.auth.dto.UserRegistrationRequest;
import com.instaprepsai.common.api.response.error.FieldValidationError;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AuthValidator {

    private final UserRepository userRepository;

    public List<FieldValidationError> validationRegistrationPayload(UserRegistrationRequest userRequest) {
        List<FieldValidationError> errorDetails = new ArrayList<>();
        if (userRepository.existsByUsername(userRequest.getUsername())) {
            errorDetails.add(new FieldValidationError("username", userRequest.getUsername(), "Username already taken."));
        }
        if (userRepository.existsByEmail(userRequest.getEmail())) {
            errorDetails.add(new FieldValidationError("email", userRequest.getEmail(), "Email already taken."));
        }
        if (userRepository.existsByMobile(userRequest.getMobile())) {
            errorDetails.add(new FieldValidationError("mobile", userRequest.getMobile(), "Mobile number already taken."));
        }
        if(!isValidPassword(userRequest.getPassword())) {
            errorDetails.add(new FieldValidationError("password", userRequest.getPassword(), "User's password. Must be at least 8 characters long, contain at least one uppercase letter, one lowercase letter, and one digit."));
        }
        return errorDetails;
    }

    private Boolean isValidPassword(String password) {
        boolean hasUppercase = password.chars().anyMatch(Character::isUpperCase);
        boolean hasLowercase = password.chars().anyMatch(Character::isLowerCase);
        boolean hasDigit = password.chars().anyMatch(Character::isDigit);
        boolean hasMinimumLength = password.length() >= 8;
        return hasUppercase && hasLowercase && hasDigit && hasMinimumLength;
    }

}
