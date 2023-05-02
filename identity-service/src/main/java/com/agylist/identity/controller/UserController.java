package com.agylist.identity.controller;

import com.agylist.identity.dto.UserResponse;
import com.agylist.identity.service.UserService;
import com.fasterxml.jackson.databind.deser.std.UUIDDeserializer;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/user/")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{username}")
    public ResponseEntity<UserResponse> getUserByUsername(@PathVariable String username) {
        val response = userService.getUserByUsername(username);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/findById/{userId}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable UUID userId) {
        val response = userService.getUserById(userId);
        return ResponseEntity.ok(response);
    }
}
