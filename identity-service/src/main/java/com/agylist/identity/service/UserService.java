package com.agylist.identity.service;

import com.agylist.identity.dto.UserResponse;
import com.agylist.identity.exception.ResourceNotFoundException;
import com.agylist.identity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserResponse getUserByUsername(String username) {
        val user = userRepository.findByName(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getName())
                .build();
    }

    public UserResponse getUserById(UUID id) {
        val user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getName())
                .build();
    }
}
