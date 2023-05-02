package com.agylist.identity.service;

import com.agylist.identity.entity.UserCredential;
import com.agylist.identity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuthService {

    @Autowired
    private UserRepository repository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    public void saveUser(UserCredential credential) {
        credential.setId(UUID.randomUUID());
        credential.setPassword(passwordEncoder.encode(credential.getPassword()));
        repository.save(credential);
    }

    public String generateToken(String username) {
        return jwtService.generateToken(username);
    }

    public void validateToken(String token) {
        jwtService.validateToken(token);
    }


}
