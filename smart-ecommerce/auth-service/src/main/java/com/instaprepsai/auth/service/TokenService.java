package com.instaprepsai.auth.service;

import com.instaprepsai.auth.data.model.Token;
import com.instaprepsai.auth.data.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TokenService {

    private final TokenRepository tokenRepository;

    public List<Token> getAllTokens() {
        return tokenRepository.findAll();
    }

    public Optional<Token> getTokenById(Long id) {
        return tokenRepository.findById(id);
    }

    public Optional<Token> getTokenByValue(String token) {
        return tokenRepository.findByToken(token);
    }

    public Token createToken(Token token) {
        return tokenRepository.save(token);
    }

    public Token updateToken(Long id, Token updatedToken) {
        return tokenRepository.findById(id).map(token -> {
            token.setToken(updatedToken.getToken());
            token.setRevoked(updatedToken.isRevoked());
            token.setExpiresAt(updatedToken.getExpiresAt());
            token.setUser(updatedToken.getUser());
            return tokenRepository.save(token);
        }).orElseThrow(() -> new RuntimeException("Token not found"));
    }

    public void deleteToken(Long id) {
        tokenRepository.deleteById(id);
    }
}