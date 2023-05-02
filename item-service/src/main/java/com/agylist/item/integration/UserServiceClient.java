package com.agylist.item.integration;

import com.agylist.item.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserServiceClient {

    private static final String BASE_URI = "http://IDENTITY-SERVICE/user/";

    private final RestTemplate restTemplate;

    public UserResponse getUserById(final UUID userId) {
        val uri = BASE_URI + "findById/" + userId;
        return restTemplate.getForEntity(uri, UserResponse.class).getBody();
    }
}
