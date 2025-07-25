package com.instaprepsai.auth.constant;

public interface AppConstant {

    String[] PERMIT_ALL_URLS = {
            "/login",
            "/register",
            "/refresh",
            "/validate-token",
            "/swagger-ui/index.html",
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/api-docs/**",
            "/resources/**"
    };

}