package com.agylist.sprint.service;

import com.agylist.sprint.dto.Role;
import com.agylist.sprint.dto.SprintChangeRequest;
import com.agylist.sprint.dto.SprintRequest;
import com.agylist.sprint.exception.NotEligibleException;
import com.agylist.sprint.exception.ResourceNotFoundException;
import com.agylist.sprint.integration.ProjectServiceClient;
import com.agylist.sprint.model.Sprint;
import com.agylist.sprint.repository.SprintRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SprintService {

    private final SprintRepository sprintRepository;

    private final ProjectServiceClient projectServiceClient;

    public Sprint createNewSprint(SprintRequest request, String username) {
        isEligible(username, request.getProjectId());
        val project = projectServiceClient.getProjectById(request.getProjectId());

        val sprint = Sprint.builder()
                .id(UUID.randomUUID())
                .projectId(project.getId())
                .name(request.getName())
                .goals(request.getGoals())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .createdOn(OffsetDateTime.now())
                .isClosed(false)
                .isStarted(false)
                .build();

        sprintRepository.save(sprint);
        return sprint;
    }

    public Sprint getSprintById(UUID sprintId, String username) {
        val sprint = sprintRepository.findById(sprintId)
                .orElseThrow(() -> new ResourceNotFoundException("Sprint not found"));
        isEligible(username, sprint.getProjectId());
        return sprint;
    }

    public void startSprint(UUID sprintId, String username) {
        val sprint = sprintRepository.findById(sprintId)
                .orElseThrow(() -> new ResourceNotFoundException("Sprint not found"));
        isEligible(username, sprint.getProjectId());

        if (!sprint.isClosed()) {
            sprint.setStarted(true);
            sprintRepository.save(sprint);
        } else {
            throw new NotEligibleException("Sprint is already closed");
        }
    }

    public void closeSprint(UUID sprintId, String username) {
        val sprint = sprintRepository.findById(sprintId)
                .orElseThrow(() -> new ResourceNotFoundException("Sprint not found"));
        isEligible(username, sprint.getProjectId());

        if (sprint.isStarted()) {
            sprint.setClosed(true);
            sprint.setClosedOn(OffsetDateTime.now());
            sprintRepository.save(sprint);
        } else {
            throw new NotEligibleException("Sprint is not started yet");
        }
    }

    public List<Sprint> getAllSprintsByProjectId(UUID projectId, String username) {
        isEligible(username, projectId);
        val projects = sprintRepository.findAllByProjectId(projectId);
        return projects;
    }

    public Sprint updateProject(UUID sprintId, SprintChangeRequest request, String username) {
        val sprint = sprintRepository.findById(sprintId)
                .orElseThrow(() -> new ResourceNotFoundException("Spring not found"));

        isEligible(username, sprint.getProjectId());

        sprint.setName(request.getName());
        sprint.setGoals(request.getGoals());
        sprint.setStartDate(request.getStartDate());
        sprint.setEndDate(request.getEndDate());

        sprintRepository.save(sprint);

        return sprint;
    }

    private void isEligible(String username, UUID projectId) {
        val userProject = projectServiceClient.getProjectUser(projectId, username);

        if (userProject.getRole() == Role.USER) {
            throw new NotEligibleException("User has no sufficient rights to perform action");
        }
    }
}
