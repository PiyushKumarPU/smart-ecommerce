package com.instaprepsai.auth.service;

import com.instaprepsai.auth.AuthResponse;
import com.instaprepsai.auth.data.model.Role;
import com.instaprepsai.auth.data.model.User;
import com.instaprepsai.auth.data.repository.UserRepository;
import com.instaprepsai.auth.mapper.UserMapper;
import com.instaprepsai.auth.security.util.JwtUtil;
import com.instaprepsai.auth.dto.LoginRequest;
import com.instaprepsai.auth.dto.UserRegistrationRequest;
import com.instaprepsai.auth.validator.AuthValidator;
import com.instaprepsai.common.api.response.error.FieldValidationError;
import com.instaprepsai.common.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final RoleService roleService;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthValidator authValidator;

    public AuthResponse authenticate(LoginRequest user) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
        String jwt = jwtUtil.generateToken(userPrincipal.getUsername());
        String refreshToken = jwtUtil.generateToken(userPrincipal.getUsername());
        return new AuthResponse(jwt, refreshToken);
    }

    public void register(UserRegistrationRequest userRequest) {
        List<FieldValidationError> apiErrorDetails = authValidator.validationRegistrationPayload(userRequest);
        if (!apiErrorDetails.isEmpty()) {
            throw new BusinessException(apiErrorDetails);
        }

        List<Role> roles = roleService.getByRoleNames(userRequest.getRoles());
        User user = UserMapper.INSTANCE.toUser(userRequest, roles, passwordEncoder);
        userRepository.save(user);
    }
}