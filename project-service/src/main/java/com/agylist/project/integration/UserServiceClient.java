package com.agylist.project.integration;

import com.agylist.project.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class UserServiceClient {

    private static final String BASE_URI = "http://IDENTITY-SERVICE/user/";

    private final RestTemplate restTemplate;

    public UserResponse getUserByUsername(final String username) {
        val uri = BASE_URI + username;
        return restTemplate.getForEntity(uri, UserResponse.class).getBody();
    }
}
