package com.instaprepsai.auth.dto;

import com.instaprepsai.auth.data.model.AccountStatusEnum;
import com.instaprepsai.auth.data.model.RoleEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@Schema(description = "User details model")
public class UserResponse {

    @Schema(description = "Unique identifier for the user")
    private UUID id;

    @Schema(description = "First name of the user")
    private String firstName;

    @Schema(description = "Last name of the user")
    private String lastName;

    @Schema(description = "Username of the user")
    private String username;

    @Schema(description = "Password of the user")
    private String password;

    @Schema(description = "Email address of the user")
    private String email;

    @Schema(description = "Mobile number of the user")
    private String mobile;

    @Schema(description = "Account status of the user")
    private AccountStatusEnum accountStatus;

    @Schema(description = "Indicates if the account is locked")
    private boolean accountLocked;

    @Schema(description = "Indicates if the account is expired")
    private boolean accountExpired;

    @Schema(description = "Indicates if the credentials are expired")
    private boolean credentialsExpired;

    @Schema(description = "Indicates if the password is expired")
    private boolean passwordExpired;

    @Schema(description = "Number of failed login attempts")
    private int failedAttempts;

    @Schema(description = "Timestamp of the last failed login attempt")
    private LocalDateTime lastFailedAttempt;

    @Schema(description = "Timestamp of the last password change")
    private LocalDateTime lastPasswordChange;

    @Schema(description = "List of roles assigned to the user")
    private List<RoleEnum> roles;

    @Schema(description = "Version of the user record")
    private Long version;

    @Schema(description = "Timestamp when the user was created")
    private LocalDateTime createdAt;

    @Schema(description = "Timestamp when the user was last updated")
    private LocalDateTime updatedAt;


}
