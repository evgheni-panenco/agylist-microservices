package com.agylist.item.integration;

import com.agylist.item.dto.Sprint;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class SprintServiceClient {

    private static final String BASE_URI = "http://SPRINT-SERVICE/sprint/";

    private final RestTemplate restTemplate;

    public Sprint getSprintById(UUID sprintId, String username) {
        val uri = BASE_URI + sprintId;

        HttpHeaders headers = new HttpHeaders();
        headers.add("username", username);
        HttpEntity<Void> httpEntity = new HttpEntity<>(headers);

        return restTemplate.exchange(uri, HttpMethod.GET, httpEntity, Sprint.class).getBody();
    }
}
