package com.instaprepsai.auth.web.controller;

import com.instaprepsai.auth.data.model.RoleEnum;
import com.instaprepsai.auth.data.model.User;
import com.instaprepsai.auth.dto.UserResponse;
import com.instaprepsai.auth.service.UserService;
import com.instaprepsai.common.api.response.InstaprepsApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @Operation(summary = "Get user details", description = "Fetches user details based on the provided JWT token.",
            security = @SecurityRequirement(name = "Bearer"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User details retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Invalid Authentication Token"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/user")
    public ResponseEntity<InstaprepsApiResponse<UserResponse>> getUserDetails() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> userDetails = userService.getUserByUsername(username);

        return userDetails.map(user -> ResponseEntity.ok(new InstaprepsApiResponse<>(buildUserResponse(user), "User Details.")))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new InstaprepsApiResponse<>(null, "User not found")));
    }

    private UserResponse buildUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .username(user.getUsername())
                .email(user.getEmail())
                .mobile(user.getMobile())
                .accountStatus(user.getAccountStatus())
                .accountLocked(user.isAccountLocked())
                .accountExpired(user.isAccountExpired())
                .credentialsExpired(user.isCredentialsExpired())
                .passwordExpired(user.isPasswordExpired())
                .failedAttempts(user.getFailedAttempts())
                .lastFailedAttempt(user.getLastFailedAttempt())
                .lastPasswordChange(user.getLastPasswordChange())
                .roles(user.getRoles().stream().map(role -> RoleEnum.valueOf(role.getRoleName())).toList())
                .createdAt(user.getCreatedAt().toLocalDateTime())
                .updatedAt(user.getUpdatedAt().toLocalDateTime())
                .version(user.getVersion())
                .build();
    }

}
