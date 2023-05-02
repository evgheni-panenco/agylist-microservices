package com.agylist.identity.controller;

import com.agylist.identity.dto.AuthRequest;
import com.agylist.identity.dto.GetTokenResponse;
import com.agylist.identity.dto.ValidateTokenResponse;
import com.agylist.identity.entity.UserCredential;
import com.agylist.identity.exception.AccessRestrictedException;
import com.agylist.identity.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<Void> addNewUser(@RequestBody UserCredential userCredential) {
        authService.saveUser(userCredential);
        return ResponseEntity.status(201).build();
    }

    @PostMapping("/token")
    public ResponseEntity<GetTokenResponse> getToken(@RequestBody AuthRequest authRequest) {
        val user = new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword());
        val authenticate = authenticationManager.authenticate(user);

        if (authenticate.isAuthenticated()) {
            val token = authService.generateToken(authRequest.getUsername());
            return ResponseEntity.ok(new GetTokenResponse(token));
        } else {
            throw new AccessRestrictedException("Access restricted: invalid credentials");
        }
    }

    @GetMapping("/validate")
    public ResponseEntity<ValidateTokenResponse> validateToken(@RequestParam("token") String token) {
        authService.validateToken(token);
        return ResponseEntity.ok(new ValidateTokenResponse(token, true));
    }
}
