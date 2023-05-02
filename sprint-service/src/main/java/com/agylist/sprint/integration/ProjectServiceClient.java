package com.agylist.sprint.integration;

import com.agylist.sprint.dto.ProjectResponse;
import com.agylist.sprint.dto.ProjectUserResponse;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ProjectServiceClient {

    private static final String BASE_URI = "http://PROJECT-SERVICE/project/";

    private final RestTemplate restTemplate;

    public ProjectResponse getProjectById(UUID projectId) {
        val uri = BASE_URI + projectId;
        return restTemplate.getForEntity(uri, ProjectResponse.class).getBody();
    }

    public ProjectUserResponse getProjectUser(UUID project, String username) {
        val uri = BASE_URI + "/management/" + project + "/" + username;
        return restTemplate.getForEntity(uri, ProjectUserResponse.class).getBody();
    }
}
