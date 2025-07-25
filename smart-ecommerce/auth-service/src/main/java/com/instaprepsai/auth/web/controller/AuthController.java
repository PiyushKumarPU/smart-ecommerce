package com.instaprepsai.auth.web.controller;

import com.instaprepsai.auth.AuthResponse;
import com.instaprepsai.auth.dto.LoginRequest;
import com.instaprepsai.auth.dto.RefreshTokenRequest;
import com.instaprepsai.auth.dto.UserRegistrationRequest;
import com.instaprepsai.auth.security.util.JwtUtil;
import com.instaprepsai.auth.service.AuthService;
import com.instaprepsai.common.api.response.InstaprepsApiResponse;
import com.instaprepsai.common.exception.BusinessException;
import com.instaprepsai.common.exception.converter.ValidationErrorConverter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;

    private final JwtUtil jwtUtil;

    @Operation(summary = "Register a new user", description = "This endpoint allows a new user to register in the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User registered successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping("/register")
    public InstaprepsApiResponse<String> register(@Valid @RequestBody UserRegistrationRequest userRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BusinessException(ValidationErrorConverter.convertBindingResultErrors(bindingResult));
        }
        authService.register(userRequest);
        return new InstaprepsApiResponse<>(true, "", "User registered successfully");
    }

    @Operation(summary = "Authenticate a user", description = "This endpoint allows a user to log in and receive authentication tokens.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User logged in successfully"),
            @ApiResponse(responseCode = "401", description = "Invalid username or password")
    })
    @PostMapping("/login")
    public InstaprepsApiResponse<AuthResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BusinessException(ValidationErrorConverter.convertBindingResultErrors(bindingResult));
        }
        AuthResponse authResponse = authService.authenticate(loginRequest);
        log.info("User {} logged in successfully", loginRequest.getUsername());
        return new InstaprepsApiResponse<>(true, authResponse, "User logged in successfully.");
    }

    @Operation(summary = "Refresh authentication token", description = "This endpoint allows a user to refresh their authentication token using a valid refresh token.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Token refreshed successfully"),
            @ApiResponse(responseCode = "401", description = "Invalid or expired refresh token")
    })
    @PostMapping("/refresh")
    public InstaprepsApiResponse<?> refreshToken(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BusinessException(ValidationErrorConverter.convertBindingResultErrors(bindingResult));
        }
        String refreshToken = refreshTokenRequest.getRefreshToken();
        if (jwtUtil.isValidJwtToken(refreshToken)) {
            String newToken = jwtUtil.generateTokenForRefreshToken(refreshToken);
            return new InstaprepsApiResponse<>(true, new AuthResponse(newToken, refreshToken), "Token generated successfully.");
        }
        return InstaprepsApiResponse.failure(String.valueOf(HttpStatus.UNAUTHORIZED.value()), "Invalid or expired refresh token");
    }

    @GetMapping("validate-token")
    public ResponseEntity<String> validateToken(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        log.info("Started validate token: {}", token);
        if (jwtUtil.isValidJwtToken(token)) {
            return ResponseEntity.ok("Token is valid");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        }
    }
}
