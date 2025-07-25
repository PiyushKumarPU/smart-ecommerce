package com.instaprepsai.auth.mapper;

import com.instaprepsai.auth.data.model.Role;
import com.instaprepsai.auth.data.model.User;
import com.instaprepsai.auth.dto.UserRegistrationRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "password", expression = "java(passwordEncoder.encode(userRequest.getPassword()))")
    @Mapping(target = "roles", source = "roles")
    User toUser(UserRegistrationRequest userRequest, List<Role> roles, PasswordEncoder passwordEncoder);
}
