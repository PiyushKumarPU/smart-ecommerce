package com.instaprepsai.auth;

import lombok.Data;

@Data
public class AuthResponse {
    private final String token;
    private final String refreshToken;
}
