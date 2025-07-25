package com.instaprepsai.auth.service;

import com.instaprepsai.auth.data.model.User;
import com.instaprepsai.auth.data.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserService {

    private final UserRepository userRepository;

    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }


    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(Long id, User updatedUser) {
        return userRepository.findById(id).map(user -> {
            user.setUsername(updatedUser.getUsername());
            user.setPassword(updatedUser.getPassword());
            user.setEmail(updatedUser.getEmail());
            user.setMobile(updatedUser.getMobile());
            user.setAccountStatus(updatedUser.getAccountStatus());
            user.setAccountLocked(updatedUser.isAccountLocked());
            user.setAccountExpired(updatedUser.isAccountExpired());
            user.setCredentialsExpired(updatedUser.isCredentialsExpired());
            user.setPasswordExpired(updatedUser.isPasswordExpired());
            user.setFailedAttempts(updatedUser.getFailedAttempts());
            user.setLastFailedAttempt(updatedUser.getLastFailedAttempt());
            user.setLastPasswordChange(updatedUser.getLastPasswordChange());
            return userRepository.save(user);
        }).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}